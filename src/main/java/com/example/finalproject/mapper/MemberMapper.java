package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO member (cate, email, password, name, fm, tell, birth, mailaddr, roadaddr, detailaddr)" +
            " VALUES ('home', #{email}, #{password}, #{name}, #{fm}, #{tell}, #{birth}, " +
            "#{mailaddr}, #{roadaddr}, #{detailaddr})") // cate 필드 OAuth 구현 후 수정하기.
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(MemberVO member);
}
