package com.example.finalproject.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.finalproject.jwt.JwtAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    private final CustomExceptionHandler customExceptionHandler;
    public SecurityConfig(CustomExceptionHandler customExceptionHandler) {
        this.customExceptionHandler = customExceptionHandler;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .formLogin(withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
//                                .requestMatchers("/product/**").authenticated()
//                                .requestMatchers("/member/confirmPassword").authenticated()
                                .requestMatchers("/member/deleteAccount").authenticated()
                                .requestMatchers("/ws/**").permitAll()
                                .anyRequest().permitAll() // 나머지 모든 요청은 접근 허용
//                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(oauth2UserService()) // 사용자 정보 서비스 설정
                                )
                                .successHandler(customAuthenticationSuccessHandler())
                                .failureUrl("/login?error=true") // 로그인 실패 시 이동할 URL
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout=true")
                                .permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(customExceptionHandler)
                                .accessDeniedHandler(customExceptionHandler)
                );


        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://kitri-final-project-front.s3-website.ap-northeast-2.amazonaws.com",
                "http://ELB777-314057791.ap-northeast-2.elb.amazonaws.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
                OAuth2User oAuth2User = super.loadUser(userRequest);

                // 세션에 registrationId 저장
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                request.getSession().setAttribute("registrationId", userRequest.getClientRegistration().getRegistrationId());

                return oAuth2User;
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                // 사용자 인증 성공 후, 인증된 사용자 정보를 가져옵니다.
                String registrationId = (String) request.getSession().getAttribute("registrationId");
                Map<String, Object> attributes = ((OAuth2User) authentication.getPrincipal()).getAttributes();
                String targetUrl = "/default";
                if ("kakao".equals(registrationId)) {
                    //한글이 있을 시 오류가 나기 때문에 인코딩 작업 해줘야 함
                    String email = ((Map<String, String>)attributes.get("kakao_account")).get("email");
                    String name = ((Map<String, String>)attributes.get("properties")).get("nickname");
                    String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
                    String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
                    targetUrl = "http://localhost:3000/oauth?cate=kakao&email=" + encodedEmail + "&name=" + encodedName;

                } else if ("naver".equals(registrationId)) {
                    Object user = attributes.get("response");
                    if (user instanceof Map) {
                        try {
                            Map<String, String> userMap = (Map<String, String>) user;
                            String email = userMap.get("email");
                            String name = userMap.get("name");
                            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
                            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
                            targetUrl = "http://localhost:3000/oauth?cate=naver&email=" + encodedEmail + "&name=" + encodedName;
                        } catch (UnsupportedEncodingException e) {
//                        // 인코딩 실패 시 예외 처리
//                        e.printStackTrace();
//                        redirectUrl = "http://localhost:3000/terms";
                    }
                    }
                } else if ("google".equals(registrationId)) {
                    String email = (String)attributes.get("email");
                    String name = (String)attributes.get("name");
                    String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
                    String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
                    targetUrl = "http://localhost:3000/oauth?cate=google&email=" + encodedEmail + "&name=" + encodedName;
                }
                // 클라이언트 애플리케이션으로 리디렉션합니다.
                response.sendRedirect(targetUrl);
            }
        };
    }
}
