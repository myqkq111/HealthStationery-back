package com.example.finalproject.service;

import com.example.finalproject.mapper.BuylistMapper;
import com.example.finalproject.vo.BuylistProductVO;
import com.example.finalproject.vo.BuylistVO;
import com.example.finalproject.vo.ProductVO;
import com.example.finalproject.vo.SelectBuylistVO;
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

            //상품 판매량 증가
            productService.saleCountUp(product.getProductId());

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

    public List<SelectBuylistVO> selectBuylist(int id){
        return buylistMapper.selectBuylist(id);
    }

    public void delete(int id, int pid, String color, String size, int count){
        //구매 취소
        int su = buylistMapper.selectBuylistCount(id);
        if(su > 1){ //한번에 여러상품을 구매한 경우 구매내역 전부가 아닌 해당상품만 삭제
            buylistMapper.deleteBuylistProduct(id, pid, color, size);
        } else{ //하나의 상품만 구매했을 경우 다 삭제
            buylistMapper.deleteBuylist(id);
        }
//        buylistMapper.deleteBuylistProduct(id);

        //상품 재고 증가
        productService.stockUp(pid,color,size,count);

        //상품 판매량 감소
        productService.saleCountDown(pid);
    }

    public void updateConfirmation(int id){
        buylistMapper.updateConfirmation(id);
    }

}
