package com.example.finalproject.service;

import com.example.finalproject.mapper.OAuthMapper;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthService {

    @Autowired
    public OAuthMapper oAuthMapper;

    public MemberVO findByEmail(String email, String cate){
        return oAuthMapper.findByEmail(email,cate);
    }

    public void inset(MemberVO member){
        oAuthMapper.inset(member);
    }
}
