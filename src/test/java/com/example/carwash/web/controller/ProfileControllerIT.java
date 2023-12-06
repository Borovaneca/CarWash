package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ProfileControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

private static Long USER_ID;

    private User savedUser;

    @BeforeEach
    void setUp() {
        if (userRepository.count() == 0) {
            savedUser = aRandomUser();
            userRepository.save(savedUser);
            USER_ID = savedUser.getId();
        }
    }
    @Test
    void getProfileShouldRedirectToNotFoundPage() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/view/test")
                        .with(user("Admin").password("Adminov1"))
        ).andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getProfileShouldReturnStatusOk() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/view/PetyoV")
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUpdateProfileShouldUpdateIt() throws Exception {
        Assertions.assertTrue(userRepository.existsById(USER_ID));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/edit/PetyoV")
                        .param("id", String.valueOf(USER_ID))
                        .param("firstName", "Ivan")
                        .param("lastName", "Ivanov")
                        .param("city", "Varna")
                        .param("age", "23")
                        .param("bio", "Lorem ipsum dolor sit amet consectetur adipisicing elit. Blanditiis ducimus ad atque nulla. Reiciendis praesentium beatae quod cumque odit accusamus. Doloribus inventore voluptatem suscipit pariatur omnis aliquid non illo mollitia!")
                        .param("email", "petyoV@softuni.bg")
                        .param("username", "PetyoA")
                        .param("password", "Adminov1")
                        .param("confirmPassword", "Adminov1")
                        .with(csrf())
                        .with(user("PetyoV").password("Adminov1"))
        ).andExpect(status().isFound());

        Optional<User> updatedUser = userRepository.findById(USER_ID);
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get())
                .extracting(User::getFirstName, User::getLastName, User::getCity, User::getUsername)
                .containsExactly("Ivan", "Ivanov", "Varna", "PetyoA");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    private User aRandomUser() {
        User admin = new User();
        admin.setUsername("PetyoV");
        admin.setPassword(passwordEncoder.encode("Adminov1"));
        admin.setRoles(roleRepository.findAll());
        admin.setImage(new ProfileImage("test"));
        admin.setCity("Vratsa");
        admin.setActive(true);
        admin.setAge(23);
        admin.setImage(profileImageRepository.save(new ProfileImage("test")));
        admin.setEmail("petyoV@softuni.bg");
        admin.setFirstName("Petyo");
        admin.setLastName("Veselinov");
        admin.setBanned(false);
        admin.setRegisteredOn(LocalDate.now());
        admin.setSocialMedias(new ArrayList<>());
        admin.setBio("Lorem ipsum dolor sit amet consectetur adipisicing elit. Blanditiis ducimus ad atque nulla. Reiciendis praesentium beatae quod cumque odit accusamus. Doloribus inventore voluptatem suscipit pariatur omnis aliquid non illo mollitia!");
        return admin;
    }
}