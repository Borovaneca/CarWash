package com.example.carwash.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllUsersView {

    private String id;

    private String locatedOn;

    private String username;

    private String email;

    private String role;

    private String age;

    private String registeredOn;

    private String isBanned;

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", locatedOn='" + locatedOn + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", age='" + age + '\'' +
                ", registeredOn='" + registeredOn + '\'' +
                ", isBanned='" + isBanned + '\'' +
                '}';
    }

}
