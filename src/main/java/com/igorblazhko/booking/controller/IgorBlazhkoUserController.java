package com.igorblazhko.booking.controller;

import com.igorblazhko.booking.dto.user.IgorBlazhkoUserResponse;
import com.igorblazhko.booking.dto.user.IgorBlazhkoUserUpdateRequest;
import com.igorblazhko.booking.service.IgorBlazhkoUserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class IgorBlazhkoUserController {

    private final IgorBlazhkoUserService userService;

    @GetMapping
    public ResponseEntity<List<IgorBlazhkoUserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IgorBlazhkoUserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IgorBlazhkoUserResponse> updateUser(@PathVariable Long id,
                                                              @Valid @RequestBody IgorBlazhkoUserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}