package com.example.finalproject.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BuylistVO {

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

    List<BuylistProductVO> products; //선택한 상품 옵션

    private int purchaseSource; //구매 페이지에 들어온 경로 (바로구매 버튼 or 장바구니)

}
