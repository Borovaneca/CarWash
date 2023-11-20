package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.PasswordForgotDTO;
import com.example.carwash.model.dtos.ResetPasswordDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.service.interfaces.ResetService;
import com.example.carwash.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForgotPasswordController {

    private final ResetService resetService;
    private final UserService userService;

    public ForgotPasswordController(ResetService resetService, UserService userService) {
        this.resetService = resetService;
        this.userService = userService;
    }

    @GetMapping("/users/forgot-password")
    public String forgotPassword(){
        return "forgot-password";
    }

    @PostMapping("/users/forgot-password")
    public String forgotPasswordPost(@Valid PasswordForgotDTO passwordForgotDTO,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "forgot-password";

        User user = userService.findByEmail(passwordForgotDTO.email());
        if (user == null) return "forgot-password";

        userService.sendResetPasswordEmail(user);
        model.addAttribute("activationSend", true);
        return "forgot-password";
    }

    @GetMapping("/users/reset-password/{username}/{token}")
    public String resetPassword(@PathVariable String username, @PathVariable String token,
                                @ModelAttribute ResetPasswordDTO resetPasswordDTO, Model model) {
        if (username == null || token == null) return "redirect:/";
        User user = userService.findByUsername(username);
        if (user == null) return "redirect:/";
        if (resetService.isValid(token, username)) {

            model.addAttribute("passwordChanged", false);
            model.addAttribute("username", username);
            model.addAttribute("token", token);
            return "reset-password";
        }
        return "redirect:/";
    }

    @PostMapping("/users/reset-password/{username}/{token}")
    public String postResetPassword(@PathVariable String username, @PathVariable String token,
                                    @Valid @ModelAttribute ResetPasswordDTO resetPasswordDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes, Model model) {
            if (username == null || token == null) return "reset-password";

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("resetPasswordDTO", resetPasswordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.resetPasswordDTO", bindingResult);
            return "redirect:/users/reset-password/" + username + "/" + token ;
        }

        resetService.resetPasswordForUserAndDeleteToken(username, resetPasswordDTO.getPassword());
        model.addAttribute("passwordChanged", true);
        model.addAttribute("username", username);
        model.addAttribute("token", token);

        return "redirect:/login";
    }
}
