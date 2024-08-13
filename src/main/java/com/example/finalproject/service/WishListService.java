package com.example.finalproject.service;

import com.example.finalproject.mapper.WishListMapper;
import com.example.finalproject.vo.LikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    @Autowired
    public WishListMapper wishListMapper;

    public int isLikedMember(int id, int uid){
        return wishListMapper.isLikedMember(id,uid).orElse(0);
    }

    public void likeTrue(LikeVO likeVO){
        wishListMapper.likeTrue(likeVO);
    }

    public void likeFalse(LikeVO likeVO){
        wishListMapper.likeFalse(likeVO);
    }

    public List<LikeVO> view(int id){
        return wishListMapper.view(id);
    }
}
