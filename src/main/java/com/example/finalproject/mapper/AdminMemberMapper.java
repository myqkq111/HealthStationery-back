package com.example.finalproject.mapper;

import com.example.finalproject.vo.MemberVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMemberMapper {
    //관리자 페이지 유저 목록
    @Select("SELECT * FROM member")
    public List<MemberVO> selectAll();

    //관리자 페이지 유저 삭제
    @Delete("DELETE FROM member WHERE id = #{id}")
    public void delete(int id);

}
