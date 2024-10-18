package com.example.product_management.service;

import com.example.product_management.dto.UserDto;
import com.example.product_management.exception.ResourceNotFoundException;
import com.example.product_management.model.User;
import com.example.product_management.repository.UserRepository;
import com.example.product_management.request.CreateUserRequest;
import com.example.product_management.request.UpdateUserRequest;
import com.example.product_management.util.DataUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final DataUtil dataUtil;
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return  userRepository .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));
    }
    public User createUser(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .filter(user -> DataUtil.isValidEmail(request.getEmail()))
                .filter(user -> DataUtil.isNullOrZero(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new ResourceNotFoundException("Oops!" +request.getEmail() +" already exists!"));
    }

    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void deleteUser(Long userId) {
    userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
        throw new ResourceNotFoundException("User not found");
    });
    }

    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
