package com.example.finalproject.controller;

import com.example.finalproject.service.ReviewService;
import com.example.finalproject.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody ReviewVO review) {
        try{
            reviewService.insertReview(review);
            return ResponseEntity.ok("리뷰 작성이 정상적으로 처리되었습니다.");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("리뷰 작성 실패"+e.getMessage());
        }
    }

    @GetMapping("/product")
    public ResponseEntity<?> selectReviewByProductId(@RequestParam(required = false) Integer productId) {
        try{
            List<ReviewVO> reviewList = reviewService.selectByProductId(productId);
            return ResponseEntity.ok(reviewList);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("해당 상품 리뷰를 가져오지 못했습니다. "+e.getMessage());
        }
    }
}
