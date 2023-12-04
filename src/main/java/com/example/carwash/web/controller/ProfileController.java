package com.example.carwash.web.controller;

import com.example.carwash.constants.ExceptionMessages;
import com.example.carwash.errors.UnauthorizedException;
import com.example.carwash.errors.UserNotFoundException;
import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.ProfileUpdateImageDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.view.ProfileView;
import com.example.carwash.service.interfaces.ViewService;
import com.example.carwash.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;

@CrossOrigin("*")
@Controller
@RequestMapping("/users")
public class ProfileController {

    private final ViewService viewService;
    private final UserService userService;


    @Autowired
    public ProfileController(ViewService viewService, @Qualifier("userServiceProxy") UserService userService) {
        this.viewService = viewService;
        this.userService = userService;
    }

    @ModelAttribute("profileEditDTO")
    public ProfileEditDTO profileEditDTO() {
        return new ProfileEditDTO();
    }


    @GetMapping("/view/{username}")
    public String getProfile(@PathVariable String username, Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {

        if (username == null) {
            return "redirect:/";
        }
        if (isValidUser(username)) {
            if (userService.isAuthorized(userDetails, username)) {
                model.addAttribute("isAuthorized", true);
            }
            ProfileView profileView = viewService.getProfileView(username);
            model.addAttribute("user", profileView);
        }
        return "profile";
    }

    @GetMapping("/edit/{username}")
    public String editProfile(@PathVariable String username,
                              Model model,
                              @AuthenticationPrincipal UserDetails userDetails) {

        if (username == null) {
            return "redirect:/";
        }

        checkIfAuthorized(userDetails, username);

        if (isValidUser(username)) {
            if (model.asMap().size() == 1) {
                ProfileEditDTO profileEditDTO = userService.getUserAndMapToProfileEditDTO(username);
                model.addAttribute("profileEditDTO", profileEditDTO);
            }
        }
        return "edit-profile";
    }


    @PostMapping("/edit/{username}")
    public String postProfile(@Valid ProfileEditDTO profileEditDTO, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable String username) {

        checkIfAuthorized(userDetails, username);

        if (isValidUser(username)) {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("profileEditDTO", profileEditDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileEditDTO", bindingResult);
                return "redirect:/users/edit/" + username;
            }
            userService.update(profileEditDTO);
        }
        return "redirect:/users/view/" + username;
    }


    @PostMapping("/change/image")
    public String editImage(
            @Valid ProfileUpdateImageDTO profileUpdateImageDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("invalidImage", true);
            return "redirect:/users/view/" + profileUpdateImageDTO.getUsername();
        }

        checkIfAuthorized(userDetails, profileUpdateImageDTO.getUsername());

        if (isValidUser(profileUpdateImageDTO.getUsername())) {
            userService.updateImage(profileUpdateImageDTO);
            return "redirect:/users/view/" + profileUpdateImageDTO.getUsername();
        } else {
            throw new UnauthorizedException("Not authorized for this action!");
        }
    }

    @PostMapping("/social/add/{username}")
    public String addSocial(@PathVariable String username,
                            @Valid SocialMediaAddDTO socialMediaAddDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("invalidSocial", true);
            return "redirect:/users/view/" + socialMediaAddDTO.getUsername();
        }

        checkIfAuthorized(userDetails, username);

        if (isValidUser(username)) {
            userService.addSocialMedia(username, socialMediaAddDTO);
            return "redirect:/users/view/" + socialMediaAddDTO.getUsername();
        }
        return "redirect:/";
    }

    @CrossOrigin("*")
    @PostMapping("/social/delete/{username}/{type}")
    public String deleteSocial(@PathVariable String username,
                               @PathVariable String type,
                               @AuthenticationPrincipal UserDetails userDetails) {

        if (isValidUser(username)) {
            checkIfAuthorized(userDetails, username);
            userService.deleteSocialMedia(username, type);
            return "redirect:/users/view/" + username;
        }
        return "redirect:/";
    }

    @DeleteMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username,
                             @AuthenticationPrincipal UserDetails userDetails,
                             HttpServletResponse response,
                             HttpServletRequest request) {

        if (isValidUser(username)) {
            checkIfAuthorized(userDetails, username);
                userService.deleteUser(username);
            Arrays.stream(request.getCookies())
                            .forEach(cookie -> {
                                cookie.setMaxAge(0);
                                cookie.setValue(null);
                                cookie.setPath("/");
                                response.addCookie(cookie);
                            });
                SecurityContextHolder.clearContext();
        }
        return "redirect:/";
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView onProfileNotFound(UserNotFoundException unfe) {
        ModelAndView modelAndView = new ModelAndView("error/user-not-found");
        modelAndView.addObject("username", unfe.getUsername());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView notAuthorized(UnauthorizedException uae) {
        return new ModelAndView("error/unauthorized");
    }

    private boolean isValidUser(String username) {
        if (userService.findByUsername(username) == null) {
            throw new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND, username);
        }
        return true;
    }

    private void checkIfAuthorized(UserDetails userDetails, String username) {
        if (!userService.isAuthorized(userDetails, username)) {
            throw new UnauthorizedException(ExceptionMessages.ACCESS_DENIED);
        }
    }
}
