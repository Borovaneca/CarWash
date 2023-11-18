package com.example.carwash.aop.initDB;

import com.example.carwash.model.entity.*;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.ProfileImageRepository;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class SeedDataBase {

    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final RoleRepository roleRepository;
    private final ServiceRepository serviceRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SeedDataBase(UserRepository userRepository, ProfileImageRepository profileImageRepository, RoleRepository roleRepository, ServiceRepository serviceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileImageRepository = profileImageRepository;
        this.roleRepository = roleRepository;
        this.serviceRepository = serviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private ProfileImage profileImage;

    public void registerAdmin() {
        if (this.userRepository.count() == 0) {
            profileImage = profileImageRepository.save(new ProfileImage("https://statusneo.com/wp-content/uploads/2023/02/MicrosoftTeams-image551ad57e01403f080a9df51975ac40b6efba82553c323a742b42b1c71c1e45f1.jpg"));
            List<Role> roles = new ArrayList<>();

            Arrays.stream(RoleName.values())
                    .forEach(name -> {
                        Role role = new Role();
                        role.setName(name);
                        roles.add(role);
                    });
            roleRepository.saveAll(roles);
            if (this.serviceRepository.count() == 0) {
                List<Service> services = new ArrayList<>();
                List<String> serviceNames = List.of("Interior Detailing", "Express Wash", "The Works Wash");
                serviceNames.forEach(name -> services.add(new Service(name)));
                serviceRepository.saveAll(services);
            }
            addAdmin();
        }
    }

    private void addAdmin() {
        Scanner scanner = new Scanner(System.in);
        User admin = new User();
//        System.out.println("Please insert admin username:");
        admin.setUsername("Admin");
//        System.out.println("Please insert admin password:");
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
        admin.setImage(profileImage);
        admin.setSocialMedias(getSocialMedias(admin));
        admin.setBio("Lorem ipsum dolor sit amet consectetur adipisicing elit. Blanditiis ducimus ad atque nulla. Reiciendis praesentium beatae quod cumque odit accusamus. Doloribus inventore voluptatem suscipit pariatur omnis aliquid non illo mollitia!");
        userRepository.save(admin);
    }

    private Set<SocialMedia> getSocialMedias(User admin) {
        Set<SocialMedia> socialMedias = new HashSet<>();
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
