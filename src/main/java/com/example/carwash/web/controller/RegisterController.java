package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.service.RegisterServiceImpl;
import com.example.carwash.service.interfaces.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final RegisterService registerService;
    private final SecurityContextRepository securityContextRepository;


    @Autowired
    public RegisterController(RegisterService registerService, SecurityContextRepository securityContextRepository) {
        this.registerService = registerService;
        this.securityContextRepository = securityContextRepository;
    }

    @ModelAttribute("userRegisterDTO")
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }


    @GetMapping("/users/register")
    public String register() {
        return "register";
    }

    @PostMapping("users/register")
    public String postRegister(
            @Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            HttpServletResponse response
            ) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/users/register";
        }
        registerService.registerUser(userRegisterDTO, successfulAuth -> {
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);

            strategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        });

        return "redirect:/";
    }
}
