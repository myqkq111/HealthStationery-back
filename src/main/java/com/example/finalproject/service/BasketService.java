package com.example.finalproject.service;

import com.example.finalproject.mapper.BasketMapper;
import com.example.finalproject.vo.BasketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    @Autowired
    BasketMapper basketMapper;

    public void insert(BasketVO basket){
        basketMapper.insert(basket);
    }

    public BasketVO check(BasketVO basket){
        return basketMapper.check(basket);
    }

    public List<BasketVO> selectByMemberId(int memberId){
        return basketMapper.selectByMemberId(memberId);
    }

    public void update(int id){
        basketMapper.update(id);
    }

    public void delete(List<Integer> ids){
        basketMapper.delete(ids);
    }

    public void buylistAfterDelete(int productId, int memberId, String color, String size){
        BasketVO basket = new BasketVO();
        basket.setProductId(productId);
        basket.setMemberId(memberId);
        basket.setColor(color);
        basket.setSize(size);

        basketMapper.buylistAfterDelete(basket);
    }

    public void optionUpdate(BasketVO basket){
        basketMapper.optionUpdate(basket);
    }

    public int getCartItemCount(int memberId) {
        // 장바구니의 아이템 수를 계산하는 로직을 구현합니다.
        // 이 예제에서는 memberId로 장바구니를 조회하고 아이템 수를 반환합니다.
        List<BasketVO> items = selectByMemberId(memberId); // 예시로 memberId에 따라 아이템 조회
        return items.size(); // 아이템 수 반환
    }
}
