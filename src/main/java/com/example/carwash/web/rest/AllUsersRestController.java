package com.example.carwash.web.rest;

import com.example.carwash.model.view.AllUsersView;
import com.example.carwash.service.interfaces.ViewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/owner/users")
public class AllUsersRestController {

    private final ViewService viewService;

    @Autowired
    public AllUsersRestController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(200).body(viewService.getAllUsers());
    }

    @PostMapping("/ban/{id}")
    public ResponseEntity<?> banUnbanUser(@PathVariable Long id) {
        ResponseEntity<AllUsersView> body = ResponseEntity.status(200).body(viewService.banOrUnbanUser(id));
        String string = body.getBody().toString();
        return body;
    }

}
