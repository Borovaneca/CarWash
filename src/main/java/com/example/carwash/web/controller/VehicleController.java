package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin("*")
@Controller
@RequestMapping("/my-vehicles")
public class VehicleController {

    private final UserService userService;

    public VehicleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String vehicles() {
        return "vehicles-view";
    }


    @PostMapping("/add")
    public String addVehicle(@Valid @ModelAttribute VehicleAddDTO vehicleAddDTO, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("invalidFields", true);
            return "redirect:/my-vehicles";
        }
        userService.addVehicleToUser(userDetails.getUsername(), vehicleAddDTO);
        return "redirect:/my-vehicles";
    }

    @GetMapping("/remove/{id}")
    public String removeVehicle(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {

        if (userService.removeVehicleFromUser(userDetails.getUsername(), Long.parseLong(id))) {
            return "redirect:/my-vehicles";
        }
        return "redirect:/";
    }

}
