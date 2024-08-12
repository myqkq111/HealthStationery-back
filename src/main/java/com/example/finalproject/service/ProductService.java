package com.example.finalproject.service;

import com.example.finalproject.mapper.ProductMapper;
import com.example.finalproject.vo.ProductOptionVO;
import com.example.finalproject.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public void insert(ProductVO product){
        String strImage = String.join(",", product.getImage());
        String strContentImage = String.join(",", product.getContentImage());

        product.setStrImage(strImage);
        product.setStrContentImage(strContentImage);
        productMapper.insertProduct(product);

        // sizeStock이 List<String>로 반환되는 경우를 가정
        List<String> sizeStockList = product.getSizeStock();

        // 리스트의 크기가 3의 배수가 아닌 경우를 처리
        if (sizeStockList.size() % 3 != 0) {
            throw new IllegalArgumentException("SizeStock list size is not a multiple of 3");
        }

        for (int i = 0; i < sizeStockList.size(); i += 3) {
            // 3개씩 나누어 데이터 추출
            String color = sizeStockList.get(i).trim();
            String size = sizeStockList.get(i + 1).trim();
            int stock;
            try {
                stock = Integer.parseInt(sizeStockList.get(i + 2).trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock format: " + sizeStockList.get(i + 2));
                continue;
            }

            // ProductOptionVO 객체 생성 및 설정
            ProductOptionVO productOption = new ProductOptionVO();
            productOption.setProductId(product.getId()); // 상품 고유번호 설정
            productOption.setColor(color); // 색상 설정
            productOption.setSize(size); // 사이즈 설정
            productOption.setStock(stock); // 재고 설정

            // productMapper를 사용하여 데이터베이스에 저장
            try {
                productMapper.insertOption(productOption);
            } catch (Exception e) {
                System.out.println("Failed to insert option: " + e.getMessage());
                // 예외 처리 로직을 추가할 수 있습니다.
            }
        }
    }

    public List<ProductVO> selectAll(){
        // 모든 상품을 가져옵니다.
        List<ProductVO> listProduct = productMapper.selectAll();

        // 모든 상품 옵션을 가져옵니다.
        List<ProductOptionVO> listOptions = productMapper.selectOptionAll();

        // 상품 ID를 키로, 옵션 목록을 값으로 가지는 맵을 만듭니다.
        Map<Integer, List<ProductOptionVO>> productOptionsMap = new HashMap<>();

        // 상품 옵션 목록을 맵에 추가합니다.
        for (ProductOptionVO option : listOptions) {
            productOptionsMap
                    .computeIfAbsent(option.getProductId(), k -> new ArrayList<>())
                    .add(option);
        }

        // 상품 목록을 순회하며 해당 상품의 옵션을 설정합니다.
        for (ProductVO product : listProduct) {
            List<ProductOptionVO> optionsForProduct = productOptionsMap.get(product.getId());
            if (optionsForProduct != null) {
                product.setList(optionsForProduct);
            } else {
                product.setList(new ArrayList<>()); // 옵션이 없는 경우 빈 리스트를 설정합니다.
            }
        }

        return listProduct;
    }

    public void update(ProductVO product) {
        String strImage = String.join(",", product.getImage());
        String strContentImage = String.join(",", product.getContentImage());

        product.setStrImage(strImage);
        product.setStrContentImage(strContentImage);
        productMapper.insertProduct(product);

        productMapper.deleteOption(product.getId());

        // sizeStock이 List<String>로 반환되는 경우를 가정
        List<String> sizeStockList = product.getSizeStock();

        // 리스트의 크기가 3의 배수가 아닌 경우를 처리
        if (sizeStockList.size() % 3 != 0) {
            throw new IllegalArgumentException("SizeStock list size is not a multiple of 3");
        }

        for (int i = 0; i < sizeStockList.size(); i += 3) {
            // 3개씩 나누어 데이터 추출
            String color = sizeStockList.get(i).trim();
            String size = sizeStockList.get(i + 1).trim();
            int stock;
            try {
                stock = Integer.parseInt(sizeStockList.get(i + 2).trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock format: " + sizeStockList.get(i + 2));
                continue;
            }

            // ProductOptionVO 객체 생성 및 설정
            ProductOptionVO productOption = new ProductOptionVO();
            productOption.setProductId(product.getId()); // 상품 고유번호 설정
            productOption.setColor(color); // 색상 설정
            productOption.setSize(size); // 사이즈 설정
            productOption.setStock(stock); // 재고 설정

            // productMapper를 사용하여 데이터베이스에 저장
            try {
                productMapper.insertOption(productOption);
            } catch (Exception e) {
                System.out.println("Failed to insert option: " + e.getMessage());
                // 예외 처리 로직을 추가할 수 있습니다.
            }
        }
    }

    public void delete(int id) {
        productMapper.deleteOption(id);
        productMapper.deleteProduct(id);
    }

    public List<ProductVO> selectCate(String cate) {

        // 카테고리에 맞는 상품을 가져옵니다.
        List<ProductVO> listProduct = productMapper.selectCate(cate);

        // 모든 상품 옵션을 가져옵니다.
        List<ProductOptionVO> listOptions = productMapper.selectOptionAll();

        // 상품 ID를 키로, 옵션 목록을 값으로 가지는 맵을 만듭니다.
        Map<Integer, List<ProductOptionVO>> productOptionsMap = new HashMap<>();

        // 상품 옵션 목록을 맵에 추가합니다.
        for (ProductOptionVO option : listOptions) {
            productOptionsMap
                    .computeIfAbsent(option.getProductId(), k -> new ArrayList<>())
                    .add(option);
        }

        // 상품 목록을 순회하며 해당 상품의 옵션을 설정합니다.
        for (ProductVO product : listProduct) {
            List<ProductOptionVO> optionsForProduct = productOptionsMap.get(product.getId());
            if (optionsForProduct != null) {
                product.setList(optionsForProduct);
            } else {
                product.setList(new ArrayList<>()); // 옵션이 없는 경우 빈 리스트를 설정합니다.
            }
        }

        return listProduct;
    }

    public ProductVO selectOne(int id) {
        // id에 맞는 상품을 가져옵니다.
        ProductVO listProduct = productMapper.selectOne(id);

        // 모든 상품 옵션을 가져옵니다.
        List<ProductOptionVO> listOptions = productMapper.selectOptionAll();

        // 상품 ID를 키로, 옵션 목록을 값으로 가지는 맵을 만듭니다.
        Map<Integer, List<ProductOptionVO>> productOptionsMap = new HashMap<>();

        // 상품 옵션 목록을 맵에 추가합니다.
        for (ProductOptionVO option : listOptions) {
            productOptionsMap
                    .computeIfAbsent(option.getProductId(), k -> new ArrayList<>())
                    .add(option);
        }

        // 상품에 맞는 상품의 옵션을 설정합니다.
        List<ProductOptionVO> optionsForProduct = productOptionsMap.get(listProduct.getId());
        if (optionsForProduct != null) {
            listProduct.setList(optionsForProduct);
        } else {
            listProduct.setList(new ArrayList<>()); // 옵션이 없는 경우 빈 리스트를 설정합니다.
        }

        return listProduct;
    }


}
