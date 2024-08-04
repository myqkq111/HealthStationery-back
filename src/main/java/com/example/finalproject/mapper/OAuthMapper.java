package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OAuthMapper {

    //email 유효성 검사
    @Select("SELECT * FROM member WHERE email = #{email} AND cate = #{cate}")
    public MemberVO findByEmail(String email, String cate);

    //OAuth 회원가입
    @Insert("INSERT INTO member(cate, email, name, fm, tell, birth, mailaddr, roadaddr, detailaddr) VALUES (#{cate}, #{email}, #{name}, #{fm}, #{tell}, #{birth}, #{mailaddr}, #{roadaddr}, #{detailaddr})")
    public void inset(MemberVO member);
}
