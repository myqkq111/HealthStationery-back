package com.example.finalproject.service;

import com.example.finalproject.mapper.OAuthMapper;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthService {

    @Autowired
    public OAuthMapper oAuthMapper;

    public MemberVO naverEmailSelect(String email){
        return oAuthMapper.naverEmailSelect(email);
    }

    public void naverSingUp(MemberVO member){
        oAuthMapper.naverSingUp(member);
    }


    public MemberVO kakaoIdSelect(long kakaoId){
        return oAuthMapper.kakaoIdSelect(kakaoId);
    }

    public void kakaoSingUp(MemberVO member){
        oAuthMapper.kakaoSingUp(member);
    }

}
