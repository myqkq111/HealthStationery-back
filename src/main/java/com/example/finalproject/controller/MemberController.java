package com.example.finalproject.controller;

import com.example.finalproject.jwt.JwtUtil;
import com.example.finalproject.service.MemberService;
import com.example.finalproject.vo.AuthResponseVO;
import com.example.finalproject.vo.ErrorResponseVO;
import com.example.finalproject.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    public MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberVO member) {
        try{
            memberService.signup(member);
            return ResponseEntity.ok(2);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User registration failed: " + e.getMessage());
        }
    }
    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody String email) {
        try{
            if(memberService.findByEmail(email)!=null)
                return ResponseEntity.ok(1);
            else
                return ResponseEntity.ok(0);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/findPW")
    public ResponseEntity<?> findByEmail(@RequestBody String email) {
        try{
            if(memberService.findByEmail(email)!=null){
                return ResponseEntity.ok(1);
            }
            return ResponseEntity.ok(0);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("User email search failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody MemberVO member) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(member.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new AuthResponseVO(jwt,memberService.findByEmail(jwtUtil.extractUsername(jwt))));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseVO("Incorrect username or password"));
        }
    }

    //토큰 유효성 검사
    @GetMapping("/validate-token")
    public Map<String, Object> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        Map<String, Object> response = new HashMap<>();
        String jwtToken = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7); // "Bearer " 제거
            try {
                username = jwtUtil.extractUsername(jwtToken);
                if (username != null && jwtUtil.validateToken(jwtToken, username)) {
                    // JWT 유효한 경우 사용자 정보를 반환합니다.
                    response.put("valid", true);
                    response.put("username", username);
                } else {
                    response.put("valid", false);
                }
            } catch (Exception e) {
                response.put("valid", false);
            }
        } else {
            response.put("valid", false);
        }
        return response;
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String token, @RequestBody MemberVO member) {
        try {
            String email = jwtUtil.extractUsername(token.replace("Bearer ", ""));
            memberService.deleteMember(email,member.getCate());
            return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원탈퇴에 실패했습니다.");
        }
    }
    @PostMapping("/confirmPassword")
    public ResponseEntity<?> confirmPassword(@RequestBody MemberVO member) {
        try{
            String pw = memberService.confirmPassword(member.getEmail(), member.getCate());
            if (passwordEncoder.matches(member.getPassword(),pw)) {
                return ResponseEntity.ok(1);
            }
            return ResponseEntity.ok(0);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("비밀번호 불일치");
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody MemberVO member) {
        try{
            memberService.updateUser(member);
            System.out.println(member);
            return ResponseEntity.ok(1);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 비밀번호 변경 전 현재 비밀번호 확인하는 api 실행
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody MemberVO member) {
        try{
            memberService.updatePassword(member);
            return ResponseEntity.ok(1);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
