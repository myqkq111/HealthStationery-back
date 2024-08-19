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

    public List<InqVO> selectAdmin(){
        return inqMapper.selectAdmin();
    }

    public void deleteInq(int id){ inqMapper.delete(id); }

    public void updateComment(InqVO inq){
//        System.out.println("서비스 : "+inq);
        inqMapper.updateComment(inq); }
}
