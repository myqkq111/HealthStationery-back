package com.example.finalproject.controller;

import com.example.finalproject.service.BasketService;
import com.example.finalproject.vo.BasketVO;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.POST;

@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    BasketService basketService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody BasketVO basket) {
        try {
            basketService.insert(basket);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add item to cart: " + e.getMessage());
        }
    }

}
