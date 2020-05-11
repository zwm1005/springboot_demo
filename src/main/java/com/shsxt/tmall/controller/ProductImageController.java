package com.shsxt.tmall.controller;

import com.shsxt.base.BaseController;
import com.shsxt.tmall.query.ProductImageQuery;
import com.shsxt.tmall.service.ProductImageService;
import com.shsxt.tmall.vo.ProductImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("productImage")
public class ProductImageController extends BaseController {
    @Resource
    private ProductImageService productImageService;


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> imageList(Integer pId){
        ProductImageQuery productImageQuery = new ProductImageQuery();
        productImageQuery.setpId(pId);
        return productImageService.imageList(productImageQuery);
    }

}
