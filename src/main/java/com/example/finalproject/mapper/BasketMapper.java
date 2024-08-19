package com.example.finalproject.mapper;

import com.example.finalproject.vo.BasketVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BasketMapper {

    @Insert("INSERT INTO basket (product_id, member_id, color, size, count) VALUES (#{productId}, #{memberId}, #{color}, #{size}, #{count})")
    void insert(BasketVO basket);

    @Select("SELECT * FROM basket WHERE product_id = #{productId} AND member_id = #{memberId} AND color = #{color} AND size = #{size}")
    BasketVO check(BasketVO basket);

    @Select("SELECT b.*, b.product_id as productId, b.member_id as memberId, p.name,  p.cate, p.price, p.image as strImage, po.stock FROM basket b join product p on(b.product_id = p.id) JOIN product_option po ON(b.product_id = po.product_id AND b.color = po.color AND b.size = po.size) WHERE member_id = #{memberId}")
    List<BasketVO> selectByMemberId(int memberId);

    @Update("UPDATE basket SET count = count + 1 WHERE id = #{id}")
    void update(int id);

    @Delete({
            "<script>",
            "DELETE FROM basket WHERE id IN",
            "<foreach item='id' collection='list' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void delete(List<Integer> ids);

    @Delete("DELETE FROM basket WHERE product_id = #{productId} AND member_id = #{memberId} AND color = #{color} AND size = #{size}")
    void buylistAfterDelete(BasketVO basket);

    @Update("UPDATE basket SET color = #{color}, size = #{size}, count = #{count} WHERE id = #{id}")
    void optionUpdate(BasketVO basket);

    // 장바구니 아이템 수 반환
    @Select("SELECT COUNT(*) FROM basket WHERE member_id = #{memberId}")
    int getCartItemCount(@Param("memberId") int memberId);
}
