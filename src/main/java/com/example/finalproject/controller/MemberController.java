package com.example.finalproject.controller;

import com.example.finalproject.jwt.JwtUtil;
import com.example.finalproject.service.MemberService;
import com.example.finalproject.vo.AuthResponseVO;
import com.example.finalproject.vo.ErrorResponseVO;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    public MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberVO member) {
        try{
            memberService.signup(member);
            return ResponseEntity.ok(2);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User registration failed: " + e.getMessage());
        }
    }
    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody String email) {
        try{
            if(memberService.findByEmail(email)!=null)
                return ResponseEntity.ok(1);
            else
                return ResponseEntity.ok(0);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/findPW")
    public ResponseEntity<?> findByEmail(@RequestBody String email) {
        try{
            if(memberService.findByEmail(email)!=null){
                return ResponseEntity.ok(1);
            }
            return ResponseEntity.ok(0);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("User email search failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody MemberVO member) throws Exception {
        member.setUsername(member.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(member.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new AuthResponseVO(jwt,memberService.findByEmail(jwtUtil.extractUsername(jwt))));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseVO("Incorrect username or password"));
        }
    }

}
