package com.example.finalproject.controller;

import com.example.finalproject.service.InqService;
import com.example.finalproject.service.ProductService;
import com.example.finalproject.service.WishListService;
import com.example.finalproject.vo.InqVO;
import com.example.finalproject.vo.ProductOptionVO;
import com.example.finalproject.vo.ProductVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    WishListService wishListService;

    @Autowired
    InqService inqService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertProduct(@ModelAttribute ProductVO product, @RequestParam("sizeStock") String sizeStockJson){
        try{
            // JSON 문자열을 List<ProductOptionVO>로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<ProductOptionVO> sizeStockList = objectMapper.readValue(sizeStockJson, new TypeReference<List<ProductOptionVO>>() {});

            // List<ProductOptionVO>를 List<String>으로 변환
            List<String> sizeStockStrings = sizeStockList.stream()
                    .flatMap(option -> List.of(option.getColor(), option.getSize(), String.valueOf(option.getStock())).stream())
                    .collect(Collectors.toList());
            product.setSizeStock(sizeStockStrings);

            productService.insert(product);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product registration failed: " + e.getMessage());
        }
    }


    @GetMapping("/selectAll")
    public ResponseEntity<?> selectAll() {
        try{
            List<ProductVO> list = productService.selectAll();
            return ResponseEntity.ok(list);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to retrieve the list of all products: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @ModelAttribute ProductVO product,
                                           @RequestParam("sizeStock") String sizeStockJson) {
        try{
            // JSON 문자열을 List<ProductOptionVO>로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<ProductOptionVO> sizeStockList = objectMapper.readValue(sizeStockJson, new TypeReference<List<ProductOptionVO>>() {});

            // List<ProductOptionVO>를 List<String>으로 변환
            List<String> sizeStockStrings = sizeStockList.stream()
                    .flatMap(option -> List.of(option.getColor(), option.getSize(), String.valueOf(option.getStock())).stream())
                    .collect(Collectors.toList());
            product.setSizeStock(sizeStockStrings);
            product.setId(id);
            productService.update(product);
            return ResponseEntity.ok(1);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to update the product: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete the product: " + e.getMessage());
        }
    }

    @GetMapping("/selectCate")
    public ResponseEntity<?> selectCate(@RequestParam String cate) {
        try {
            List<ProductVO> list =  productService.selectCate(cate);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve products for the selected category: " + e.getMessage());
        }
    }

    @GetMapping("/selectOne")
    public ResponseEntity<?> selectOne(@RequestParam int id,@RequestParam(required = false) Integer uid) {
        try {
            ProductVO product =  productService.selectOne(id);
            List<InqVO> inqList = inqService.selectAll(id);
            Map<String, Object> productDetailsMap = new HashMap<>();
            productDetailsMap.put("product", product);
            productDetailsMap.put("inquiries", inqList);
            if(uid != null && wishListService.isLikedMember(id,uid)>=1)
                product.setLikeToggle(product.getLikeToggle()+1);
            product.setLike(wishListService.totalLikes(id));
            return ResponseEntity.ok(productDetailsMap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve products for the selected category: " + e.getMessage());
        }
    }

    @GetMapping("/oneOption")
    public ResponseEntity<?> oneOption(@RequestParam int id) {
        try {
            List<ProductOptionVO> list =  productService.oneOption(id);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/viewUp")
    public ResponseEntity<?> viewUp(@RequestParam int id, HttpSession session) {
        try {
            // Log session details
            String sessionId = session.getId();

            // 세션에 특정 상품에 대한 조회 여부를 저장할 키
            String viewedKey = "viewed_" + id;

            // 상품 조회 여부를 확인
            String lastViewedSessionId = (String) session.getAttribute(viewedKey);

            if (lastViewedSessionId == null || !lastViewedSessionId.equals(sessionId)) {
                System.out.println("상품을 새로운 세션에서 조회하였습니다. 조회수를 증가시킵니다.");
                productService.viewUp(id);  // 조회수 증가
                session.setAttribute(viewedKey, sessionId);  // 현재 세션 ID를 저장
            } else {
                System.out.println("이미 이 세션에서 조회한 상품입니다. 조회수를 증가시키지 않습니다.");
            }

            return ResponseEntity.ok(1);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/liketop10")
    public ResponseEntity<?> liketop10() {
        try {
            System.out.println("하이");

            List<ProductVO> list =  productService.selectTop10ProductsByLikes();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("좋아요순 상품을 가져오지 못했습니다: " + e.getMessage());
        }
    }
    @GetMapping("/viewtop10")
    public ResponseEntity<?> viewtop10() {
        try {
            List<ProductVO> list =  productService.selectTop10ProductsByView();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("조회수순 상품을 가져오지 못했습니다: " + e.getMessage());
        }
    }
    @GetMapping("/purchasetop10")
    public ResponseEntity<?> purchasetop10() {
        try {
            List<ProductVO> list =  productService.selectTop10ProductsByPurchase();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("구매순 상품을 가져오지 못했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam String keyword) {
        try{
            List<ProductVO> list = productService.searchProductByName(keyword);
            return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("상품 검색 실패 : " + e.getMessage());
        }
    }
}
