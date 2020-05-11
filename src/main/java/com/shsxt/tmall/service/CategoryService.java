package com.shsxt.tmall.service;

import com.shsxt.base.BaseService;
import com.shsxt.tmall.query.CategoryQuery;
import com.shsxt.tmall.vo.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface CategoryService  {
    //新增分类
    public void saveCategory(Category category);
    //更新分类
    public void updateCategory(Category category);
    //删除分类
    public void deleteCategory(Integer cId);
    //多条件查询分类
    public Map<String, Object> queryCategoryByParams(CategoryQuery categoryQuery);
}
