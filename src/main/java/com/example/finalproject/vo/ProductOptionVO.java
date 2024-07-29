package com.example.finalproject.vo;

import lombok.Data;

@Data
public class ProductOptionVO {

    private int id; //상품 옵션 고유번호
    private int productId; //상품 고유번호
    private String name; //상품 옵션 이름
    private String value; //상품 옵션 데이터
}
