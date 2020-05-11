package com.shsxt.tmall.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.tmall.vo.ProductImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface ProductImageMapper extends BaseMapper<ProductImage,Integer> {

    public Integer insertReturnKey(ProductImage productImage);
}