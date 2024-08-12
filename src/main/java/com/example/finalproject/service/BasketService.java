package com.example.finalproject.service;

import com.example.finalproject.mapper.BasketMapper;
import com.example.finalproject.vo.BasketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    @Autowired
    BasketMapper basketMapper;

    public void insert(BasketVO basket){
        basketMapper.insert(basket);
    }

    public BasketVO check(BasketVO basket){
        return basketMapper.check(basket);
    }

    public List<BasketVO> selectByMemberId(int memberId){
        return basketMapper.selectByMemberId(memberId);
    }

    public void update(int id){
        basketMapper.update(id);
    }



}
