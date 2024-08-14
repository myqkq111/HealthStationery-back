package com.example.finalproject.service;

import com.example.finalproject.mapper.ReviewMapper;
import com.example.finalproject.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewMapper reviewMapper;

    public void insertReview(ReviewVO reviewVO){
        reviewMapper.insert(reviewVO);
    }

    public List<ReviewVO> selectByProductId(int productId){
        return reviewMapper.selectByProductId(productId);
    }
}
