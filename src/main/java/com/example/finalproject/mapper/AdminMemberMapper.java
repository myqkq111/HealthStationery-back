package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMemberMapper {
    //관리자 페이지 유저 목록
    @Select("SELECT * FROM member")
    public MemberVO selectAll();

}
