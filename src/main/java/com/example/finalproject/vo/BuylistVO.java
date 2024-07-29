package com.example.finalproject.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BuylistVO {

    private int id; //구매 내역 번호
    private int productId; //상품 고유번호
    private int memberId; //회원 고유번호
    //    private String option;
    //    private String color;
    //    private int size;
    private String name; //받으실 분
    private String tell; //휴대폰 번호
    private String mailaddr; //우편번호
    private String roadaddr; //도로명주소
    private String detailaddr; //상세주소
    private String request; //요청사항
    private LocalDateTime regdt;// 구매일시
    private int count; //구매개수
}
