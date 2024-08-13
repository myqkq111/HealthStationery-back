package com.example.finalproject.mapper;

import com.example.finalproject.vo.InqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InqMapper {

    // 상품 상세보기에서 해당 상품에 대한 문의글 보여주기
    @Select("SELECT i.*, m.name " +
            "FROM inq i " +
            "JOIN member m ON i.member_id = m.id " +
            "WHERE i.product_id = #{productId}")
    public List<InqVO> selectAll(int productId);
}
