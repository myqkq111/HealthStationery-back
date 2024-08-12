package com.example.finalproject.controller;

import com.example.finalproject.jwt.JwtUtil;
import com.example.finalproject.service.CustomUserDetailsService;
import com.example.finalproject.service.MemberService;
import com.example.finalproject.service.OAuthService;
import com.example.finalproject.vo.AuthResponseVO;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    OAuthService oAuthService;

    @Autowired
    MemberService memberService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String cate){
        MemberVO member = oAuthService.findByEmail(email, cate);
        String jwt = "";
        if (member == null) {
            if(memberService.findByEmail(email) == null){
                jwt = "no";
            } else{
                jwt = "yes";
            }
        } else{
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            jwt = jwtUtil.generateToken(userDetails.getUsername());
        }
        return ResponseEntity.ok(new AuthResponseVO(jwt,member));
    }
}