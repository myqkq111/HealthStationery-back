package com.example.finalproject.controller;

import com.example.finalproject.service.BuylistService;
import com.example.finalproject.vo.BuylistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buylist")
public class BuylistController {

    @Autowired
    BuylistService buylistService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody BuylistVO buyList){
        try {
            buylistService.insert(buyList);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add item to cart: " + e.getMessage());
        }
    }

}
