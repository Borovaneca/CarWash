package com.example.carwash.web.controller;

import com.example.carwash.model.entity.User;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
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

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private GreenMail greenMail;

    private User user;
    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(port,     host, "smtp"));
        greenMail.start();
        greenMail.setUser(username, password);
        if (userRepository.count() == 0) {
            user = registerUser();
            userRepository.save(user);
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
                MockMvcRequestBuilders.get("/users/view/Admin")
                        .with(user("Admin").password("Adminov1"))
        ).andExpect(status().isOk());
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
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