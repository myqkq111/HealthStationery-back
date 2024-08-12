package com.example.finalproject.vo;

import lombok.Data;

@Data
public class BasketVO {

    private int id; //장바구니 상품 번호
    private int productId; //상품 고유번호
    private int memberId; //회원 고유번호
    private String color; //상품 컬러
    private String size; //상품 사이즈
    private String count; //상품 개수
}
