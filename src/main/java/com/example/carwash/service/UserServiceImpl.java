package com.example.carwash.service;

import com.example.carwash.errors.UserNotFoundException;
import com.example.carwash.errors.VehicleInAppointmentException;
import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.*;
import com.example.carwash.model.entity.*;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.model.view.AllUsersView;
import com.example.carwash.model.view.ProfileView;
import com.example.carwash.model.view.StaffView;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileImageService profileImageService;
    private final SocialMediaService socialMediaService;
    private final CustomMapper customMapper;
    private final PasswordEncoder passwordEncoder;
    private final VehicleService vehicleService;
    private final RoleService roleService;
    private final ServiceRepository serviceRepository;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    private ProfileImage profileImage;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProfileImageService profileImageService,
                           SocialMediaServiceImpl socialMediaService, CustomMapper customMapper, PasswordEncoder passwordEncoder,
                           @Qualifier("vehicleServiceProxy") VehicleService vehicleService,
                           RoleService roleService, ServiceRepository serviceRepository) {
        this.userRepository = userRepository;
        this.profileImageService = profileImageService;
        this.socialMediaService = socialMediaService;
        this.customMapper = customMapper;
        this.passwordEncoder = passwordEncoder;
        this.vehicleService = vehicleService;
        this.roleService = roleService;
        this.serviceRepository = serviceRepository;
    }


    @Override
    public void update(ProfileEditDTO profileEditDTO) {
        Optional<User> userOptional = userRepository.findById(profileEditDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setUsername(profileEditDTO.getUsername());
            user.setEmail(profileEditDTO.getEmail());
            user.setFirstName(profileEditDTO.getFirstName());
            user.setLastName(profileEditDTO.getLastName());
            user.setCity(profileEditDTO.getCity());
            user.setAge(profileEditDTO.getAge());
            user.setBio(profileEditDTO.getBio());
            userRepository.save(user);
        }
    }

    @Override
    public ProfileEditDTO getUserAndMapToProfileEditDTO(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(customMapper::userToProfileEditDTO).orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found", username));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void updateImage(ProfileUpdateImageDTO profileUpdateImageDTO) {
        profileImageService.saveProfileImage(profileUpdateImageDTO.getImage(), findByUsername(profileUpdateImageDTO.getUsername()));
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

        if (!vehicle.getAppointments().isEmpty()) {
            throw new VehicleInAppointmentException();
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
            if (this.serviceRepository.count() == 0) {
                List<com.example.carwash.model.entity.Service> services = new ArrayList<>();
                List<String> serviceNames = List.of("Interior Detailing", "Express Wash", "The Works Wash");
                serviceNames.forEach(name -> services.add(new com.example.carwash.model.entity.Service(name)));
                serviceRepository.saveAll(services);
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

    @Override
    public List<StaffView> getAllStaffViews() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().size() > 1)
                .map(this::toStaffView)
                .toList();
    }

    @Override
    public ProfileView getProfileView(String username) {
        return userRepository.findByUsername(username)
                .map(this::toProfileView)
                .orElseThrow(() -> new UsernameNotFoundException("RegisterDTO not found"));
    }

    @Override
    public List<AllUsersView> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> {
                    AllUsersView userView = customMapper.userToAllUsersView(u);
                    userView.setRole(getMajorRole(u.getRoles()));
                    return userView;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AllUsersView banOrUnbanUser(Long id) {
        return userRepository.findById(id).map(user -> {
            user.setBanned(!user.isBanned());
            userRepository.save(user);
            AllUsersView allUsersView = customMapper.userToAllUsersView(user);
            allUsersView.setRole(getMajorRole(user.getRoles()));
            return allUsersView;
        }).orElse(null);
    }

    @Override
    public boolean isAuthorized(UserDetails userDetails, String username) {
        return !userDetails.getAuthorities().
                stream().
                filter(a -> a.getAuthority().equals("ROLE_OWNER")).
                collect(Collectors.toSet()).isEmpty() || username.equals(userDetails.getUsername());
    }

    @Override
    public boolean isAuthorized(UserDetails userDetails, Long userId) {
        return !userDetails.getAuthorities().
                stream().
                filter(a -> a.getAuthority().equals("ROLE_OWNER")).
                collect(Collectors.toSet()).isEmpty() || userRepository.findById(userId).map(user -> user.getUsername().equals(userDetails.getUsername())).orElse(false);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {user.getVehicles().forEach(vehicleService::delete);
        userRepository.delete(user);
    });
    }

    @Override
    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        User user = userRepository.findByUsername(username).get();
        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getPassword()));
        userRepository.save(user);
    }

    private ProfileView toProfileView(User user) {
        ProfileView profileView = customMapper.userToProfileView(user);
        profileView.setSocials(user.getSocialMedias().stream().map(customMapper::socialToSocialMediaView)
                .collect(Collectors.toSet()));
        profileView.setRole(getMajorRole(user.getRoles()));
        return profileView;
    }

    private StaffView toStaffView(User user) {
        StaffView staff = customMapper.userToStaffView(user);
        staff.setPosition(getMajorRole(user.getRoles()));
        return staff;
    }

    private void addAdmin() {
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

    private String getMajorRole(List<Role> roles) {
        Map<RoleName, Integer> rolePriorityMap = Map.of(
                RoleName.USER, 0,
                RoleName.EMPLOYEE, 1,
                RoleName.MANAGER, 2,
                RoleName.OWNER, 3
        );
        Role majorRole = roles.get(0);
        for (Role role : roles) {
            if (rolePriorityMap.get(role.getName()) > rolePriorityMap.get(majorRole.getName())) {
                majorRole = role;
            }
        }
        return majorRole.getName().name();
    }
}
