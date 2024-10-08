package com.example.finalproject.controller;

import com.example.finalproject.service.WishListService;
import com.example.finalproject.vo.LikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            wishListService.likeTrue2(likeVO);
            return ResponseEntity.ok(1);
        }catch(Exception e) {
            return ResponseEntity.badRequest().body("찜추가 오류"+e.getMessage());
        }
    }
    @DeleteMapping("/remove")
    public ResponseEntity<?> likeFalse(@RequestBody LikeVO likeVO){
        try{
            wishListService.likeFalse(likeVO);
            wishListService.likeFalse2(likeVO);
            return ResponseEntity.ok(1);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("찜해제 오류"+e.getMessage());
        }
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewWishList(@RequestParam int id){
        try{
            List<LikeVO> list = wishListService.view(id);
            return ResponseEntity.ok(list);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("wishList 목록 오류"+e.getMessage());
        }
    }


}
