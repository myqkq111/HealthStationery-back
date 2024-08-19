package com.example.finalproject.controller;

import com.example.finalproject.service.InqService;
import com.example.finalproject.vo.InqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Inquiry")
public class InqController {

    @Autowired
    InqService inqService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertInq(@RequestBody InqVO inq) {
        try{
            System.out.println(inq);
            inqService.insert(inq);
            return ResponseEntity.ok(1);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/selectAll")
    public ResponseEntity<?> selectAdmiin() {
        try{
            List<InqVO> list = inqService.selectAdmin();
            return ResponseEntity.ok(list);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInq(@PathVariable int id) {
        try{
//            System.out.println(id);
            inqService.deleteInq(id);
            return ResponseEntity.ok(1);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/comment")
    public ResponseEntity<?> updateInq(@RequestBody InqVO inq) {
        try{
//            System.out.println("컨트롤러 : "+inq);
            inqService.updateComment(inq);
            return ResponseEntity.ok(1);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("관리자 댓글 추가 에러 : "+e.getMessage());
        }
    }
}
