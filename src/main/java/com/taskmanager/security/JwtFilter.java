package com.taskmanager.security;

import com.taskmanager.entity.User;
import com.taskmanager.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private  final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authtoken = request.getHeader("Authorization");
        if(authtoken!=null && authtoken.startsWith("Bearer")){
            String tokenvalue=authtoken.substring(7,authtoken.length());
            System.out.println(tokenvalue+"        token value");
            String username = jwtUtil.getusername(tokenvalue);
            System.out.println(username+"        username");
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isPresent()){
                User appuser = user.get();
                System.out.println(user.get().getEmail());
                System.out.println(appuser.getUsername());
                System.out.println(appuser.getId());
                System.out.println(appuser.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appuser, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_"+(appuser.getRole()))));
            authenticationToken.setDetails( new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
