package com.example.finalproject.service;

import com.example.finalproject.mapper.BuylistMapper;
import com.example.finalproject.vo.BuylistProductVO;
import com.example.finalproject.vo.BuylistVO;
import com.example.finalproject.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuylistService {

    @Autowired
    BuylistMapper buylistMapper;

    public void insert(BuylistVO buylist){
        buylistMapper.insertBuylist(buylist);
        List<BuylistProductVO> list = buylist.getProducts();
        for(BuylistProductVO product : list) {
            product.setBuylistId(buylist.getId());
            buylistMapper.insertBuylistProduct(product);
        }
    }

}
