package com.example.finalproject.controller;

import com.example.finalproject.service.ProductService;
import com.example.finalproject.service.WishListService;
import com.example.finalproject.vo.ProductOptionVO;
import com.example.finalproject.vo.ProductVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    WishListService wishListService;

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

//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateProduct(@PathVariable int id, @ModelAttribute ProductVO product,
//                                             @RequestParam List<String> optionName,
//                                             @RequestParam List<String> optionValue) {
//        try{
//            product.setId(id);
//            productService.update(product, optionName, optionValue);
//            return ResponseEntity.ok(1);
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body("Failed to update the product: " + e.getMessage());
//        }
//    }

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
            System.out.println(product);
            if(uid != null && wishListService.isLikedMember(id,uid)>=1)
                product.setLikeToggle(product.getLikeToggle()+1);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve products for the selected category: " + e.getMessage());
        }
    }


}
