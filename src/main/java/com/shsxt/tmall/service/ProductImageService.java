package com.shsxt.tmall.service;

import com.shsxt.tmall.query.ProductImageQuery;
import com.shsxt.tmall.vo.Product;
import com.shsxt.tmall.vo.ProductImage;

import java.util.Map;

public interface ProductImageService {
    //新增商品
    public Integer saveProductImage(ProductImage productImage);
    //更新商品信息
    public void updateProductImage(ProductImage productImage);
    //删除商品
    public void deleteProductImage(Integer pId);
    public Map<String, Object> imageList(ProductImageQuery productImageQuery);
}
