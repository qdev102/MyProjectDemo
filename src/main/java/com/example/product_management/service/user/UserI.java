package com.example.product_management.service.user;

import com.example.product_management.dto.UserDto;
import com.example.product_management.model.Product;
import com.example.product_management.model.User;
import com.example.product_management.request.CreateUserRequest;
import com.example.product_management.request.UpdateUserRequest;

import java.util.List;

public interface UserI {
    List<User> getAllUser();
    User getUserById (Long userId);
    User createUser (CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
