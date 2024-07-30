package com.example.finalproject.controller;

import com.example.finalproject.service.MemberService;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    public MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberVO member) {
        try{
//            if(memberService.findByEmail(member.getEmail())!=null){
//                return ResponseEntity.badRequest().body("존재하는 아이디 입니다.");
//            }
            memberService.signup(member);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/findPW")
    public ResponseEntity<?> findByEmail(@RequestBody String email) {
        try{
            if(memberService.findByEmail(email)==null){
                return ResponseEntity.badRequest().body(0);
            }
            return ResponseEntity.ok(1);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("User email search failed: " + e.getMessage());
        }
    }

}
