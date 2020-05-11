package com.shsxt.tmall.controller;
import com.shsxt.base.BaseController;
import com.shsxt.base.ResultInfo;
import com.shsxt.tmall.dao.CategoryMapper;
import com.shsxt.tmall.query.CategoryQuery;
import com.shsxt.tmall.service.impl.CategoryService;
import com.shsxt.tmall.utils.AssertUtil;
import com.shsxt.tmall.vo.Category;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("category")
public class CategoryController extends BaseController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private CategoryMapper categoryMapper;
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(Category category,Integer parentId){


       // System.out.println(parentId+"=======================");
        categoryService.saveCategory(category);
        return success("新增分类成功!");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Category category){
        categoryService.updateCategory(category);
        return success("分类更新成功!");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        categoryService.deleteCategory(id);
        return success("分类删除成功!");
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> listCategorys(CategoryQuery categoryQuery, Model model){
        Map<String,Object> map;
        map = categoryService.queryCategoryByParams(categoryQuery);
        model.addAttribute("categorys",map.get("data"));
        return map;
    }
    @RequestMapping("queryAllFatherCategory")
    @ResponseBody
    public List<Category> queryAllFatherCategory(){
        return categoryMapper.queryAllFatherCategory();
    }

    @RequestMapping("queryAllChildCategory")
    @ResponseBody
    public List<Category> queryAllChildCategory(){
        return categoryMapper.queryAllChildCategory();
    }

    @RequestMapping("saveImg")
    @ResponseBody
    public ResultInfo saveImg(MultipartFile file, HttpServletRequest request, Integer cId){
        categoryService.saveImg(file,request,cId);
        return success("分类图片成功!");
    }
}
