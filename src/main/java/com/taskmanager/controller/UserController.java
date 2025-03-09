package com.taskmanager.controller;

import com.taskmanager.Exception.NotFoundException;
import com.taskmanager.entity.User;
import com.taskmanager.payload.LoginDto;
import com.taskmanager.payload.Tokendto;
import com.taskmanager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.ScatteringByteChannel;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/login")
    public  ResponseEntity<?> login(
            @RequestBody LoginDto loginDto
    ){
        Tokendto token = userService.verifylogin(loginDto);
        if (token!=null){
            return new ResponseEntity<>(token, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("invalid username and password", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User userDetails) {

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}
