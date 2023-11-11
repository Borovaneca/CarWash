package com.example.carwash.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {


    @GetMapping("/users/login")
    public String login(){
        return "login";
    }

    @PostMapping("/users/login-error")
    public String loginError(RedirectAttributes redirectAttributes){
        boolean wrongCredentials = true;
        redirectAttributes.addFlashAttribute("wrongCredentials", wrongCredentials);
        return "redirect:/users/login";
    }
}
