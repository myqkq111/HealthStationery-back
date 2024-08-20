package com.example.finalproject.vo;

import lombok.Data;

@Data
public class BuylistProductVO {

    private int id; //구매 내역 상품 고유번호
    private int buylistId; //구매 내역 고유번호
    private int productId; //상품 고유번호
    private String color; //상품 컬러
    private String size; //상품 사이즈
    private int count; //구매 개수
    private int confirmation; //구매 확정(1이 확정)
    private String status; //배송 상태
}
