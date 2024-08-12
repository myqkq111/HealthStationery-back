package com.example.finalproject.mapper;

import com.example.finalproject.vo.BasketVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BasketMapper {

    @Insert("INSERT INTO basket (product_id, member_id, color, size, count) VALUES (#{productId}, #{memberId}, #{color}, #{size}, #{count})")
    void insert(BasketVO basket);

    @Select("SELECT * FROM basket WHERE product_id = #{productId} AND member_id = #{memberId} AND color = #{color} AND size = #{size}")
    BasketVO check(BasketVO basket);

    @Select("SELECT b.*, b.product_id as productId, b.member_id as memberId, p.name,  p.cate, p.price, p.image as strImage FROM basket b join product p on(b.product_id = p.id) WHERE member_id = #{memberId}")
    List<BasketVO> selectByMemberId(int memberId);

    @Update("UPDATE basket SET count = count + 1 WHERE id = #{id}")
    void update(int id);


}
