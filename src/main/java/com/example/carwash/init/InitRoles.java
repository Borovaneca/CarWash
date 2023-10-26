package com.example.carwash.init;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitRoles implements CommandLineRunner {


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public InitRoles(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.roleRepository.count() == 0) {
            List<Role> roles = new ArrayList<>();

            Arrays.stream(RoleName.values())
                    .forEach(name -> {
                        Role role = new Role();
                        role.setName(name);
                        roles.add(role);
                    });
            roleRepository.saveAll(roles);
            addAdmin();
        }
    }

    private void addAdmin() {
        User admin = new User();
        PasswordEncoder passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        admin.setUsername("Borovaneca");
        admin.setPassword(passwordEncoder.encode("Borovaneca1"));
        admin.setRoles(roleRepository.findAll());
        admin.setCity("Vratsa");
        admin.setEmail("borovaneca@abv.bg");
        admin.setFirstName("Petyo");
        admin.setLastName("Veselinov");
        admin.setRegisteredOn(LocalDate.now());
        admin.setActive(true);
        userRepository.save(admin);
    }
}
