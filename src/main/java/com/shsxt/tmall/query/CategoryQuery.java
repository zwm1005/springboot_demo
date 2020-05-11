package com.shsxt.tmall.query;

import com.shsxt.base.BaseQuery;

public class CategoryQuery extends BaseQuery {
    //分类名称
    private String name;
    //分类等级
    private String grade;
    //父级分类名称
    private String pName;
    //父级分类id
    private Integer pId;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }


}
