package com.example.finalproject.service;

import com.example.finalproject.mapper.InqMapper;
import com.example.finalproject.vo.InqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InqService {

    @Autowired
    InqMapper inqMapper;

    public List<InqVO> selectAll(int productId){
        return inqMapper.selectAll(productId);
    }

    public void insert(InqVO inqVO){
        inqMapper.insert(inqVO);
    }
}
