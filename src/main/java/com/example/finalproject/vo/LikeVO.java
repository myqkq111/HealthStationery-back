package com.example.finalproject.vo;

import lombok.Data;

@Data
public class LikeVO {

    private int id; //좋아요 고유번호
    private int productId; //상품 고유번호
    private int memberId; //회원 고유번호

    private String name;
    private String cate;
    private String price;
    private String strImage;
}
