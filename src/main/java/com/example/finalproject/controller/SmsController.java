package com.example.finalproject.controller;

import com.example.finalproject.service.SmsServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsServiceImpl smsService;

    @PostMapping("/send")
    public String sendVerificationCode(@RequestBody String phoneNumber) {

        System.out.println(phoneNumber);
        smsService.sendSms("01075310153");
        return "Verification code sent!";
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> requestBody) {
        String phoneNumber = requestBody.get("phoneNumber");
        String code = requestBody.get("code");

        boolean isValid = smsService.verifyCode(phoneNumber, code);
        if (isValid) {
            return ResponseEntity.ok("Code verified successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid verification code.");
        }
    }

}