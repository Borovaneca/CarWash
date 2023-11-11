package com.example.carwash.web.controller;

import com.example.carwash.constants.ExceptionMessages;
import com.example.carwash.errors.UnauthorizedException;
import com.example.carwash.errors.UserNotFoundException;
import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.ProfileUpdateImageDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.view.ProfileView;
import com.example.carwash.service.UserService;
import com.example.carwash.service.ViewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.stream.Collectors;

@CrossOrigin("*")
@Controller
@RequestMapping("/users")
public class ProfileController {

    private final ViewService viewService;
    private final UserService userService;

    private ProfileEditDTO profileEditDTO;

    @Autowired
    public ProfileController(ViewService viewService, UserService userService) {
        this.viewService = viewService;
        this.userService = userService;
    }

    @ModelAttribute("profileEditDTO")
    public ProfileEditDTO profileEditDTO() {
        return profileEditDTO;
    }

    @CrossOrigin(value = "*")
    @GetMapping(value = "/view/{username}")
    public String getProfile(@PathVariable String username, Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {

        if (username == null) {
            return "redirect:/";
        }

        if (isValidUser(username)) {
            if (isAuthorized(userDetails, username)) {
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
                              Principal principal,
                              @AuthenticationPrincipal UserDetails userDetails) {

        if (username == null) {
            return "redirect:/";
        }

        checkIfAuthorized(userDetails, username);

        if (isValidUser(username)) {
            if (profileEditDTO == null) {
                profileEditDTO = userService.getUserAndMapToProfileEditDTO(username);
                model.addAttribute("profileEditDTO", profileEditDTO);
            }
        }
        return "edit-profile";
    }


    @PostMapping("/edit/{username}")
    public String postProfile(@Valid ProfileEditDTO profileEditDTO, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              UserDetails userDetails,
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
        return "redirect:/users/" + username;
    }

    private static void checkIfAuthorized(UserDetails userDetails, String username) {
        if (!isAuthorized(userDetails, username)) {
            throw new UnauthorizedException(ExceptionMessages.ACCESS_DENIED);
        }
    }

    @PostMapping("/change/image")
    public String editImage(
            ProfileUpdateImageDTO profileUpdateImageDTO,
            @AuthenticationPrincipal UserDetails userDetails) throws InterruptedException {

        if (profileUpdateImageDTO.getImage() == null) {
            return "redirect:/users/view/" + profileUpdateImageDTO.getUsername();
        }

        checkIfAuthorized(userDetails, profileUpdateImageDTO.getUsername());

        if (isValidUser(profileUpdateImageDTO.getUsername())) {
            userService.updateImage(profileUpdateImageDTO);
            Thread.sleep(4000);
            return "redirect:/users/view/" + profileUpdateImageDTO.getUsername();
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/social/add/{username}")
    public String addSocial(@PathVariable String username,
                            SocialMediaAddDTO socialMediaAddDTO,
                            @AuthenticationPrincipal UserDetails userDetails) throws InterruptedException {

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
                            @AuthenticationPrincipal UserDetails userDetails) throws InterruptedException {


        if (isValidUser(username)) {
            checkIfAuthorized(userDetails, username);
            userService.deleteSocialMedia(username, type);
            return "redirect:/users/view/" + username;
        }
        return "redirect:/";
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView onProfileNotFound(UserNotFoundException unfe) {
        ModelAndView modelAndView = new ModelAndView("user-not-found");
        modelAndView.addObject("username", unfe.getUsername());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView notAuthorized(UnauthorizedException uae) {
        return new ModelAndView("unauthorized");
    }

    private boolean isValidUser(String username) {
        if (userNotFound(username)) {
            throw new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND, username);
        }
        return true;
    }


    private boolean userNotFound(String username) {
        return userService.findByUsername(username) == null;
    }

    private static boolean isAuthorized(UserDetails userDetails, String username) {
        boolean admin = !userDetails.getAuthorities().
                stream().
                filter(a -> a.getAuthority().equals("ROLE_OWNER")).
                collect(Collectors.toSet()).isEmpty();

        boolean user = username.equals(userDetails.getUsername());

        return !userDetails.getAuthorities().
                stream().
                filter(a -> a.getAuthority().equals("ROLE_OWNER")).
                collect(Collectors.toSet()).isEmpty() || username.equals(userDetails.getUsername());
    }
}
