package com.example.carwash.web.rest;

import com.example.carwash.model.view.AllUsersView;
import com.example.carwash.service.interfaces.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner/users")
public class AllUsersRestController {

    private final ViewService viewService;

    @Autowired
    public AllUsersRestController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AllUsersView>> getAllUsers() {
        return ResponseEntity.ok(viewService.getAllUsers());
    }

    @PostMapping("/ban/{id}")
    public AllUsersView banUser(@PathVariable Long id) {
        return viewService.banUser(id);
    }

}
