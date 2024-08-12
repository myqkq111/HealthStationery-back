package com.example.finalproject.mapper;

import com.example.finalproject.vo.BasketVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BasketMapper {

    @Insert("INSERT INTO basket (product_id, member_id, color, size, count) VALUES (#{productId}, #{memberId}, #{color}, #{size}, #{count})")
    void insert(BasketVO basket);

}
