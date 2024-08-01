package com.example.finalproject.controller;

import com.example.finalproject.jwt.JwtUtil;
import com.example.finalproject.vo.AuthRequestVO;
import com.example.finalproject.vo.AuthResponseVO;
import com.example.finalproject.vo.ErrorResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/member/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequestVO authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getName());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new AuthResponseVO(jwt));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseVO("Incorrect username or password"));
        }
    }
}
