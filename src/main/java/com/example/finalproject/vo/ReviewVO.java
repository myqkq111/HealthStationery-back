package com.example.finalproject.vo;

import lombok.Data;

@Data
public class ReviewVO {

    private int id; //후기 고유번호
    private int productId; //제품 고유번호
    private String strImage;
    private int buylistProductId; //제품 주문 고유번호
    private String color; //상품 컬러
    private String size; //상품 사이즈
    private int score; //별점
    private String content; //후기 내용

    private String name;
    private String productName;
    private String cate; //카테고리
}
