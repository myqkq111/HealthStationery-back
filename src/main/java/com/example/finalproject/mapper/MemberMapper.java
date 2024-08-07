package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {
    // 회원가입
    @Insert("INSERT INTO member (cate, email, password, name, fm, tell, birth, mailaddr, roadaddr, detailaddr)" +
            " VALUES (#{cate}, #{email}, #{password}, #{name}, #{fm}, #{tell}, #{birth}, " +
            "#{mailaddr}, #{roadaddr}, #{detailaddr})") // cate 필드 OAuth 구현 후 수정하기.
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(MemberVO member);

    // 이메일 유효성 검사
    @Select("SELECT * FROM member WHERE email = #{email}")
    MemberVO findByEmail(String email);

    // 회원 탈퇴
    @Delete("DELETE FROM member WHERE email = #{email} AND cate = #{cate}")
    void deleteMember(String email, String cate);
    @Select("SELECT password FROM member WHERE id = #{id}")
    String confirmPassword(int id);

    // 회원 수정
    @Update("UPDATE member " +
            "SET name = #{name}, tell = #{tell}, mailaddr = #{mailaddr}, roadaddr = #{roadaddr}, detailaddr = #{detailaddr} " +
            "WHERE id = #{id}")
    void updateUser(MemberVO member);

    // 비밀번호 변경
    @Update("UPDATE member SET password = #{password} WHERE email = #{email} AND cate = #{cate}")
    void updatePassword(MemberVO member);
}
