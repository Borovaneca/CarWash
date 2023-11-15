package com.example.carwash.init;

import com.example.carwash.model.entity.*;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class InitDB implements CommandLineRunner {

    private final ServiceRepository serviceRepository;
    private final ProfileImageRepository profileImageRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public InitDB(ServiceRepository serviceRepository, ProfileImageRepository profileImageRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {

        this.serviceRepository = serviceRepository;
        this.profileImageRepository = profileImageRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private ProfileImage profileImage;
    @Override
    public void run(String... args) throws Exception {
        if (this.userRepository.count() == 0) {
            profileImage = profileImageRepository.save(new ProfileImage().setLocatedOn("http://localhost:8080/images/Borovaneca/69713466_3289830984390387_2336911265034665984_n.jpg"));
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
        User admin = new User();
        admin.setUsername("Borovaneca");
        admin.setPassword(passwordEncoder.encode("Borovaneca1"));
        admin.setRoles(roleRepository.findAll());
        admin.setCity("Vratsa");
        admin.setActive(true);
        admin.setAge(23);
        admin.setEmail("borovaneca@abv.bg");
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
