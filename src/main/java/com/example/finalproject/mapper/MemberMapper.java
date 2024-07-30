package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    // 회원가입
    @Insert("INSERT INTO member (cate, email, password, name, fm, tell, birth, mailaddr, roadaddr, detailaddr)" +
            " VALUES ('home', #{email}, #{password}, #{name}, #{fm}, #{tell}, #{birth}, " +
            "#{mailaddr}, #{roadaddr}, #{detailaddr})") // cate 필드 OAuth 구현 후 수정하기.
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(MemberVO member);

    // 이메일 유효성 검사
    @Select("SELECT * FROM member WHERE email = #{email}")
    MemberVO findByEmail(String email);
}
