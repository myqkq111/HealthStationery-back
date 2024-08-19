package com.example.finalproject.mapper;

import com.example.finalproject.vo.InqVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InqMapper {

    // 상품 상세보기에서 해당 상품에 대한 문의글 보여주기
    @Select("SELECT i.*, m.name, i.product_id as productId, i.member_id as memberId, i.is_secret as isSecret " +
            "FROM inq i " +
            "JOIN member m ON i.member_id = m.id " +
            "WHERE i.product_id = #{productId}")
    public List<InqVO> selectAll(int productId);

    // 문의글 작성
    @Insert("INSERT INTO inq(product_id, member_id, is_secret, title, content)" +
            "VALUES(#{productId},#{memberId},#{isSecret},#{title},#{content})")
    void insert(InqVO inqVO);

    //모든 문의글 보여주기
    @Select("SELECT i.*, i.product_id as productId, i.member_id as memberId, m.name FROM inq i JOIN member m ON(i.member_id = m.id)")
    List<InqVO> selectAdmin();

    @Delete("DELETE FROM inq WHERE id = #{id}")
    void delete(int id);

    @Update("UPDATE inq " +
            "SET comment = #{comment} " +
            "WHERE id = #{id}")
    void updateComment(InqVO inq);
}
