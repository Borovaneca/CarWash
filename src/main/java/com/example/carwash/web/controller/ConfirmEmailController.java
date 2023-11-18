package com.example.carwash.web.controller;

import com.example.carwash.constants.ExceptionMessages;
import com.example.carwash.errors.TokenNotFoundException;
import com.example.carwash.model.entity.ConfirmationToken;
import com.example.carwash.service.interfaces.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConfirmEmailController {

    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public ConfirmEmailController(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping("/confirm-email/token/{token}")
    public String confirmEmail(@PathVariable String token) {
        ConfirmationToken valid = confirmationTokenService.findByToken(token);
        if (valid == null) throw new TokenNotFoundException(ExceptionMessages.TOKEN_NOT_FOUND_EXCEPTION_MESSAGE);
        confirmationTokenService.confirmEmail(valid.getUser());
        return "email/confirm-email";
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ModelAndView handleTokenNotFoundException(TokenNotFoundException e, Model model) {
        model.addAttribute("invalidToken", true);
        return new ModelAndView("error/404");
    }
}
