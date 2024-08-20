package com.example.finalproject.mapper;

import com.example.finalproject.vo.BuylistProductVO;
import com.example.finalproject.vo.BuylistVO;
import com.example.finalproject.vo.ProductVO;
import com.example.finalproject.vo.SelectBuylistVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuylistMapper {

    //구매 내역 등록
    @Insert("INSERT INTO buylist(member_id, name, tell, mailaddr, roadaddr, detailaddr, request, totalPrice) VALUES (#{memberId}, #{name}, #{tell}, #{mailaddr}, #{roadaddr}, #{detailaddr}, #{request}, #{totalPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertBuylist(BuylistVO buylist);

    //구매 내역 상품 등록
    @Insert("INSERT INTO buylist_product(buylist_id, product_id, color, size, count) VALUES (#{buylistId}, #{productId}, #{color}, #{size}, #{count})")
    void insertBuylistProduct(BuylistProductVO buylist);

    //결제완료 페이지 띄우기
    @Select("SELECT * FROM buylist WHERE id = #{id}")
    BuylistVO selectSuccess(int id);

    //마이페이지 주문 목록
    @Select("SELECT b.*, b.member_id AS memberId, bp.id AS buylistProductId, bp.product_id AS productId, " +
            "bp.color, bp.size, bp.count, bp.confirmation, bp.status, p.name AS productName, p.price, p.cate, p.image AS strImage, " +
            "p.content, " +
            "CASE WHEN r.id IS NOT NULL THEN 1 ELSE 0 END AS hasReview " +
            "FROM buylist b " +
            "JOIN buylist_product bp ON (b.id = bp.buylist_id) " +
            "JOIN product p ON (p.id = bp.product_id) " +
            "LEFT JOIN review r ON r.buylist_product_id = bp.id " +
            "WHERE b.member_id = #{id}")
    List<SelectBuylistVO> selectBuylist(int id);

    @Select("select count(*) from buylist_product where buylist_id = #{id}")
    int selectBuylistCount(int id);

    //구매 내역 취소
    @Delete("DELETE FROM buylist WHERE id = #{id}")
    void deleteBuylist(int id);

    //구매 내역 상품 취소
    @Delete("DELETE FROM buylist_product WHERE buylist_id = #{id} AND product_id = #{pid} AND color = #{color} AND size = #{size}")
    void deleteBuylistProduct(int id, int pid, String color, String size);

    //구매 확정
    @Update("UPDATE buylist_product SET confirmation = 1 WHERE id = #{id}")
    void updateConfirmation(int id);

    //관리자 페이지 모든 주문 보기
    @Select("select b.*, b.member_id as memberId, bp.id as buylistProductId, bp.product_id as productId, bp.color, bp.size, bp.count, bp.confirmation, bp.status, p.name as productName, p.price, p.cate, p.image as strImage, p.content, m.name as memberName from buylist b join buylist_product bp on(b.id = bp.buylist_id) join product p on(p.id = bp.product_id) join member m on(b.member_id = m.id)")
    List<SelectBuylistVO> selectAll();

    //5분마다 상태 자동 업데이트
    @Update("UPDATE buylist_product SET status = '배송중' WHERE status = '상품 준비중'")
    void updateProductStatusToShipping();

    //10분마다 상태 자동 업데이트
    @Update("UPDATE buylist_product SET status = '배송완료' WHERE status = '배송중'")
    void updateProductStatusToDelivered();
}
