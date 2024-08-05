package com.example.finalproject.service;

import com.example.finalproject.mapper.MemberMapper;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MemberService {
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public MemberMapper memberMapper;

    @Autowired
    public OAuthService oAuthService;

    public void signup(MemberVO member) {
        if(member.getCate().equals("naver") || member.getCate().equals("kakao") || member.getCate().equals("google")){ //OAuth로 회원가입 할 경우
            oAuthService.inset(member);
        } else{ //우리 사이트로 회원가입 할 경우
            //스프링 시큐리티로 인한 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encodedPassword);
            member.setCate("home");
//         member.setEnabled(true); 비활성화 토글 기능 보류
            memberMapper.insert(member);
        }
    }
    public MemberVO findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    public void deleteMember(String email, String cate){
        memberMapper.deleteMember(email,cate);
    }
    public String confirmPassword(String email, String cate) {
        return memberMapper.confirmPassword(email, cate);
    }
}
