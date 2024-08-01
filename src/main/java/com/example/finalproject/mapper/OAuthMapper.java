package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OAuthMapper {

    @Select("SELECT * FROM member WHERE email = #{email}")
    public MemberVO naverEmailSelect(String email);

    @Insert("INSERT INTO member(cate, email, name, fm, tell, birth) VALUES ('naver', #{email}, #{name}, #{fm}, #{tell}, #{birth})")
    public void naverSingUp(MemberVO member);

    @Select("SELECT * FROM member WHERE kakao_id = #{kakaoId}")
    public MemberVO kakaoIdSelect(long kakaoId);

    @Insert("INSERT INTO member(cate, name, kakao_id) VALUES ('kakao', #{name}, #{kakaoId})")
    public void kakaoSingUp(MemberVO member);


}
