package com.example.finalproject.controller;

import com.example.finalproject.service.BasketService;
import com.example.finalproject.vo.BasketVO;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.POST;

import java.util.List;

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

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody BasketVO basket) {
        try {
            BasketVO check = basketService.check(basket);
            if(check != null){
                return ResponseEntity.ok(check.getId());
            } else{
                return ResponseEntity.ok(0);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add item to cart: " + e.getMessage());
        }
    }

    @PutMapping("/countup")
    public ResponseEntity<?> countup(@RequestParam int id) {
        try {
                basketService.update(id);
                return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to increase quantity: " + e.getMessage());
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<?> selectByMemberId(@RequestParam int memberId) {
        try {
            List<BasketVO> list = basketService.selectByMemberId(memberId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve cart items: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam int id) {
        try {
            basketService.delete(id);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove item from the cart: " + e.getMessage());
        }
    }

}
