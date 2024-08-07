package com.example.finalproject.controller;

import com.example.finalproject.service.AdminMemberService;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminMember")
public class AdminMemberController {

    @Autowired
    AdminMemberService adminMemberService;

    @GetMapping("/selectAll")
    public ResponseEntity<?> selectAll() {
        try {
            List<MemberVO> list = adminMemberService.selectAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve the user list: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            adminMemberService.delete(id);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete the user: " + e.getMessage());
        }
    }






}
