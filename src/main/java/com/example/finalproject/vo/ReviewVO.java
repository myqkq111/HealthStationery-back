package com.example.finalproject.vo;

import lombok.Data;

@Data
public class ReviewVO {

    private int id; //후기 고유번호
    private int buylistId; //구매 고유번호
    private int memberId; //회원 고유번호
    private int score; //별점
    private String content; //후기 내용
    private int like; //후기 좋아요
    private String contentImage; //후기 이미지 제목
}
