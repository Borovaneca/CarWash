package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ViewService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AppointmentControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ViewService viewService;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        if (userRepository.count() == 0) {
            user = registerUser();
            userRepository.save(user);
        }
    }

    @Test
    void testGetShouldBeOk() throws Exception {
        mockMvc.perform(get("/appointments/make-appointment"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"USER", "ADMIN"})
    void testMakeVehicleShouldBeOk() throws Exception {
        VehicleAddDTO vehicleAddDTO = new VehicleAddDTO();
        vehicleAddDTO.setBrand("BMW");
        vehicleAddDTO.setModel("X5");
        vehicleAddDTO.setColor("Black");

        mockMvc.perform(post("/my-vehicles/add")
                        .with(csrf())
                        .flashAttr("vehicleAddDTO", vehicleAddDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"USER", "ADMIN"})
    void testAddAppointmentShouldBAdd() throws Exception {
        VehicleAddDTO vehicleAddDTO = new VehicleAddDTO();
        vehicleAddDTO.setBrand("BMW");
        vehicleAddDTO.setModel("X5");
        vehicleAddDTO.setColor("Black");
        mockMvc.perform(post("/my-vehicles/add")
                        .with(csrf())
                        .flashAttr("vehicleAddDTO", vehicleAddDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection());


        AppointmentAddDTO appointmentAddDTO = new AppointmentAddDTO();
        appointmentAddDTO.setVehicleId(1L);
        appointmentAddDTO.setService(viewService.getAllServices().get(0).getName());
        appointmentAddDTO.setMadeFor(LocalDateTime.now().plusDays(1));

        mockMvc.perform(post("/appointments/make-appointment")
                        .flashAttr("appointmentAddDTO", appointmentAddDTO)
                        .with(csrf()))
                .andExpect(status().isFound());
    }


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }



    private User registerUser() {
        User admin = new User();
        admin.setUsername("Admin");
        admin.setPassword(passwordEncoder.encode("Adminov1"));
        admin.setRoles(roleRepository.findAll());
        admin.setImage(new ProfileImage("test"));
        admin.setCity("Vratsa");
        admin.setActive(true);
        admin.setAge(23);
        admin.setImage(profileImageRepository.save(new ProfileImage("test")));
        admin.setEmail("borovaneca@softuni.bg");
        admin.setFirstName("Petyo");
        admin.setLastName("Veselinov");
        admin.setBanned(false);
        admin.setRegisteredOn(LocalDate.now());
        admin.setSocialMedias(new ArrayList<>());
        admin.setBio("Lorem ipsum dolor sit amet consectetur adipisicing elit. Blanditiis ducimus ad atque nulla. Reiciendis praesentium beatae quod cumque odit accusamus. Doloribus inventore voluptatem suscipit pariatur omnis aliquid non illo mollitia!");
        return admin;
    }
}
