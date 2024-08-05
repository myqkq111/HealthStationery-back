package com.example.finalproject.controller;

import com.example.finalproject.service.ProductService;
import com.example.finalproject.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertProduct(@ModelAttribute ProductVO product,
                                           @RequestParam List<String> optionName,
                                           @RequestParam List<String> optionValue) {
        try{
            System.out.println(optionName);
            System.out.println(optionValue);
            productService.insert(product, optionName, optionValue);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User registration failed: " + e.getMessage());
        }
    }

}
