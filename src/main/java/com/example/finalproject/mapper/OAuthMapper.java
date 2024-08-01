package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OAuthMapper {

    //naver 유효성 검사
    @Select("SELECT * FROM member WHERE email = #{email}")
    public MemberVO naverEmailSelect(String email);

    //naver 계정이 DB에 동록 안되어 있을 시 추가
    @Insert("INSERT INTO member(cate, email, name, fm, tell, birth) VALUES ('naver', #{email}, #{name}, #{fm}, #{tell}, #{birth})")
    public void naverSingUp(MemberVO member);

    //kakao 유효성 검사
    @Select("SELECT * FROM member WHERE kakao_id = #{kakaoId}")
    public MemberVO kakaoIdSelect(long kakaoId);

    //kakao 계정이 DB에 동록 안되어 있을 시 추가
    @Insert("INSERT INTO member(cate, name, kakao_id) VALUES ('kakao', #{name}, #{kakaoId})")
    public void kakaoSingUp(MemberVO member);


}
