package com.example.finalproject.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
// "INSERT INTO users (username, password, enabled) VALUES (#{username}, #{password}, #{enabled})"
@Data
public class MemberVO {
    private int id; //회원 고유번호
    private String cate; //회원 가입 방식 (우리사이트 or OAuth)
    private String email; //이메일
    private String password; //비밀번호
    private String name; //이름
    private String fm; //성별 (man or women)
    private String tell; //전화번호
    private Date birth; //생년월일
    private String mailaddr; //우편번호
    private String roadaddr; //도로명주소
    private String detailaddr; //상세주소
    private LocalDateTime regdt; //가입일
    private String member_type; //유저 구분 (admin or user)
}
