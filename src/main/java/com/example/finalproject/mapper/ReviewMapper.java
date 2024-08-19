package com.example.finalproject.mapper;

import com.example.finalproject.vo.ReviewVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper {

    // 리뷰 작성
    @Insert("INSERT INTO review(buylist_id, product_id, score, content)" +
            "VALUES(#{buylistId}, #{productId}, #{score}, #{content})")
    void insert(ReviewVO reviewVO);

    // 리뷰 출력
    @Select("SELECT content, score, name FROM review JOIN buylist b ON buylist_id = b.id " +
            "WHERE product_id = #{productId}")
    public List<ReviewVO> selectByProductId(int productId);
}
