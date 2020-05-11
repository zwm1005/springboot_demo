package com.shsxt.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.tmall.dao.ProductImageMapper;
import com.shsxt.tmall.dao.ProductMapper;
import com.shsxt.tmall.query.ProductImageQuery;
import com.shsxt.tmall.query.ProductQuery;
import com.shsxt.tmall.utils.AssertUtil;
import com.shsxt.tmall.vo.Product;
import com.shsxt.tmall.vo.ProductImage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductImageService extends BaseService<ProductImage,Integer> implements com.shsxt.tmall.service.ProductImageService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductImageMapper productImageMapper;
    @Override
    public Integer saveProductImage(ProductImage productImage) {
        AssertUtil.isTrue(null==productImage.getPid(),"请指定商品id");
        AssertUtil.isTrue(null==productMapper.selectByPrimaryKey(productImage.getPid()),"商品id不存在!");
        Integer row = productImageMapper.insertReturnKey(productImage);
        AssertUtil.isTrue(row<1,"商品图片信息保存失败!");
        //获取插入数据返回的主键
        Integer id = productImage.getId();
        return id;
    }

    @Override
    public void updateProductImage(ProductImage productImage) {
        AssertUtil.isTrue(null==productImage.getPid(),"请指定商品id");
        AssertUtil.isTrue(null==productMapper.selectByPrimaryKey(productImage.getPid()),"商品id不存在!");
        AssertUtil.isTrue(productImageMapper.updateByPrimaryKeySelective(productImage)<1,"商品图片信息更新失败!");
    }

    @Override
    public void deleteProductImage(Integer pId) {

    }


    public Map<String, Object> imageList(ProductImageQuery productImageQuery) {
        Map<String,Object> map = new HashMap<String ,Object>();
        PageHelper.startPage(productImageQuery.getPage(),productImageQuery.getLimit());
        PageInfo<ProductImage> pageInfo = new PageInfo<ProductImage>(productImageMapper.selectByParams(productImageQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }
}
