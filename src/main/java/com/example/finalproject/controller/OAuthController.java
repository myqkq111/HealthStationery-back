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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    OAuthService oAuthService;

    @GetMapping("/naver")
    public String naver(@AuthenticationPrincipal OAuth2User principal) {
        String email = "";
        String name = "";
        String redirectUrl = "";
        if (principal != null) {
            // 네이버 사용자 정보 가져오기
            Map<String, Object> attributes = principal.getAttributes();
            // attributes의 `response` 객체에서 값을 추출하는 방법
            Object response = attributes.get("response");
            if (response instanceof Map) {
                Map<String, String> responseMap = (Map<String, String>) response;

//                String id = responseMap.get("id");
//                String gender = responseMap.get("gender");
                email = responseMap.get("email");
//                String mobile = responseMap.get("mobile"); //010-7531-0153
                name = responseMap.get("name");
//                String birth = responseMap.get("birthyear") + "-" + responseMap.get("birthday");

                // 날짜 형식 지정
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                Date date;
//                try {
//                    // 문자열을 Date 객체로 변환
//                    date = dateFormat.parse(birth);
//
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }

                MemberVO member = new MemberVO();
//                member.setFm(gender);
                member.setEmail(email);
//                member.setTell(mobile);
                member.setName(name);
//                member.setBirth(date);

                MemberVO select = oAuthService.naverEmailSelect(email);
                if(select == null){ //회원가입이 안된 이메일이라면 회원가입 페이지로 이동
                    try {
                        // 이메일과 이름을 URL 인코딩
                        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
                        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());

                        // 인코딩된 값을 쿼리 파라미터에 추가
                        redirectUrl = "http://localhost:3000/terms?email=" + encodedEmail + "&name=" + encodedName;

                    } catch (UnsupportedEncodingException e) {
                        // 인코딩 실패 시 예외 처리
                        e.printStackTrace();
                        redirectUrl = "http://localhost:3000/terms";
                    }
                } else{ //회원가입이 된 이메일이라면 메인페이지로 이동

                }
            }
        }
        return redirectUrl;
    }

    @GetMapping("/kakao")
    public String kakao(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            // 카카오 사용자 정보 가져오기
            Map<String, Object> attributes = principal.getAttributes();
            String email = ((Map<String, String>)attributes.get("kakao_account")).get("email");
            String name = ((Map<String, String>)attributes.get("properties")).get("nickname");
            System.out.println(email);

            MemberVO member = new MemberVO();
            member.setEmail(email);
            member.setName(name);
            MemberVO select = oAuthService.kakaoEmailSelect(email);
            if(select == null){
                return "redirect:/";
            }
        }
        return "redirect:http://localhost:3000";
    }
}