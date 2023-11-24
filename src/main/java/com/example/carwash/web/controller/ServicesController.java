package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.ServiceAddDTO;
import com.example.carwash.service.interfaces.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@CrossOrigin("*")
public class ServicesController {

    private final ServiceService serviceService;

    @Autowired
    public ServicesController(@Qualifier("serviceServiceProxy") ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @ModelAttribute("serviceAddDTO")
    public ServiceAddDTO serviceAddDTO() {
        return new ServiceAddDTO();
    }


    @GetMapping("/services/view")
    public String get() {
        return "services";
    }

    @GetMapping("/owner/services/add")
    public String add() {
        return "owner/add-service";
    }

    @PostMapping("/owner/services/add")
    public String addPost(@Valid ServiceAddDTO serviceAddDTO, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("serviceAddDTO", serviceAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.serviceAddDTO", bindingResult);
            return "redirect:/owner/services/add";
        }
        serviceService.addService(serviceAddDTO);

        return "redirect:/services/view";
    }

    @PostMapping("/owner/services/delete/{id}")
    public String delete(@PathVariable Long id) {
        serviceService.deleteServiceById(id);
        return "redirect:/services/view";
    }
}
