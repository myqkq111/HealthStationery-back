package com.example.finalproject.service;

import com.example.finalproject.mapper.ProductMapper;
import com.example.finalproject.vo.ProductOptionVO;
import com.example.finalproject.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public void insert(ProductVO product, List<String> list){
        String strImage = String.join(",", product.getImage());
        String strContentImage = String.join(",", product.getContentImage());

        product.setStrImage(strImage);
        product.setStrContentImage(strContentImage);
        productMapper.insertProduct(product);

//        String strOptionName = String.join(",", optionName);
//        String strOptionValue;
//        if(optionName.size() == 1){
//            // 1. List<String>의 값을 하나의 문자열로 합치기
//            String combinedString = String.join("", optionValue);
//
//            // 2. 공백 제거하기
//            strOptionValue = combinedString.replaceAll("\\s+", "");
//        } else{
//            strOptionValue = optionValue.get(0) + "|" + optionValue.get(1);
//        }

        // List<String>을 순회하며 각 문자열을 처리
        for (String data : list) {
            // 데이터 문자열을 콤마로 분리
            String[] parts = data.split(",");

            if (parts.length != 3) {
                // 데이터 형식이 맞지 않는 경우
                System.out.println("Invalid data format: " + data);
                continue;
            }

            // ProductOptionVO 객체 생성 및 설정
            ProductOptionVO productOption = new ProductOptionVO();
            productOption.setProductId(product.getId()); // 상품 고유번호 설정
            productOption.setColor(parts[0].trim()); // 색상 설정
            productOption.setSize(parts[1].trim()); // 사이즈 설정
            productOption.setStock(Integer.parseInt(parts[2].trim())); // 재고 설정

            // productMapper를 사용하여 데이터베이스에 저장
            productMapper.insertOption(productOption);
        }
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

    public void delete(int id) {
        productMapper.deleteOption(id);
        productMapper.deleteProduct(id);
    }

    public List<ProductVO> selectCate(String cate) {
        return productMapper.selectCate(cate);
    }

    public ProductVO selectOne(int id) {
        return productMapper.selectOne(id);
    }


}
