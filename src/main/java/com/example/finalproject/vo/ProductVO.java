package com.example.finalproject.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductVO {

    private int id; //상품 고유번호
    private String cate; //카테고리
    private String name; //상품 이름
    private int price; //상품 가격
    private String image; // 상품 이미지 제목
    private String content; //상품 설명
    private String contentImage; //상품 설명 이미지 제목
    private LocalDateTime regdt; //등록일
    private int like; //좋아요 개수
    private int view; //조회수
    private int inven; //재고
    private int sale_count; //판매량
}
