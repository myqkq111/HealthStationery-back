package com.example.finalproject.controller;

import com.example.finalproject.service.OAuthService;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    OAuthService oAuthService;

    @GetMapping("/info")
    public String userInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            // 네이버 사용자 정보 가져오기
            Map<String, Object> attributes = principal.getAttributes();
            // attributes의 `response` 객체에서 값을 추출하는 방법
            Object response = attributes.get("response");
            if (response instanceof Map) {
                Map<String, String> responseMap = (Map<String, String>) response;

//                String id = responseMap.get("id");
                String gender = responseMap.get("gender");
                String email = responseMap.get("email");
                String mobile = responseMap.get("mobile"); //010-7531-0153
                String name = responseMap.get("name");
                String birth = responseMap.get("birthyear") + "-" + responseMap.get("birthday");

                // 날짜 형식 지정
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date;
                try {
                    // 문자열을 Date 객체로 변환
                    date = dateFormat.parse(birth);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                MemberVO member = new MemberVO();
                member.setFm(gender);
                member.setEmail(email);
                member.setTell(mobile);
                member.setName(name);
                member.setBirth(date);

                oAuthService.oAuthSingUp(member);
            }
        }
        return "redirect:http://localhost:3000";
    }
}