package com.example.carwash.service;

import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SocialMediaServiceImpl socialMediaService;

    @Mock
    private ProfileImageServiceImpl profileImageService;

    @Mock
    private ResetServiceImpl resetService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private VehicleServiceImpl vehicleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void update_ValidProfileEditDTO_ShouldUpdateUser() {
        ProfileEditDTO profileEditDTO = new ProfileEditDTO();
        profileEditDTO.setId(1L);
        profileEditDTO.setFirstName("John");
        profileEditDTO.setLastName("Doe");
        profileEditDTO.setCity("New York");
        profileEditDTO.setAge(30);
        profileEditDTO.setBio("Test Bio");
        profileEditDTO.setPassword("newPassword");


        User mockUser = getUser();

        doReturn(Optional.of(mockUser)).when(userRepository).findById(profileEditDTO.getId());

        userService.update(profileEditDTO);

        assertEquals("John", mockUser.getFirstName());
        assertEquals("Doe", mockUser.getLastName());
        assertEquals("New York", mockUser.getCity());
        assertEquals(30, mockUser.getAge());
        assertEquals("Test Bio", mockUser.getBio());
        assertNotEquals("newPassword", mockUser.getPassword()); // Password should be encoded
        verify(userRepository, times(1)).save(mockUser);
    }

    private User getUser() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("johndoe");
        mockUser.setEmail("john@example.com");
        mockUser.setPassword("topsecret");
        return mockUser;
    }

    @Test
    void getUserAndMapToProfileEditDTO_ValidUsername_ShouldReturnProfileEditDTO() {
        String username = "johndoe";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername(username);
        mockUser.setEmail("john@example.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setCity("New York");
        mockUser.setAge(30);
        mockUser.setBio("Test Bio");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        ProfileEditDTO profileEditDTO = userService.getUserAndMapToProfileEditDTO(username);

        assertNotNull(profileEditDTO);
        assertEquals("johndoe", profileEditDTO.getUsername());
        assertEquals("John", profileEditDTO.getFirstName());
        assertEquals("Doe", profileEditDTO.getLastName());
        assertEquals("New York", profileEditDTO.getCity());
        assertEquals(30, profileEditDTO.getAge());
        assertEquals("Test Bio", profileEditDTO.getBio());
        assertNull(profileEditDTO.getPassword());
    }

}