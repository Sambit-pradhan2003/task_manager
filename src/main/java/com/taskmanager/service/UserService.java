package com.taskmanager.service;

import com.taskmanager.entity.User;
import com.taskmanager.payload.LoginDto;
import com.taskmanager.payload.Tokendto;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }
    public User registerUser(User user) {
        String enpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(enpw);
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {


        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            String token = jwtUtil.generateToken(username);
            System.out.println(token);
            String nammm = jwtUtil.getusername(token);
            System.out.println(nammm);
        }
        return byUsername;

    }

    public Tokendto verifylogin(LoginDto loginDto) {
        Optional<User> byUsername = userRepository.findByUsername(loginDto.getUsername());
        if (byUsername.isPresent()) {
            boolean checkpw = BCrypt.checkpw(loginDto.getPassword(), byUsername.get().getPassword());
            Tokendto token = new Tokendto();
            if (checkpw){
                //generate toke
                String token1 = jwtUtil.generateToken(byUsername.get().getUsername());
                token.setToken(token1);
                token.setType("jwt");
                return token;
            }else {
                return null;
            }
        }else {
            return null;}
    }
}
