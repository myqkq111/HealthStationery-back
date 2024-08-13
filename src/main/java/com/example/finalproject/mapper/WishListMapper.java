package com.example.finalproject.mapper;

import com.example.finalproject.vo.LikeVO;
import com.example.finalproject.vo.ProductVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface WishListMapper {

    // 로그인한 유저가 클릭한 제품에 좋아요를 눌렀는지
    @Select("SELECT member_id FROM wishlist WHERE product_id = #{id} AND member_id = #{uid}")
    public Optional<Integer> isLikedMember(int id, int uid);

    // 좋아요 On
    @Insert("INSERT INTO wishlist VALUES (default, #{productId}, #{memberId})")
    public void likeTrue(LikeVO likeVO);

    // 좋아요 Off
    @Delete("DELETE FROM wishlist WHERE product_id = #{productId} AND member_id = #{memberId}")
    public void likeFalse(LikeVO likeVO);

    // 마이페이지 속 좋아요 목록
   
}