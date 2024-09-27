package com.practice.dietmanager.service;

import com.practice.dietmanager.domain.entity.User;
import com.practice.dietmanager.domain.entity.UserJoinRequestDTO;
import com.practice.dietmanager.domain.entity.UserRole;
import com.practice.dietmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(UserJoinRequestDTO userJoinRequestDTO) {
        User user = new User();
        user.setRole(UserRole.USER);
        user.setEmail(userJoinRequestDTO.getEmail());
        user.setUsername(userJoinRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userJoinRequestDTO.getPassword()));
        userRepository.save(user);

    }

    public User registerUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get();
    }


}
