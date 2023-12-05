package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.RegisterDTO;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.RegisterService;
import com.example.carwash.service.interfaces.UserService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
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

    @Autowired
    @Qualifier("userServiceProxy") private UserService userService;

    @Autowired
    private RegisterService registerService;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private GreenMail greenMail;

    private RegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
        greenMail.start();
        greenMail.setUser(username, password);
        registerDTO = createUser();
    }

    @Test
    void testRegister() throws Exception {

        Assertions.assertEquals(1, userRepository.count());
        Assertions.assertEquals(1, userService.getAllUsers().size());

        mockMvc.perform(
                        post("/users/register")
                                .param("username", registerDTO.getUsername())
                                .param("email", registerDTO.getEmail())
                                .param("password", registerDTO.getPassword())
                                .param("confirmPassword", registerDTO.getConfirmPassword())
                                .with(csrf())
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));

        Assertions.assertEquals(2, userRepository.count());
        Assertions.assertEquals(2, userService.getAllUsers().size());

        Assertions.assertNotNull(userRepository.findByUsername("testUser").get());
        Assertions.assertEquals("test@test.com", userRepository.findByUsername("testUser").get().getEmail());
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
        userRepository.deleteAll();
    }


    private RegisterDTO createUser() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testUser");
        registerDTO.setEmail("test@test.com");
        registerDTO.setPassword("Borovaneca1");
        registerDTO.setConfirmPassword("Borovaneca1");
        return registerDTO;
    }
}
