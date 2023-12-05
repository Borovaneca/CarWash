package com.example.carwash.web.rest;

import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ViewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.util.GreenMail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class AllUsersRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private ViewService viewService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int portEmail;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private GreenMail greenMail;

    private String url = "http://localhost:";

    private User user;
    @BeforeEach
    void setUp() {
        user = registerUser();
        userRepository.save(user);
    }

    @Test
    void getAllUsers() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(viewService.getAllUsers().toArray());

        MvcResult mvcResult = mockMvc.perform(get(url + port + "/api/owner/users/all"))
                .andExpect(status().isOk())
                .andReturn();

        String actual = mvcResult.getResponse().getContentAsString();

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    private User registerUser() {
        User admin = new User();
        admin.setUsername("Adminco");
        admin.setPassword(passwordEncoder.encode("Adminov1"));
        admin.setRoles(roleRepository.findAll());
        admin.setImage(new ProfileImage("test"));
        admin.setCity("Vratsa");
        admin.setActive(true);
        admin.setAge(23);
        admin.setImage(profileImageRepository.save(new ProfileImage("test")));
        admin.setEmail("borovaneca1@softuni.bg");
        admin.setFirstName("Petyo");
        admin.setLastName("Veselinov");
        admin.setBanned(false);
        admin.setRegisteredOn(LocalDate.now());
        admin.setSocialMedias(new ArrayList<>());
        admin.setBio("Lorem ipsum dolor sit amet consectetur adipisicing elit. Blanditiis ducimus ad atque nulla. Reiciendis praesentium beatae quod cumque odit accusamus. Doloribus inventore voluptatem suscipit pariatur omnis aliquid non illo mollitia!");
        return admin;
    }
}