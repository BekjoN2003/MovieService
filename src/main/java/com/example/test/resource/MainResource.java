package com.example.test.resource;


import com.example.test.dto.auth.LoginDto;
import com.example.test.dto.auth.LoginResultDto;
import com.example.test.service.AuthService;
import com.example.test.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainResource {
    private final MovieService movieService;
    private final AuthService authService;

    public MainResource(MovieService movieService, AuthService authService) {
        this.movieService = movieService;
        this.authService = authService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", movieService.getAll(0, 10));
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(Model model, @ModelAttribute("user") LoginDto dto) {
        LoginResultDto result = authService.login(dto);
        return "redirect:admin";
    }


    @GetMapping("/sing-up")
    public String singUp(Model model) {
        return "sing-up";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin";
    }
}
