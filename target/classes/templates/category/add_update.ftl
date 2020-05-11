<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input type="hidden" name="id"  value="${(category.id)!}">
    <input type="hidden" name="pId"  value="${(category.parentId)!}">
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="name" id="name"  value="${(category.name)!}"  placeholder="请输入分类名称">
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">分类级别</label>
        <div class="layui-input-block">

            <select name="grade" id="grade" <#if category??><#if category.grade??>disabled="disabled"</#if></#if>>
                <option value="" >请选择</option>
                <option value="1" <#if category??><#if category.grade==1>  selected="selected"</#if></#if>>一级分类</option>
                <option value="2" <#if category??><#if category.grade==2>  selected="selected"</#if></#if>>二级分类</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">父级分类</label>
        <div class="layui-input-block">
            <select name="parentId" id="parentId" <#if category??><#if category.grade==1>  disabled="disabled"</#if></#if>>
                <option value="" >请选择</option>
            </select>
        </div>
    </div>

    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addOrUpdateSaleChance">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal">取消</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/category/add_update.js"></script>
</body>
</html>