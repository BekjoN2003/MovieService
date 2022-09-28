package com.example.test.controller;

import com.example.test.dto.auth.LoginDto;
import com.example.test.dto.auth.LoginResultDto;
import com.example.test.dto.auth.SignInDto;
import com.example.test.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInDto dto){
        String result = authService.singIn(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto dto){
         LoginResultDto resultDto = authService.login(dto);
        return ResponseEntity.ok(resultDto);
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<?> verification(@PathVariable ("token") String token){
        String result = authService.verification(token);
        return ResponseEntity.ok(result);
    }

}
