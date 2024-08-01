package com.example.finalproject.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // OAuth2User에서 권한 정보 가져오기
        String targetUrl = "/default";
        for (var authority : oAuth2User.getAuthorities()) {
            if (authority instanceof SimpleGrantedAuthority) {
                String authorityName = authority.getAuthority();

                // 권한 이름을 통해 제공자 구분
                if (authorityName.contains("ROLE_KAKAO")) {
                    targetUrl = "/oauth/kakao";
                    break;
                } else if (authorityName.contains("ROLE_NAVER")) {
                    targetUrl = "/oauth/naver";
                    break;
                }
            }
        }

        response.sendRedirect(targetUrl);
    }
}

