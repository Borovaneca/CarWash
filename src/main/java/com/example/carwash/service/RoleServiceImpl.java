package com.example.carwash.service;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public void saveAll(List<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
