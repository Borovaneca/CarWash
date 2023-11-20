package com.example.carwash.web.controller;

import com.example.carwash.model.dtos.PasswordForgotDTO;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.model.entity.ResetPassword;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.ResetPasswordRepository;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.UserService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ForgotPasswordControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private GreenMail greenMail;

    @Mock
    private BindingResult bindingResult;

    @Autowired
    private ForgotPasswordController forgotPasswordController;

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
    public void testGetResetPasswordShouldNotFoundAndRedirect() throws Exception {
        mockMvc.perform(get("/users/reset-password/{username}/{token}", "test", "test"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/"));
    }

    @Test
    public void testPostResetPasswordShouldReturnFound() throws Exception {
        PasswordForgotDTO passwordForgotDTO = new PasswordForgotDTO("borovaneca@softuni.bg");

        mockMvc.perform(
                post("/users/forgot-password")
                .flashAttr("passwordForgotDTO", passwordForgotDTO)
                .with(csrf()))
                .andExpect(status().isOk());
        Optional<ResetPassword> user = resetPasswordRepository.findByUsername("Admin");
        String token = user.get().getToken();
        mockMvc.perform(post("/users/reset-password/{username}/{token}", "Admin", token)
                        .param("password", "Adminov1")
                .param("confirmPassword", "Adminov1")
                .with(csrf()))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/login"));
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
        admin.setCity("Vratsa");
        admin.setActive(true);
        admin.setAge(23);
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
