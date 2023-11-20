package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.UserRepository;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private GreenMail greenMail;

    private UserRegisterDTO user;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
        greenMail.start();
        greenMail.setUser(username, password);
        user = createUser();
    }

    @Test
    void testRegister() throws Exception {

        mockMvc.perform(
                        post("/users/register")
                                .flashAttr("userRegisterDTO", user)
                                .with(csrf())
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
        userRepository.deleteAll();
    }


    private UserRegisterDTO createUser() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setEmail("test@test.com");
        userRegisterDTO.setPassword("Borovaneca");
        userRegisterDTO.setConfirmPassword("Borovaneca");
        return userRegisterDTO;
    }
}
