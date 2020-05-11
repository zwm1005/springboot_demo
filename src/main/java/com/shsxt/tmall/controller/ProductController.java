package com.shsxt.tmall.controller;

import com.shsxt.base.BaseController;
import com.shsxt.base.ResultInfo;
import com.shsxt.tmall.query.ProductQuery;
import com.shsxt.tmall.service.impl.ProductService;
import com.shsxt.tmall.vo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("product")
public class ProductController extends BaseController {
    @Resource
    private ProductService productService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> listProduct(ProductQuery productQuery){
        return productService.queryProductByParams(productQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(Product product){
        productService.saveProduct(product);
        return success("新增商品成功!");
    }
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Product product){
        productService.updateProduct(product);
        return success("商品信息更新成功!");
    }

    @RequestMapping("saveImg")
    @ResponseBody
    public ResultInfo saveImg(MultipartFile file, HttpServletRequest request, Integer pId, Integer flag){
        productService.saveImg(file,request,pId,flag);
        return success("上传成功!");
    }

}
