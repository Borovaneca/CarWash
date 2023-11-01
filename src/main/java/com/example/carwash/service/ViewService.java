package com.example.carwash.service;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.model.view.StaffView;
import com.example.carwash.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ViewService {

    private UserRepository userRepository;

    public ViewService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<StaffView> getAllStaffViews() {
        List<StaffView> views = userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().size() > 1)
                .map(this::toStaffView)
                .toList();
        return views;
    }

    private StaffView toStaffView(User user) {
        StaffView staff = new StaffView();
        staff.setFullName(user.getFullName());
        staff.setImageUrl(user.getImageUrl());
        staff.setAge(user.getAge());
        staff.setPosition(getMajorRole(user.getRoles()));
        return staff;
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
