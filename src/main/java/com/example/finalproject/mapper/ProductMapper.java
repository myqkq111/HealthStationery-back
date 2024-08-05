package com.example.finalproject.mapper;

import com.example.finalproject.vo.ProductOptionVO;
import com.example.finalproject.vo.ProductVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO product(cate, name, price, image, content, content_image, inven) VALUES (#{cate}, #{name}, #{price}, #{strImage}, #{content}, #{strContentImage}, #{inven})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertProduct(ProductVO product);

    @Insert("INSERT INTO product_option(product_id, name, value) VALUES(#{id}, #{strOptionName}, #{strOptionValue})")
    public void insertOption(int id, String strOptionName, String strOptionValue);

}
