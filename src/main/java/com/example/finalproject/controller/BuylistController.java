package com.example.finalproject.controller;

import com.example.finalproject.service.BuylistService;
import com.example.finalproject.vo.BuylistVO;
import com.example.finalproject.vo.SelectBuylistVO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/mypage")
    public ResponseEntity<?> myPage(@RequestParam int id){
        try {
            List<SelectBuylistVO> list = buylistService.selectBuylist(id);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam int id, @RequestParam int pid, @RequestParam String color, @RequestParam String size, @RequestParam int count){
        try {
            buylistService.delete(id, pid, color, size, count);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
