<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input type="hidden" name="id"  value="${(product.id)!}">
    <input type="hidden" name="cid"  value="${(product.cid)!}">
    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">商品名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="name" id="name"  value="${(product.name)!}"  placeholder="请输入商品名称">
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">标题</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="subTitle" id="subTitle"  value="${(product.subTitle)!}"  placeholder="请输入商品标题">
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">原价</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="originalPrice" id="originalPrice"  value="${((product.originalPrice)!)?c}"  placeholder="请输入商品原价">
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">促销价格</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                    name="promotePrice" id="promotePrice"  value="${(product.promotePrice)!}"  placeholder="请输入商品促销价格">
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">库存数量</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"
                   lay-verify="required" name="stock" id="stock"  value="${(product.stock)!}"  placeholder="请输入商品库存数量">
        </div>
    </div>

    <div class="layui-form-item layui-row layui-col-xs12">
        <label class="layui-form-label">父级分类</label>
        <div class="layui-input-block">
            <select name="cid" id="cid" >
                <option value="" >请选择</option>
            </select>
        </div>
    </div>

    <br/>
    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addOrUpdateProduct">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal">取消</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/js/product/add_update.js"></script>
</body>
</html>