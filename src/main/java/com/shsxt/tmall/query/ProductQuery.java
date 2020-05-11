package com.shsxt.tmall.query;

import com.shsxt.base.BaseQuery;

public class ProductQuery extends BaseQuery {
    private String name;//商品名
    private String categoryName;//所属分类名
    private Integer level;//价格区间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
