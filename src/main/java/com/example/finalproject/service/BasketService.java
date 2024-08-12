package com.example.finalproject.service;

import com.example.finalproject.mapper.BasketMapper;
import com.example.finalproject.vo.BasketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired
    BasketMapper basketMapper;

    public void insert(BasketVO basket){
        basketMapper.insert(basket);
    }

}
