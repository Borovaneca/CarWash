package com.example.carwash.web.rest;

import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import jakarta.validation.constraints.Min;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class VehicleRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @LocalServerPort
    private int port;

    private String url = "http://localhost:";

    @BeforeEach
    void setUp() {
        userRepository.save(registerUser());
    }

    @Test
    @WithMockUser(username = "Adminov", roles = {"USER", "ADMIN"})
    void getVehicles() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/my-vehicles/"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("[]", mvcResult.getResponse().getContentAsString());

        VehicleAddDTO vehicleAddDTO = new VehicleAddDTO();
        vehicleAddDTO.setBrand("BMW");
        vehicleAddDTO.setModel("X5");
        vehicleAddDTO.setColor("Black");

        mockMvc.perform(post(url + port + "/my-vehicles/add")
                .flashAttr("vehicleAddDTO", vehicleAddDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        String expected = "[{\"id\":1,\"brand\":\"BMW\",\"model\":\"X5\",\"color\":\"Black\"}]";

        String actual = mockMvc.perform(get(url + port + "/api/my-vehicles/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    private User registerUser() {
        User admin = new User();
        admin.setUsername("Adminov");
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
        admin.setVehicles(new ArrayList<>());
        admin.setRegisteredOn(LocalDate.now());
        admin.setSocialMedias(new ArrayList<>());
        admin.setBio("Lorem ipsum dolor sit amet consectetur adipisicing elit. Blanditiis ducimus ad atque nulla. Reiciendis praesentium beatae quod cumque odit accusamus. Doloribus inventore voluptatem suscipit pariatur omnis aliquid non illo mollitia!");
        return admin;
    }
}
