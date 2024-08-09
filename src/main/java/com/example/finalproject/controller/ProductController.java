package com.example.finalproject.controller;

import com.example.finalproject.service.ProductService;
import com.example.finalproject.vo.ProductVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertProduct(@ModelAttribute ProductVO product){
        try{
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
            System.out.println(list);
            return ResponseEntity.ok(list);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to retrieve the list of all products: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @ModelAttribute ProductVO product,
                                             @RequestParam List<String> optionName,
                                             @RequestParam List<String> optionValue) {
        try{
            product.setId(id);
            productService.update(product, optionName, optionValue);
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
    public ResponseEntity<?> selectOne(@RequestParam int id) {
        try {
            ProductVO product =  productService.selectOne(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve products for the selected category: " + e.getMessage());
        }
    }


}
