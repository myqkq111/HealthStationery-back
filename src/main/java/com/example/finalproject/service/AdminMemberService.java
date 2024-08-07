package com.example.finalproject.service;

import com.example.finalproject.mapper.AdminMemberMapper;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMemberService {

    @Autowired
    AdminMemberMapper adminMemberMapper;

    public List<MemberVO> selectAll(){
        return adminMemberMapper.selectAll();
    }

    public void delete(int id){
        adminMemberMapper.delete(id);
    }

    public void update(int id, String memberType ){
        adminMemberMapper.update(id, memberType);
    }

    public MemberVO selectOne(int id){
        return adminMemberMapper.selectOne(id);
    }

}
