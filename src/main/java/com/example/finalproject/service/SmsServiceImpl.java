package com.example.finalproject.service;

import com.example.finalproject.Util.SmsCertificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsServiceImpl {

    @Autowired
    private SmsCertificationUtil smsCertificationUtil;

    public void sendSms(String phoneNumber) {
        String certificationCode = generateVerificationCode();
        smsCertificationUtil.sendSMS(phoneNumber, certificationCode); // SMS 인증 유틸리티를 사용하여 SMS 발송
    }

    public boolean verifyCode(String phoneNumber, String code) {
        return smsCertificationUtil.verifyCode(phoneNumber, code);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
