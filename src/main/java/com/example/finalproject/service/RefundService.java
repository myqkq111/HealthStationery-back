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
            String accessToken = getAccessToken();

            // 결제 금액 정보 조회
            String paymentInfoJson = getPaymentInfo(request.getImpUid(), accessToken);
            int totalAmount = calculateTotalAmount(paymentInfoJson);
            int totalRefunded = calculateTotalRefunded(paymentInfoJson);
            int remainingAmount = totalAmount - totalRefunded;

            // 남아 있는 금액 확인
            if (remainingAmount < request.getAmount()) {
                throw new IllegalArgumentException("잔여 금액이 요청한 환불 금액보다 적습니다.");
            }

            // 환불 요청
            return requestRefund(request, accessToken, totalAmount);
        } catch (IOException | ParseException e) {
            throw new RuntimeException("환불 처리 중 오류가 발생했습니다.", e);
        }
    }

    private String getAccessToken() throws ParseException {
        String tokenUrl = "https://api.iamport.kr/users/getToken";
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_JSON);
        String tokenRequestBody = String.format("{\"imp_key\":\"%s\",\"imp_secret\":\"%s\"}", impKey, impSecret);
        HttpEntity<String> tokenRequest = new HttpEntity<>(tokenRequestBody, tokenHeaders);
        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequest, String.class);

        if (tokenResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Access Token 발급에 실패했습니다. 응답 코드: " + tokenResponse.getStatusCode());
        }

        return extractAccessToken(tokenResponse.getBody());
    }

    private String extractAccessToken(String responseBody) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
            JSONObject response = (JSONObject) jsonResponse.get("response");
            if (response != null && response.get("access_token") != null) {
                return (String) response.get("access_token");
            } else {
                throw new RuntimeException("Access Token이 응답에 없습니다.");
            }
        } catch (ParseException e) {
            throw new RuntimeException("Access Token 추출 중 오류가 발생했습니다.", e);
        }
    }

    private String getPaymentInfo(String impUid, String accessToken) throws IOException, ParseException {
        URL url = new URL("https://api.iamport.kr/payments/" + impUid);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setDoOutput(false);

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("결제 정보 조회 요청이 실패했습니다. 응답 코드: " + responseCode);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            String responseLine = br.readLine();
            JSONParser parser = new JSONParser();
            JSONObject responseJson = (JSONObject) parser.parse(responseLine);
            JSONObject responseData = (JSONObject) responseJson.get("response");

            if (responseData != null) {
                return responseData.toJSONString(); // JSON 응답 반환
            } else {
                // 응답 로그 추가
                System.err.println("결제 정보 응답이 null입니다. 응답 내용: " + responseLine);
                throw new RuntimeException("결제 정보 응답이 null입니다.");
            }
        }
    }

    private int calculateTotalAmount(String paymentInfoJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject responseData = (JSONObject) parser.parse(paymentInfoJson);
        Long totalAmount = responseData.get("amount") != null ? (Long) responseData.get("amount") : 0L;
        return totalAmount.intValue();
    }

    private int calculateTotalRefunded(String paymentInfoJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject responseData = (JSONObject) parser.parse(paymentInfoJson);
        JSONArray cancelHistory = (JSONArray) responseData.get("cancel_history");

        int totalRefunded = 0;
        if (cancelHistory != null) {
            for (Object cancel : cancelHistory) {
                JSONObject cancelDetail = (JSONObject) cancel;
                totalRefunded += Integer.parseInt(cancelDetail.get("amount").toString());
            }
        }

        return totalRefunded;
    }

//    private String requestRefund(RefundRequestVO request, String accessToken, int totalAmount, int totalRefunded) {
//        String refundUrl = "https://api.iamport.kr/payments/cancel";
//        HttpHeaders refundHeaders = new HttpHeaders();
//        refundHeaders.setContentType(MediaType.APPLICATION_JSON);
//        refundHeaders.setBearerAuth(accessToken);
//
//        // 요청된 환불 금액
//        int refundAmount = request.getAmount();
//        System.out.println("환불 요청 금액: " + refundAmount);
//
//        // checksum 계산: 전체 결제 금액 - (현재 환불된 금액 + 이번 환불 요청 금액)
//        int checksum = totalAmount - (totalRefunded + refundAmount);
//        System.out.println("Checksum: " + checksum);
//
//        // 환불 요청 본문 작성
//        JSONObject refundBody = new JSONObject();
//        refundBody.put("imp_uid", request.getImpUid());
//        refundBody.put("amount", refundAmount);
//        refundBody.put("reason", request.getReason());
//        refundBody.put("checksum", checksum); // checksum 추가
//
//        HttpEntity<String> refundRequest = new HttpEntity<>(refundBody.toJSONString(), refundHeaders);
//        ResponseEntity<String> refundResponse = restTemplate.exchange(refundUrl, HttpMethod.POST, refundRequest, String.class);
//
//        if (refundResponse.getStatusCode() != HttpStatus.OK) {
//            throw new RuntimeException("환불 요청이 실패했습니다. 응답 코드: " + refundResponse.getStatusCode() +
//                    ", 응답 메시지: " + refundResponse.getBody());
//        }
//
//        return refundResponse.getBody();
//    }


    private String requestRefund(RefundRequestVO request, String accessToken, int remainingAmount) {
        String refundUrl = "https://api.iamport.kr/payments/cancel";
        HttpHeaders refundHeaders = new HttpHeaders();
        refundHeaders.setContentType(MediaType.APPLICATION_JSON);
        refundHeaders.setBearerAuth(accessToken);

        // checksum을 제거하고 필요한 필드만 사용합니다
        JSONObject refundBody = new JSONObject();
        refundBody.put("imp_uid", request.getImpUid());
        refundBody.put("amount", request.getAmount());
        refundBody.put("reason", request.getReason());

        HttpEntity<String> refundRequest = new HttpEntity<>(refundBody.toJSONString(), refundHeaders);
        ResponseEntity<String> refundResponse = restTemplate.exchange(refundUrl, HttpMethod.POST, refundRequest, String.class);

        if (refundResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("환불 요청이 실패했습니다. 응답 코드: " + refundResponse.getStatusCode() +
                    ", 응답 메시지: " + refundResponse.getBody());
        }

        return refundResponse.getBody();
    }

}