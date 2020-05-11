package com.shsxt.tmall.service;

import com.shsxt.tmall.query.CategoryQuery;
import com.shsxt.tmall.query.ProductQuery;
import com.shsxt.tmall.vo.Category;
import com.shsxt.tmall.vo.Product;

import java.util.Map;

public interface ProductService {
    //新增商品
    public void saveProduct(Product product);
    //更新商品信息
    public void updateProduct(Product product);
    //删除商品
    public void deleteProduct(Integer pId);
    //多条件查询商品
    public Map<String, Object> queryProductByParams(ProductQuery productQuery);
}
