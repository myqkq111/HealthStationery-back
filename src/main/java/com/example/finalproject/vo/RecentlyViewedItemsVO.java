package com.example.finalproject.vo;

import lombok.Data;

@Data
public class RecentlyViewedItemsVO {

    private int id; //최근 본 상품 고유번호
    private int productId; //상품 고유번호
    private int memberId; //회원 고유번호
}
