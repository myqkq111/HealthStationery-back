package com.example.finalproject.service;

import com.example.finalproject.mapper.MemberMapper;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public MemberMapper memberMapper;

    public void signup(MemberVO member) {
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
//        member.setEnabled(true); 비활성화 토글 기능 보류
        memberMapper.insert(member);
    }
}
