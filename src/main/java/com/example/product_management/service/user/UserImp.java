package com.example.product_management.service.user;

import com.example.product_management.dto.UserDto;
import com.example.product_management.exception.ResourceNotFoundException;
import com.example.product_management.model.User;
import com.example.product_management.repository.UserRepository;
import com.example.product_management.request.CreateUserRequest;
import com.example.product_management.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImp implements UserI{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return  userRepository .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));
    }
    @Override
    public User createUser(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new ResourceNotFoundException("Oops!" +request.getEmail() +" already exists!"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
    userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
        throw new ResourceNotFoundException("User not found");
    });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
