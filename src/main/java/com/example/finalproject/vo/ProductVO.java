package com.example.finalproject.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductVO {

    private int id; //상품 고유번호
    private String cate; //카테고리
    private String name; //상품 이름
    private int price; //상품 가격
    private List<String> image; // 상품 이미지 제목
    private String content; //상품 설명
    private List<String> contentImage; //상품 설명 이미지 제목
    private LocalDateTime regdt; //등록일
    private int like; //좋아요 개수
    private int view; //조회수
    private int sale_count; //판매량

    //상품 등록 때 List를 String으로 변환 후 DB에 넣어야함
    private String strImage;
    private String strContentImage;

//    //상품 목록 가져올 때 상품 옵션이 문자열로 되어 있음
//    private String strOptionName;
//    private String strOptionValue;

    //상품 옵션
    List<ProductOptionVO> productOptions;

}
