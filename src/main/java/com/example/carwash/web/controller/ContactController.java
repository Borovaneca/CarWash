package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.ContactDTO;
import com.example.carwash.service.interfaces.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    private final EmailService emailService;

    @Autowired
    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/contact")
    public String contact(ContactDTO contactDTO, Model model) {

            model.addAttribute("contact", contactDTO);
        return "contact";
    }

    @PostMapping("/contact")
    public String postContact(@Valid ContactDTO contactDTO, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contact", contactDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactDTO", bindingResult);
            return "redirect:/contact";
        }
        emailService.receiveComment(contactDTO);
        redirectAttributes.addFlashAttribute("success", true);
        contactDTO = null;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/contact";
    }
}
