package com.example.test.service;

<<<<<<< HEAD
import com.example.test.config.JwtTokenUtil;
=======
import com.example.test.utill.JwtTokenUtil;
>>>>>>> origin/main
import com.example.test.dto.auth.LoginDto;
import com.example.test.dto.auth.LoginResultDto;
import com.example.test.dto.auth.SignInDto;
import com.example.test.entity.Role;
import com.example.test.entity.User;
import com.example.test.exception.BadRequest;
import com.example.test.repository.UserRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
<<<<<<< HEAD
    private final JwtTokenUtil jwtTokenUtil;
=======
    private final JwtTokenUtil  jwtTokenUtil;
>>>>>>> origin/main
    private final MailSenderService mailSenderService;
    @Value(" ${auth.verification.api}")
    private String verificationLinc ;


    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, MailSenderService mailSenderService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mailSenderService = mailSenderService;
    }

    public String singIn(SignInDto dto) {
        Optional<User> optional = userRepo.findByEmailAndDeletedAtIsNull(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequest("User whit this email already exist");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.ROLE_USER);
        userRepo.save(user);
        if (sendMessageToEmail(user)){
            return "Please confirm your email";
        }
        return "Failed to send message";
    }

    public boolean sendMessageToEmail(User user) {
        String token = jwtTokenUtil.generateAccessToken(user.getId(), user.getEmail());
        String link = verificationLinc + token;
        String subject = "Movie Service Verification";
        String content = "Click for verification this link: " + link;
        try {
            mailSenderService.send(user.getEmail(), subject, content);
        } catch (Exception e) {
            userRepo.delete(user);
            return false;
        }

        return true;
    }
    public LoginResultDto login(LoginDto dto) {
        Optional<User> optional = userRepo.findByEmailAndDeletedAtIsNull(dto.getEmail());
        if (optional.isEmpty()) {
            throw new BadRequest("User not found");
        }

        User user = optional.get();
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadRequest("User's password not found");
        }
        String token = jwtTokenUtil.generateAccessToken(user.getId(), user.getEmail());
        return new LoginResultDto(user.getEmail(), token);
    }


    public String verification(String token) {
        if (!jwtTokenUtil.validate(token)) {
            throw new BadRequest("Token invalid--");
        }
        String email = jwtTokenUtil.getUsername(token);
        Optional<User> optional = userRepo.findByEmailAndDeletedAtIsNull(email);
        if (optional.isEmpty()) {
            throw new BadRequest("User not found");
            }

        User user = optional.get();
        if (user.getEmailVerifiedAt() != null) {
            throw new BadRequest("User already verified");
        }

        user.setEmailVerifiedAt(LocalDateTime.now());
        userRepo.save(user);
        return "Successful verified";
    }

}
