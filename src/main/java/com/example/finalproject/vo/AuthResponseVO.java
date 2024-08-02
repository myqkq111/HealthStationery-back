package com.example.finalproject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class AuthResponseVO {
    private String token;
    private MemberVO member;
}
