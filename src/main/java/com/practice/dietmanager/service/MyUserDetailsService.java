package com.practice.dietmanager.service;

import com.practice.dietmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.practice.dietmanager.domain.entity.User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("없는 회원 입니다..."));

        return User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRole().name()).build();
    }
}
