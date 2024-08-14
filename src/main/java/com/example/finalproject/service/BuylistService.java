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

    @Autowired
    ProductService productService;

    @Autowired
    BasketService basketService;

    public int insert(BuylistVO buylist){
        //구매내역 등록
        buylistMapper.insertBuylist(buylist);
        List<BuylistProductVO> list = buylist.getProducts();
        for(BuylistProductVO product : list) {
            product.setBuylistId(buylist.getId());
            buylistMapper.insertBuylistProduct(product);

            //상품 재고 수정
            productService.stockUpdate(product.getProductId(), product.getColor(), product.getSize(), product.getCount());

            //장바구니 목록 삭제
            //장바구니 페이지를 통해 구매를 할 경우 장바구니에 구매한 목록 사제
            if(buylist.getPurchaseSource() == 1){
                basketService.buylistAfterDelete(product.getProductId(), buylist.getMemberId(), product.getColor(), product.getSize());
            }
        }
        return buylist.getId();
    }

    public BuylistVO selectSuccess(int id){
        return buylistMapper.selectSuccess(id);
    }

}
