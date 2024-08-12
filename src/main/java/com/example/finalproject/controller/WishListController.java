package com.example.finalproject.controller;

import com.example.finalproject.service.WishListService;
import com.example.finalproject.vo.LikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    public WishListService wishListService;

    @PostMapping("/add")
    public ResponseEntity<?> likeTrue(@RequestBody LikeVO likeVO){
        try {
            System.out.println(likeVO);
            wishListService.likeTrue(likeVO);
            return ResponseEntity.ok(1);
        }catch(Exception e) {
            return ResponseEntity.badRequest().body("찜추가 오류"+e.getMessage());
        }
    }
    @DeleteMapping("/remove")
    public ResponseEntity<?> likeFalse(@RequestBody LikeVO likeVO){
        try{
            wishListService.likeFalse(likeVO);
            return ResponseEntity.ok(1);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("찜해제 오류"+e.getMessage());
        }
    }


}
