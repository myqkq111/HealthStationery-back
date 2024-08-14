package com.example.finalproject.controller;

import com.example.finalproject.service.InqService;
import com.example.finalproject.vo.InqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class InqController {

    @Autowired
    InqService inqService;

    @PostMapping("/inq")
    public ResponseEntity<?> insertInq(@RequestBody InqVO inq) {
        try{
            System.out.println(inq);
            inqService.insert(inq);
            return ResponseEntity.ok(1);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
