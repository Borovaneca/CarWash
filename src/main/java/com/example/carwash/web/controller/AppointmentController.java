package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.service.ViewService;
import com.example.carwash.service.interfaces.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final ViewService viewService;
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(ViewService viewService, AppointmentService appointmentService) {
        this.viewService = viewService;
        this.appointmentService = appointmentService;
    }

    @ModelAttribute("appointmentAddDTO")
    public AppointmentAddDTO getAppointmentAddDTO() {
        return new AppointmentAddDTO();
    }

    @GetMapping("my-appointments")
    public String myAppointments() {
        return "my-appointments";
    }

    @GetMapping("/make-appointment")
    public String makeAppointment(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("services", viewService.getAllServices());
        model.addAttribute("vehicles", viewService.getAllVehiclesByUserUsername(userDetails.getUsername()));
        return "make-appointment";
    }


    @PostMapping("/make-appointment")
    public String postMakeAppointment(@Valid AppointmentAddDTO appointmentAddDTO, BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("appointmentAddDTO", appointmentAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.appointmentAddDTO", bindingResult);
            return "redirect:/appointments/make-appointment";
        }
        redirectAttributes.addFlashAttribute("addedSuccessfully", true);
        appointmentService.addAppointmentToUserWithUsername(appointmentAddDTO, userDetails.getUsername());
        return "redirect:/appointments/make-appointment";
    }
}
