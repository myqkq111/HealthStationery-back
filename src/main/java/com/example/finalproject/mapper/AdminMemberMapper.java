package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMemberMapper {
    //관리자 페이지 유저 목록
    @Select("SELECT * FROM member")
    public List<MemberVO> selectAll();

    //관리자 페이지 유저 삭제
    @Delete("DELETE FROM member WHERE id = #{id}")
    public void delete(int id);

    //관리자 페이지 유저 권한 설정
    @Update("UPDATE member SET member_type = #{memberType} WHERE id = #{id}")
    public void update(int id, String memberType);

    //id로 회원 조회
    @Select("SELECT * FROM member WHERE id = #{id}")
    public MemberVO selectOne(int id);

}
