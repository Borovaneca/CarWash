package com.example.carwash.service;

import com.example.carwash.errors.UserNotFoundException;
import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.ProfileUpdateImageDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.model.entity.*;
import com.example.carwash.events.events.ForgotPasswordEvent;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileImageService profileImageService;
    private final SocialMediaService socialMediaService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileImageServiceImpl profileImageServiceImpl;
    private final VehicleService vehicleService;
    private final RoleService roleService;
    private final ServiceService serviceService;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    private ProfileImage profileImage;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProfileImageService profileImageService,
                           SocialMediaServiceImpl socialMediaService, PasswordEncoder passwordEncoder,
                           ProfileImageServiceImpl profileImageServiceImpl,
                            VehicleServiceImpl vehicleService,
                           RoleService roleService, ServiceService serviceService) {
        this.userRepository = userRepository;
        this.profileImageService = profileImageService;
        this.socialMediaService = socialMediaService;
        this.passwordEncoder = passwordEncoder;
        this.profileImageServiceImpl = profileImageServiceImpl;
        this.vehicleService = vehicleService;
        this.roleService = roleService;
        this.serviceService = serviceService;
    }



    @Override
    public void update(ProfileEditDTO profileEditDTO) {
        Optional<User> userOptional = userRepository.findById(profileEditDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getUsername().equals(profileEditDTO.getUsername())) {
                user.setUsername(profileEditDTO.getUsername());
            }
            if (!user.getEmail().equals(profileEditDTO.getEmail())) {
                user.setEmail(profileEditDTO.getEmail());
            }
            user.setFirstName(profileEditDTO.getFirstName());
            user.setLastName(profileEditDTO.getLastName());
            user.setCity(profileEditDTO.getCity());
            user.setAge(profileEditDTO.getAge());
            user.setBio(profileEditDTO.getBio());
            user.setPassword(passwordEncoder.encode(profileEditDTO.getPassword()));
            userRepository.save(user);
        }
    }
    @Override
    public ProfileEditDTO getUserAndMapToProfileEditDTO(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(this::mapToProfileEditDTO).orElseThrow(() -> new UserNotFoundException("Username: " + username + " not found", username));
    }

    private ProfileEditDTO mapToProfileEditDTO(User user) {
        ProfileEditDTO profileEditDTO = new ProfileEditDTO();
        profileEditDTO.setId(user.getId());
        profileEditDTO.setUsername(user.getUsername());
        profileEditDTO.setPassword(null);
        profileEditDTO.setConfirmPassword(null);
        profileEditDTO.setEmail(user.getEmail());
        profileEditDTO.setFirstName(user.getFirstName());
        profileEditDTO.setLastName(user.getLastName());
        profileEditDTO.setCity(user.getCity());
        profileEditDTO.setAge(user.getAge());
        profileEditDTO.setBio(user.getBio());
        return profileEditDTO;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void updateImage(ProfileUpdateImageDTO profileUpdateImageDTO) {
        profileImageServiceImpl.saveProfileImage(profileUpdateImageDTO.getImage(), findByUsername(profileUpdateImageDTO.getUsername()));
    }

    @Override
    public void addSocialMedia(String username, SocialMediaAddDTO socialMediaAddDTO) {
        userRepository
                .findByUsername(username)
                .ifPresent(
                        user -> {
                            user.getSocialMedias().add(socialMediaService.addSocialMediaToUser(socialMediaAddDTO, user
                            ));
                            userRepository.save(user);
                        });
    }

    @Override
    public User findByEmail(String email) {
       return userRepository.findByEmail(email).orElse(null);
    }


    @Override
    public void deleteSocialMedia(String username, String socialName) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            SocialMedia media = socialMediaService.getByNameAndUser(socialName, user);
            user.getSocialMedias().remove(media);
            userRepository.save(user);
            socialMediaService.delete(media);
        }
    }

    private Vehicle mapToVehicleFromDTO(VehicleAddDTO vehicleAddDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(vehicleAddDTO.getBrand());
        vehicle.setModel(vehicleAddDTO.getModel());
        vehicle.setColor(vehicleAddDTO.getColor());
        return vehicle;
    }

    @Override
    public void addVehicleToUser(String username, VehicleAddDTO vehicleAddDTO) {
        Vehicle vehicle = mapToVehicleFromDTO(vehicleAddDTO);
        User user = findByUsername(username);
        user.getVehicles().add(vehicle);
        vehicle.setUser(user);
        userRepository.save(user);
        vehicleService.save(vehicle);

    }

    @Override
    public boolean removeVehicleFromUser(String username, Long id) {
        User user = findByUsername(username);
        Vehicle vehicle = vehicleService.findById(id);
        if (vehicle == null) {
            return false;
        }

        user.getVehicles().remove(vehicle);
        vehicleService.delete(vehicle);
        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void addRoleToUserId(String role, Long userId) {
        Role roleEntity = roleService.findByName(RoleName.valueOf(role));
        if (roleEntity != null) {
            userRepository.findById(userId).ifPresent(user -> {
                if (user.getRoles().contains(roleEntity)) return;
                user.getRoles().add(roleEntity);
                userRepository.save(user);
            });
        }

    }

    @Override
    public void banUserById(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setBanned(true);
            userRepository.save(user);
        });
    }

    @Override
    public void removeRoleToUserId(String role, Long userId) {
        Role roleEntity = roleService.findByName(RoleName.valueOf(role));
        if (roleEntity != null) {
            userRepository.findById(userId).ifPresent(user -> {
                if (user.getRoles().contains(roleEntity)) {
                    user.getRoles().remove(roleEntity);
                    userRepository.save(user);
                }
            });
        }
    }

    @Override
    public void registerAdmin() {
        if (this.userRepository.count() == 0) {
            profileImage = profileImageService.save(new ProfileImage("https://statusneo.com/wp-content/uploads/2023/02/MicrosoftTeams-image551ad57e01403f080a9df51975ac40b6efba82553c323a742b42b1c71c1e45f1.jpg"));
            List<Role> roles = new ArrayList<>();

            Arrays.stream(RoleName.values())
                    .forEach(name -> {
                        Role role = new Role();
                        role.setName(name);
                        roles.add(role);
                    });
            roleService.saveAll(roles);
            if (this.serviceService.allServices() == 0) {
                List<com.example.carwash.model.entity.Service> services = new ArrayList<>();
                List<String> serviceNames = List.of("Interior Detailing", "Express Wash", "The Works Wash");
                serviceNames.forEach(name -> services.add(new com.example.carwash.model.entity.Service(name)));
                serviceService.saveAll(services);
            }
            addAdmin();
        }
    }

    @Override
    public Optional<List<User>> findInactiveUsersMoreThan7Days() {
        return userRepository.findInactiveUsersMoreThan7Days();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    private void addAdmin() {
        Scanner scanner = new Scanner(System.in);
        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRoles(roleService.findAll());
        admin.setCity("Vratsa");
        admin.setActive(true);
        admin.setAge(23);
        admin.setEmail("borovaneca@softuni.bg");
        admin.setFirstName("Petyo");
        admin.setLastName("Veselinov");
        admin.setBanned(false);
        admin.setRegisteredOn(LocalDate.now());
        admin.setImage(profileImage);
        admin.setSocialMedias(getSocialMedias(admin));
        admin.setBio("Lorem ipsum dolor sit amet consectetur adipisicing elit. Blanditiis ducimus ad atque nulla. Reiciendis praesentium beatae quod cumque odit accusamus. Doloribus inventore voluptatem suscipit pariatur omnis aliquid non illo mollitia!");
        userRepository.save(admin);
    }

    private List<SocialMedia> getSocialMedias(User admin) {
        List<SocialMedia> socialMedias = new ArrayList<>();
        SocialMedia facebook = new SocialMedia();
        facebook.setType("facebook");
        facebook.setLink("https://www.facebook.com/Borovaneca");
        facebook.setUser(admin);
        socialMedias.add(facebook);
        SocialMedia github = new SocialMedia();
        github.setType("github");
        github.setLink("https://github.com/Borovaneca");
        github.setUser(admin);
        socialMedias.add(github);
        return socialMedias;
    }
}
