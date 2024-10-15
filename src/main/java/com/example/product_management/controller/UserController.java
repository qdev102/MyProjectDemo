package com.example.product_management.controller;

import com.example.product_management.dto.ProductDto;
import com.example.product_management.dto.UserDto;
import com.example.product_management.exception.ResourceNotFoundException;
import com.example.product_management.model.Category;
import com.example.product_management.model.Product;
import com.example.product_management.model.User;
import com.example.product_management.request.CreateUserRequest;
import com.example.product_management.request.UpdateUserRequest;
import com.example.product_management.response.ApiResponse;
import com.example.product_management.service.user.UserI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserI userService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUser() {
        List<User> users = userService.getAllUser();
        List<UserDto> convertedUsers = users.stream()
                .map(userService::convertUserToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse("success", convertedUsers));
    }

    @GetMapping("/{uId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long uId) {
        try {
            User user = userService.getUserById(uId);
            return ResponseEntity.ok(new ApiResponse("success", user));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create user succeefuly@", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}


