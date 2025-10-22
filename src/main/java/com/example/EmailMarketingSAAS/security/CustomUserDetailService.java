package com.example.EmailMarketingSAAS.security;

import com.example.EmailMarketingSAAS.entity.User;
import com.example.EmailMarketingSAAS.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService {

    public final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(email));
        if (user.isEmpty() && user!=null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.get().getRoles().toString()))
        );
    }
}
