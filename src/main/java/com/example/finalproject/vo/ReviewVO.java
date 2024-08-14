package com.example.finalproject.vo;

import lombok.Data;

@Data
public class ReviewVO {

    private int id; //후기 고유번호
    private int buylistId; //구매 고유번호
    private int productId; //제품 고유번호
    private int score; //별점
    private String content; //후기 내용
    private String name;
}
