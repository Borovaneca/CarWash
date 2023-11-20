package com.example.carwash.service.interfaces;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.enums.RoleName;

import java.util.List;

public interface RoleService {

    Role findByName(RoleName name);

    void saveAll(List<Role> roles);

    List<Role> findAll();
}
