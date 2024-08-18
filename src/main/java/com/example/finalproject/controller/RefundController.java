package com.example.finalproject.controller;


import com.example.finalproject.service.RefundService;
import com.example.finalproject.vo.RefundRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RefundController {

    @Autowired
    RefundService refundService;

    @PostMapping("/refund")
    public ResponseEntity<String> requestRefund(@RequestBody RefundRequestVO request) {
        try {
            String response = refundService.processRefund(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();  // 콘솔에 상세한 예외 메시지 출력
            return ResponseEntity.status(500).body("환불 처리 중 오류가 발생했습니다. 에러 메시지: " + e.getMessage());
        }
    }
}