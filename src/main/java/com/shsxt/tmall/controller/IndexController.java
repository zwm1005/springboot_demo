package com.shsxt.tmall.controller;

import com.shsxt.base.BaseController;
import com.shsxt.tmall.dao.CategoryMapper;
import com.shsxt.tmall.dao.ProductMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class IndexController extends BaseController {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ProductMapper productMapper;

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("main")
    public String main(){
        return "main";
    }
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }
    @RequestMapping("category/index")
    public String categoryIndex(){
        return "category/index";
    }

    @RequestMapping("category/addOrUpdateCategoryPage")
    public String addOrUpdateCategoryPage(Integer id,Model model){
        model.addAttribute("category",categoryMapper.selectByPrimaryKey(id));
        return "category/add_update";
    }
    @RequestMapping("category/toSaveImgPage")
    public String toSaveImgPage(Integer cId,Model model){
        model.addAttribute("cId",cId);
        return "category/add_img";
    }

    @RequestMapping("product/index")
    public String productIndex(){
        return "product/product";
    }
    @RequestMapping("product/addOrUpdateProductPage")
    public String addOrUpdateProductPage(Integer id,Model model){
        model.addAttribute("product",productMapper.selectByPrimaryKey(id));
        return "product/add_update";
    }

    @RequestMapping("product/toSaveMainImgPage")
    public String toSaveProductImgPage(Integer pId,Integer flag,Model model){
        model.addAttribute("pId",pId);
        model.addAttribute("flag",flag);
        return "product/add_img";
    }

    @RequestMapping("product/toSaveDetailImgPage")
    public String toSaveDetailImgPage(Integer pId,Integer flag,Model model){
        model.addAttribute("pId",pId);
        model.addAttribute("flag",flag);
        return "product/add_img_detail";
    }

    @RequestMapping("product/toDetailImgPage")
    public String toDetailImgPage(Integer pId,Integer flag,Model model){
        model.addAttribute("pId",pId);
        model.addAttribute("flag",flag);
        return "product/detail_img";
    }

    @RequestMapping("product/toBigDetailImgPage")
    public String toDetailImgPage(Integer id,Model model){
        model.addAttribute("id",id);
        return "product/big_detail_img";
    }

}
