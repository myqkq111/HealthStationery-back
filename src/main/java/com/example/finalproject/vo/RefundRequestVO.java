package com.example.finalproject.vo;

import lombok.Data;

@Data
public class RefundRequestVO {

    private String impUid;
    private int amount;
    private String reason;
    private int totalAmount;  // 전체 결제 금액 필드 추가
}
