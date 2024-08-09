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

    //모든 상품 가져오기 (상품에 맞는 옵션도 같이)
    @Select("SELECT p.*, image as strImage, content_image as strContentImage, po.* FROM product p join product_option po on(p.id = po.product_id)")
    public List<ProductVO> selectAll();

    //상품 수정
    @Update("UPDATE product SET cate = #{cate}, name = #{name}, price = #{price}, image = #{strImage}, content = #{content}, content_image = #{strContentImage} WHERE id = #{id}")
    public void updateProduct(ProductVO product);

    //상품 옵션 수정
    @Update("UPDATE product_option SET  WHERE product_id = #{id}")
    public void updateOption(int id, String strOptionName, String strOptionValue);

    //상품 삭제
    @Delete("DELETE FROM product WHERE id = #{id}")
    public void deleteProduct(int id);

    //상품 옵션 삭제
    @Delete("DELETE FROM product_option WHERE product_id = #{id}")
    public void deleteOption(int id);

    //상품 카테고리에 따라 가져오기 (상품에 맞는 옵션도 같이)
    @Select("SELECT p.*, image as strImage, content_image as strContentImage, po.*  FROM product p join product_option po on(p.id = po.product_id) WHERE cate = #{cate}")
    public List<ProductVO> selectCate(String cate);

    //상품 상세보기 (상품에 맞는 옵션도 같이)
    @Select("SELECT p.*, image as strImage, content_image as strContentImage, po.*  FROM product p join product_option po on(p.id = po.product_id) WHERE p.id = #{id}")
    public ProductVO selectOne(int id);

}
