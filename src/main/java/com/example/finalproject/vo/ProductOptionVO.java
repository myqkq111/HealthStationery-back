package com.example.finalproject.vo;

import lombok.Data;

@Data
public class ProductOptionVO {

    private int id; //상품 옵션 고유번호
    private int productId; //상품 고유번호
    private String color; //상품 컬러
    private String size; //상품 사이즈
    private int stock; //재고
}
