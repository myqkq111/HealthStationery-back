package com.example.finalproject.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SelectBuylistVO {

    private int id; //구매 내역 번호
    private int memberId; //회원 고유번호
    private String name; //받으실 분
    private String tell; //휴대폰 번호
    private String mailaddr; //우편번호
    private String roadaddr; //도로명주소
    private String detailaddr; //상세주소
    private String request; //요청사항
    private int totalPrice; //총 결제금액
    private LocalDateTime regdt;// 구매일시

    private int buylistProductId; //구매 내역 상품 고유번호
    private int productId; //상품 고유번호
    private String color; //상품 컬러
    private String size; //상품 사이즈
    private int count; //구매 개수
    private int confirmation; //구매 확정(1이 확정)
    private String status; //배송 상태

    private String productName; //상품 이름
    private int price; //상품 가격
    private String cate; //카테고리
    private String strImage; // 상품 이미지 제목
    private String content; //상품 설명

    private String memberName; //아이디 name

    private Integer hasReview; //리뷰작성여부
}
