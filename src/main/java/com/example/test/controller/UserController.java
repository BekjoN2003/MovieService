package com.example.test.controller;

import com.example.test.dto.user.UserCreateDto;
import com.example.test.dto.user.UserDto;
import com.example.test.dto.user.UserFilterDto;
import com.example.test.service.UserService;
import com.example.test.utill.SpringSecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateDto dto) {
        UserDto result = userService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id) {
        UserDto result = userService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterUser(@RequestBody @Valid UserFilterDto dto) {
        List<UserDto> result = userService.filter(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        UserDto resul = userService.get(SpringSecurityUtil.getUserId());
        return ResponseEntity.ok(resul);
    }

    @GetMapping("/secured/getAll")
    public ResponseEntity<?> getAll(@RequestParam("page")Integer page,
                                    @RequestParam("size")Integer size){
        List<UserDto> result = userService.getAll(page, size);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/secured/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid UserCreateDto dto) {
        UserDto result = userService.update(SpringSecurityUtil.getUserId(), dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        boolean result = userService.delete(id);
        return ResponseEntity.ok(result);
    }


    // TODO: Block  User (Admin)
    @GetMapping("/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable("id") Integer id){
        String result = userService.block(id);
        return ResponseEntity.ok(result);
    }


    // TODO: Update User Info (Admin)
    @PutMapping("/secured/update/{id}")
    public ResponseEntity<?> updateProfileAdmin(@PathVariable("id") Integer id,
                                                  @RequestBody @Valid UserCreateDto dto) {
        UserDto result = userService.update(id, dto);
        return ResponseEntity.ok(result);

    }

    // TODO: Change Password
    @PutMapping("/password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") Integer id,
                                            @RequestParam @Valid UserDto dto){
        UserDto result = userService.password(id, dto);
        return ResponseEntity.ok(result);
    }

    // TODO: Update to Admin (ADMIN)


    // TODO: Update to User (SUPER USER)



}
