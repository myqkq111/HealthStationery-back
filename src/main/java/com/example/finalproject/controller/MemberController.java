package com.example.finalproject.controller;

import com.example.finalproject.service.MemberService;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @Autowired
    public MemberService memberService;
    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody MemberVO member) {
        try{
            memberService.signup(member);
//            return ResponseEntity.ok().build();
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            System.out.println("FAILD");
            return ResponseEntity.badRequest().body("User registration failed: " + e.getMessage());
        }
    }
}
