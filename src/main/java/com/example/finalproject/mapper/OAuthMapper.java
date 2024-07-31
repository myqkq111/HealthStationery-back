package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuthMapper {

    @Insert("INSERT INTO member(cate, email, name, fm, tell, birth) VALUES ('naver', #{email}, #{name}, #{fm}, #{tell}, #{birth})")
    public void oAuthSingUp(MemberVO member);

}
