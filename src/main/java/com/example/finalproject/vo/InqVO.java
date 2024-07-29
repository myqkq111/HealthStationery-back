package com.example.finalproject.vo;

import lombok.Data;

@Data
public class InqVO {

    private int id; //문의 고유번호
    private int productId; //상품 고유번호
    private int memberId; //회원 고유번호
    private boolean isSecret; //비밀글 or 공개글
    private int password; //비밀글일 경우 비밀번호 필수
    private String content; //문의 내역
}
