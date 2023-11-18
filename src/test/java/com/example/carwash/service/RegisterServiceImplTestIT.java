package com.example.carwash.service;

import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.UserRepository;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterServiceImplTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegisterServiceImpl registerServiceTest;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Autowired
    private UserRepository userRepository;

    private GreenMail greenMail;

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;


    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
        greenMail.start();
        greenMail.setUser(username, password);
    }

    @Test
    void registerUserSendingEmail() throws Exception {

        FileInputStream stream = new FileInputStream("C:/Users/borot/IdeaProjects/CarWash/src/test/resources/static/images/R.jpeg");
        MultipartFile f1 = new MockMultipartFile("file", "MOCK_file.jpg", "image/jpeg", stream);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .param("username", "test")
                        .param("password", "Borovaneca1")
                        .param("confirmPassword", "Borovaneca1")
                        .param("email", "test@test1.com")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    void registerUser() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/register")
                        .param("image", "test")
                        .param("username", "Borovaneca")
                        .param("email", "test@test.com")
                        .param("password", "Borovaneca1")
                        .param("confirmPassword", "Borovaneca1")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection());

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        FileInputStream stream = new FileInputStream("C:/Users/borot/IdeaProjects/CarWash/src/test/resources/static/images/R.jpeg");
        MultipartFile f1 = new MockMultipartFile("file", "MOCK_file.jpg", "image/jpeg", stream);
        userRegisterDTO.setImage(f1);
        userRegisterDTO.setUsername("Borovaneca");
        userRegisterDTO.setEmail("test@test.com");
        userRegisterDTO.setPassword("Borovaneca1");
        userRegisterDTO.setConfirmPassword("Borovaneca1");
        registerServiceTest.registerUser(userRegisterDTO, successfulAuth -> {
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
            SecurityContext securityContext = strategy.getContext();
            securityContext.setAuthentication(successfulAuth);
            strategy.setContext(securityContext);
            securityContextRepository.saveContext(securityContext, request, response);
        });
        userRepository.findByUsername("Borovaneca").ifPresent(user -> {
            assertEquals("Borovaneca", user.getUsername());
            assertEquals("test@test.com", user.getEmail());
        });
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }
}