package com.example.finalproject.mapper;

import com.example.finalproject.vo.BuylistProductVO;
import com.example.finalproject.vo.BuylistVO;
import com.example.finalproject.vo.ProductVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BuylistMapper {

    //구매 내역 등록
    @Insert("INSERT INTO buylist(member_id, name, tell, mailaddr, roadaddr, detailaddr, request, totalPrice) VALUES (#{memberId}, #{name}, #{tell}, #{mailaddr}, #{roadaddr}, #{detailaddr}, #{request}, #{totalPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertBuylist(BuylistVO buylist);

    //구매 내역 상품 등록
    @Insert("INSERT INTO buylist_product(buylist_id, product_id, color, size, count) VALUES (#{buylistId}, #{productId}, #{color}, #{size}, #{count})")
    void insertBuylistProduct(BuylistProductVO buylist);

}
