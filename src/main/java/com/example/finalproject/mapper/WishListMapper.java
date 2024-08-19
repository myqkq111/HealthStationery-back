package com.example.finalproject.mapper;

import com.example.finalproject.vo.LikeVO;
import com.example.finalproject.vo.ProductVO;
import org.apache.ibatis.annotations.*;

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

    @Update("UPDATE product " +
            "SET `like` = `like`+1 " +
            "WHERE id = #{productId}")
    public void likeTrue2(LikeVO likeVO);

    // 좋아요 Off
    @Delete("DELETE FROM wishlist WHERE product_id = #{productId} AND member_id = #{memberId}")
    public void likeFalse(LikeVO likeVO);

    @Update("UPDATE product " +
            "SET `like` = `like`-1 " +
            "WHERE id = #{productId}")
    public void likeFalse2(LikeVO likeVO);

    // 마이페이지 속 좋아요 목록
    @Select("SELECT w.*, w.product_id as productId, w.member_id as memberId, p.name,  p.cate, p.price, p.image as strImage FROM wishlist w join product p on (w.product_id = p.id) WHERE w.member_id = #{id};")
    List<LikeVO> view(int id);

    // 총 좋아요 개수
    @Select("SELECT `like` FROM product WHERE id = #{productId}")
    int totalLikes(int productId);
}
