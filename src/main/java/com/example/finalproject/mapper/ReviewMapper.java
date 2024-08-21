package com.example.finalproject.mapper;

import com.example.finalproject.vo.ReviewVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper {

    // 리뷰 작성
    @Insert("INSERT INTO review(product_id, buylist_product_id, score, content)" +
            "VALUES(#{productId}, #{buylistProductId}, #{score}, #{content})")
    void insert(ReviewVO reviewVO);

    // 리뷰 출력
    @Select("SELECT content, score, name " +
            "FROM review r " +
            "JOIN buylist_product bp ON buylist_product_id = bp.id " +
            "JOIN buylist b ON b.id = bp.buylist_id " +
            "WHERE r.product_id = #{productId}")
    public List<ReviewVO> selectByProductId(int productId);
}
