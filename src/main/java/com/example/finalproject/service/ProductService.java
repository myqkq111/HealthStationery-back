package com.example.finalproject.service;

import com.example.finalproject.mapper.ProductMapper;
import com.example.finalproject.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public void insert(ProductVO product, List<String> optionName, List<String> optionValue){
        String strImage = String.join(",", product.getImage());
        String strContentImage = String.join(",", product.getContentImage());

        product.setStrImage(strImage);
        product.setStrContentImage(strContentImage);
        productMapper.insertProduct(product);

        String strOptionName = String.join(",", optionName);
        String strOptionValue = optionValue.get(0) + "|" + optionValue.get(1);
        productMapper.insertOption(product.getId(), strOptionName, strOptionValue);
    }

    public List<ProductVO> selectAll(){
        return productMapper.selectAll();
    }

    public void update(ProductVO product, List<String> optionName, List<String> optionValue) {
        String strImage = String.join(",", product.getImage());
        String strContentImage = String.join(",", product.getContentImage());

        product.setStrImage(strImage);
        product.setStrContentImage(strContentImage);
        productMapper.updateProduct(product);

        String strOptionName = String.join(",", optionName);
        String strOptionValue = optionValue.get(0) + "|" + optionValue.get(1);
        productMapper.updateOption(product.getId(), strOptionName, strOptionValue);
    }




}
