package com.example.finalproject.service;

import com.example.finalproject.vo.RefundRequestVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class RefundService {

    @Value("${imp.key}")
    private String impKey;

    @Value("${imp.secret}")
    private String impSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String processRefund(RefundRequestVO request) {
        try {
            // Access Token 발급
            String tokenUrl = "https://api.iamport.kr/users/getToken";
            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.setContentType(MediaType.APPLICATION_JSON);
            String tokenRequestBody = String.format("{\"imp_key\":\"%s\",\"imp_secret\":\"%s\"}", impKey, impSecret);
            HttpEntity<String> tokenRequest = new HttpEntity<>(tokenRequestBody, tokenHeaders);
            ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequest, String.class);

            // JSON 응답에서 access_token 추출
            String accessToken = extractAccessToken(tokenResponse.getBody());

            // 결제 금액 정보 조회
            String paymentInfoJson = paymentInfo(request.getImpUid(), accessToken);
            int remainingAmount = calculateRemainingAmount(paymentInfoJson);

            // 남아 있는 금액 확인
            if (remainingAmount < request.getAmount()) {
                throw new RuntimeException("잔여 금액이 요청한 환불 금액보다 적습니다.");
            }

            // 환불 요청
            String refundUrl = "https://api.iamport.kr/payments/cancel";
            HttpHeaders refundHeaders = new HttpHeaders();
            refundHeaders.setContentType(MediaType.APPLICATION_JSON);
            refundHeaders.setBearerAuth(accessToken);

            // checksum 계산
            int checksum = remainingAmount - request.getAmount();

            // Create the JSON object
            JSONObject refundBody = new JSONObject();
            refundBody.put("imp_uid", request.getImpUid());
            refundBody.put("amount", request.getAmount());
            refundBody.put("reason", request.getReason());
            refundBody.put("checksum", checksum);  // 올바른 checksum 값 추가

            HttpEntity<String> refundRequest = new HttpEntity<>(refundBody.toJSONString(), refundHeaders);
            ResponseEntity<String> refundResponse = restTemplate.exchange(refundUrl, HttpMethod.POST, refundRequest, String.class);

            return refundResponse.getBody();
        } catch (Exception e) {
            throw new RuntimeException("환불 처리 중 오류가 발생했습니다.", e);
        }
    }

    private String extractAccessToken(String responseBody) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
            JSONObject response = (JSONObject) jsonResponse.get("response");
            return (String) response.get("access_token");
        } catch (ParseException e) {
            throw new RuntimeException("Access Token 추출 중 오류가 발생했습니다.", e);
        }
    }

    private String paymentInfo(String impUid, String accessToken) throws IOException, ParseException {
        HttpURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/" + impUid);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", accessToken); // Bearer token
        conn.setDoOutput(false);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            JSONParser parser = new JSONParser();
            JSONObject responseJson = (JSONObject) parser.parse(br.readLine());
            JSONObject responseData = (JSONObject) responseJson.get("response");

            if (responseData != null) {
                return responseData.toJSONString(); // JSON 응답 반환
            } else {
                throw new RuntimeException("Response is null.");
            }
        } catch (IOException e) {
            throw new IOException("API 요청 중 오류가 발생했습니다.", e);
        }
    }

    private int calculateRemainingAmount(String paymentInfoJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(paymentInfoJson);
        JSONObject responseData = (JSONObject) responseJson.get("response");

        if (responseData != null) {
            int totalAmount = Integer.parseInt(responseData.get("amount").toString());
            JSONArray cancelHistory = (JSONArray) responseData.get("cancel_history");

            int totalRefunded = 0;
            if (cancelHistory != null) {
                for (Object cancel : cancelHistory) {
                    JSONObject cancelDetail = (JSONObject) cancel;
                    totalRefunded += Integer.parseInt(cancelDetail.get("amount").toString());
                }
            }

            return totalAmount - totalRefunded;
        } else {
            throw new RuntimeException("Response data is null.");
        }
    }

}
