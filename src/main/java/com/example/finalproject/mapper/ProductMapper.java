package com.example.finalproject.mapper;

import com.example.finalproject.vo.ProductOptionVO;
import com.example.finalproject.vo.ProductVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    //상품 등록
    @Insert("INSERT INTO product(cate, name, price, image, content, content_image) VALUES (#{cate}, #{name}, #{price}, #{strImage}, #{content}, #{strContentImage})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertProduct(ProductVO product);

    //상품 옵션 등록
    @Insert("INSERT INTO product_option(product_id, color, size, stock) VALUES(#{productId}, #{color}, #{size}, #{stock})")
    public void insertOption(ProductOptionVO productOptionVO);

    //모든 상품 가져오기
    @Select("SELECT *, image as strImage, content_image as strContentImage FROM product")
    public List<ProductVO> selectAll();

    //운동장비 모든 상품 가져오기
    @Select("SELECT *, image as strImage, content_image as strContentImage FROM product WHERE cate NOT IN('tops', 'bottoms', 'other-clothing')")
    public List<ProductVO> shopAll();

    //의류 모든 상품 가져오기
    @Select("SELECT *, image as strImage, content_image as strContentImage FROM product WHERE cate IN('tops', 'bottoms', 'other-clothing')")
    public List<ProductVO> clothingAll();

    //모든 옵션 가져오기
    @Select("SELECT *, product_id as productId FROM product_option")
    public List<ProductOptionVO> selectOptionAll();

    //상품 수정
    @Update("UPDATE product SET cate = #{cate}, name = #{name}, price = #{price}, image = #{strImage}, content = #{content}, content_image = #{strContentImage} WHERE id = #{id}")
    public void updateProduct(ProductVO product);

    //상품 삭제
    @Delete("DELETE FROM product WHERE id = #{id}")
    public void deleteProduct(int id);

    //상품 옵션 삭제
    @Delete("DELETE FROM product_option WHERE product_id = #{id}")
    public void deleteOption(int id);

    //상품 카테고리에 따라 가져오기
    @Select("SELECT *, image as strImage, content_image as strContentImage FROM product WHERE cate = #{cate}")
    public List<ProductVO> selectCate(String cate);

    //상품 상세보기 (상품에 맞는 옵션도 같이)
    @Select("SELECT *, image as strImage, content_image as strContentImage FROM product WHERE id = #{id}")
    public ProductVO selectOne(int id);

    //상품 옵션 수정
    @Update("UPDATE product_option SET stock = stock - #{count} WHERE product_id = #{id} AND color = #{color} AND size = #{size}")
    public void stockUpdate(int id, String color, String size, int count);

    //상품 ID에 맞는 옵션 가져오기
    @Select("SELECT *, product_id as productId FROM product_option WHERE product_id = #{id}")
    public List<ProductOptionVO> oneOption(int id);

    //판매량 증가
    @Update("UPDATE product SET sale_count = sale_count + 1 WHERE id = #{id}")
    public void saleCountUp(int id);

    //환불에 의한 판매량 감소
     @Update("UPDATE product SET sale_count = sale_count - 1 WHERE id = #{id}")
     public void saleCountDown(int id);

    //환불에 의한 재고 증가
    @Update("UPDATE product_option SET stock = stock + #{count} WHERE product_id = #{id} AND color = #{color} AND size = #{size}")
    public void stockUp(int id, String color, String size, int count);

    //조회수 증가
    @Update("UPDATE product SET view = view + 1 WHERE id = #{id}")
    public void viewUp(int id);

    // 찜 순위 TOP 10
    @Select("SELECT cate, name, price, `like`, image as strImage " +
            "FROM product " +
            "ORDER BY `like` DESC LIMIT 10")
    public List<ProductVO> selectTop10ProductsByLikes();

    // 조회수 순위 TOP 10
    @Select("SELECT cate, name, price, `like`, image as strImage " +
            "FROM product " +
            "ORDER BY `view` DESC LIMIT 10")
    public List<ProductVO> selectTop10ProductsByView();

    // 구매 순위 TOP 10
    @Select("SELECT cate, name, price, `like`, image as strImage " +
            "FROM product " +
            "ORDER BY sale_count DESC LIMIT 10")
    public List<ProductVO> selectTop10ProductsByPurchase();

    // 상품 검색
    @Select("SELECT cate, name, price, `like`, image as strImage " +
            "FROM product " +
            "WHERE `name` LIKE CONCAT('%', #{keyword}, '%')")
    public List<ProductVO> searchProductByName(String keyword);
}
