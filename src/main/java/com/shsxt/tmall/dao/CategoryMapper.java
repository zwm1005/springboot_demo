package com.shsxt.tmall.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.tmall.vo.Category;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category,Integer> {
    Category queryByName(String name);
    List<Category> queryAllFatherCategory();

    Integer countByParentId(Integer parentId);

    List<Category> queryAllChildCategory();
}