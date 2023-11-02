package com.example.carwash.init;

import com.example.carwash.model.entity.*;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InitDB implements CommandLineRunner {

    private final ServiceRepository serviceRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public InitDB(ServiceRepository serviceRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {

        this.serviceRepository = serviceRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.userRepository.count() == 0) {
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
            addManager();
            addEmployee();
            addClient();
        }
    }

    private void addClient() {
        User user = new User();
        user.setUsername("Svetoslav");
        user.setAge(23);
        user.setPassword(passwordEncoder.encode("Svetoslav"));
        user.setFirstName("Svetoslav");
        user.setLastName("Petrov");
        user.setEmail("svetoslav@abv.bg");
        user.setCity("Vratsa");
        user.setRegisteredOn(LocalDate.now());
        user.setRoles(roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().name().equals("USER"))
                .collect(Collectors.toList()));
        userRepository.save(user);
    }

    private void addEmployee() {
        User detailer = new User();
        detailer.setUsername("Detailer");
        detailer.setPassword(passwordEncoder.encode("detailer"));
        detailer.setRoles(roleRepository.findAll()
                .stream()
                .filter(name -> !name.getName().name().equals("OWNER") &&
                        !name.getName().name().equals("MANAGER"))
                .collect(Collectors.toList()));
        detailer.setCity("Vratsa");
        detailer.setAge(23);
        detailer.setEmail("borovanec@abv.bg");
        detailer.setFirstName("Ivan");
        detailer.setLastName("Ivanov");
        detailer.setRegisteredOn(LocalDate.now());
        userRepository.save(detailer);
    }

    private void addManager() {
        User manager = new User();
        manager.setUsername("Manager");
        manager.setPassword(passwordEncoder.encode("manager"));
        manager.setRoles(roleRepository.findAll()
                .stream()
                .filter(name -> !name.getName().name().equals("OWNER"))
                .collect(Collectors.toList()));
        manager.setCity("Vratsa");
        manager.setEmail("borovane@abv.bg");
        manager.setFirstName("Ivan");
        manager.setLastName("Ivanov");
        manager.setAge(23);
        manager.setRegisteredOn(LocalDate.now());
        userRepository.save(manager);
    }

    private void addAdmin() {
        User admin = new User();
        admin.setUsername("Borovaneca");
        admin.setPassword(passwordEncoder.encode("Borovaneca1"));
        admin.setRoles(roleRepository.findAll());
        admin.setCity("Vratsa");
        admin.setAge(23);
        admin.setEmail("borovaneca@abv.bg");
        admin.setFirstName("Petyo");
        admin.setLastName("Veselinov");
        admin.setRegisteredOn(LocalDate.now());
       userRepository.save(admin);
    }
}
