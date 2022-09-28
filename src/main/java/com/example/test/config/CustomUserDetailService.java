package com.example.test.config;


import com.example.test.entity.User;
import com.example.test.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo profileRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("Keldi: loadUserByUsername.");
        Optional<User> usersOptional = this.profileRepository.findByEmailAndDeletedAtIsNull(s);
        usersOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        User profile = usersOptional.get();
        System.out.println(profile);

        return new CustomUserDetails(profile);
    }
}
