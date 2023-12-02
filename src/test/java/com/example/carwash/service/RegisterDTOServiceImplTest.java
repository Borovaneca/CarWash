package com.example.carwash.service;

import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterDTOServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SocialMediaServiceImpl socialMediaService;

    @Mock
    private VehicleServiceImpl vehicleService;

    @Mock
    private RoleServiceImpl roleService;

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
        assertNotEquals("newPassword", mockUser.getPassword());
        verify(userRepository, times(1)).save(mockUser);
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

    @Test
    void testAddSocialMediaShouldAdd() {
        User mockUser = getUser();


        SocialMediaAddDTO socialMediaAddDTO = new SocialMediaAddDTO();
        socialMediaAddDTO.setType("facebook");
        socialMediaAddDTO.setLink("https://www.facebook.com/ivan.asenov.754/");

        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setType(socialMediaAddDTO.getType());
        socialMedia.setLink(socialMediaAddDTO.getLink());
        socialMedia.setUser(mockUser);

        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        when(socialMediaService.addSocialMediaToUser(socialMediaAddDTO, mockUser)).thenReturn(socialMedia);
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        userService.addSocialMedia(mockUser.getUsername(), socialMediaAddDTO);

        assertEquals(1, mockUser.getSocialMedias().size());
        verify(userRepository, times(1)).save(mockUser);
        assertEquals("facebook", mockUser.getSocialMedias().get(0).getType());
    }

    @Test
    void testDeleteSocialMediaShouldDeleteIt() {
        User mockUser = getUser();

        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setType("facebook");
        socialMedia.setLink("https://www.facebook.com/ivan.asenov.754/");
        socialMedia.setUser(mockUser);

        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        when(socialMediaService.getByNameAndUser(socialMedia.getType(), mockUser)).thenReturn(socialMedia);
        doNothing().when(socialMediaService).delete(socialMedia);
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        userService.deleteSocialMedia(mockUser.getUsername(), socialMedia.getType());

        assertEquals(0, mockUser.getSocialMedias().size());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testAddVehicleToUserShouldAdd() {
        User mockUser = getUser();
        VehicleAddDTO vehicleAddDTO = createVehicleDTO();

        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        userService.addVehicleToUser(mockUser.getUsername(), vehicleAddDTO);

        assertEquals(1, mockUser.getVehicles().size());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testDeleteVehicleFromUserShouldRemoveIt() {
        User mockUser = getUser();
        VehicleAddDTO vehicleAddDTO = createVehicleDTO();
        Vehicle vehicle = createVehicle();

        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        userService.addVehicleToUser(mockUser.getUsername(), vehicleAddDTO);

        assertEquals(1, mockUser.getVehicles().size());
        verify(userRepository, times(1)).save(mockUser);


        mockUser.setVehicles(new ArrayList<>());
        mockUser.getVehicles().add(vehicle);
        vehicle.setUser(mockUser);
        when(vehicleService.findById(vehicle.getId())).thenReturn(vehicle);
        userService.removeVehicleFromUser(mockUser.getUsername(), vehicle.getId());

        assertEquals(0, mockUser.getVehicles().size());
        verify(userRepository, times(2)).save(mockUser);
    }

    @Test
    void testAddRoleToUserShouldAdd() {
        User mockUser = getUser();
        Role role = new Role();
        role.setId(1L);
        role.setName(RoleName.MANAGER);

        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(roleService.findByName(RoleName.valueOf("MANAGER"))).thenReturn(role);

        userService.addRoleToUserId("MANAGER", mockUser.getId());
        assertEquals(1, mockUser.getRoles().size());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testRemoveRoleFromUserIdShouldRemoveIt() {
        User mockUser = getUser();
        Role role = new Role();
        role.setId(1L);
        role.setName(RoleName.MANAGER);

        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(roleService.findByName(RoleName.valueOf("MANAGER"))).thenReturn(role);

        userService.addRoleToUserId("MANAGER", mockUser.getId());
        assertEquals(1, mockUser.getRoles().size());
        verify(userRepository, times(1)).save(mockUser);

        when(userRepository.save(mockUser)).thenReturn(mockUser);
        userService.removeRoleToUserId("MANAGER", mockUser.getId());

        assertEquals(0, mockUser.getRoles().size());

    }

    private Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setBrand("BMW");
        vehicle.setModel("X5");
        vehicle.setColor("Blue");
        return vehicle;
    }

    private VehicleAddDTO createVehicleDTO() {
        VehicleAddDTO vehicleAddDTO = new VehicleAddDTO();
        vehicleAddDTO.setBrand("BMW");
        vehicleAddDTO.setModel("X5");
        vehicleAddDTO.setColor("Blue");
        return vehicleAddDTO;
    }


    private User getUser() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("johndoe");
        mockUser.setEmail("john@example.com");
        mockUser.setPassword("topsecret");
        mockUser.setFirstName("Ivan");
        mockUser.setLastName("Asenov");
        mockUser.setCity("Sofia");
        mockUser.setAge(25);
        mockUser.setBio("Test Bio");
        mockUser.setRoles(new ArrayList<>());
        mockUser.setSocialMedias(new ArrayList<>());
        mockUser.setVehicles(new ArrayList<>());
        mockUser.setImage(null);
        mockUser.setActive(true);
        mockUser.setAppointments(new ArrayList<>());
        return mockUser;
    }
}