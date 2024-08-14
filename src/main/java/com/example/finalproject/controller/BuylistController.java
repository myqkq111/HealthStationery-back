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
            int id = buylistService.insert(buyList);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/selectOne")
    public ResponseEntity<?> selectOne(@RequestParam int id){
        try {
            BuylistVO buylist = buylistService.selectSuccess(id);
            return ResponseEntity.ok(buylist);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
