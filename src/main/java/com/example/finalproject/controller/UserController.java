package com.example.finalproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public String userInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            // 네이버 사용자 정보 가져오기
            Map<String, Object> attributes = principal.getAttributes();
            // attributes의 `response` 객체에서 값을 추출하는 방법
            Object response = attributes.get("response");
            if (response instanceof Map) {
                Map<String, String> responseMap = (Map<String, String>) response;

                String id = responseMap.get("id");
                String gender = responseMap.get("gender");
                String email = responseMap.get("email");
                String mobile = responseMap.get("mobile"); //010-7531-0153
                String mobile_e164 = responseMap.get("mobile_e164"); //+821075310153
                String name = responseMap.get("name");
                String birthday = responseMap.get("birthday");
                String birthyear = responseMap.get("birthyear");

                System.out.println(id);
                System.out.println(gender);
                System.out.println(email);
                System.out.println(mobile);
                System.out.println(mobile_e164);
                System.out.println(name);
                System.out.println("잘됌");
            }


        }
        return "redirect:http://localhost:3000";
    }
}