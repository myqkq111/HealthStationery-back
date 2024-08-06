package com.example.finalproject.mapper;

import com.example.finalproject.vo.ProductOptionVO;
import com.example.finalproject.vo.ProductVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    //상품 등록
    @Insert("INSERT INTO product(cate, name, price, image, content, content_image, inven) VALUES (#{cate}, #{name}, #{price}, #{strImage}, #{content}, #{strContentImage}, #{inven})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertProduct(ProductVO product);

    //상품 옵션 등록
    @Insert("INSERT INTO product_option(product_id, name, value) VALUES(#{id}, #{strOptionName}, #{strOptionValue})")
    public void insertOption(int id, String strOptionName, String strOptionValue);

    //모든 상품 가져오기 (상품에 맞는 옵션도 같이)
    @Select("SELECT p.*, image as strImage, content_image as strContentImage, po.name as strOptionName, value as strOptionValue FROM product p join product_option po on(p.id = po.product_id)")
    public List<ProductVO> selectAll();

}
