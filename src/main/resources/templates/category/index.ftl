<!DOCTYPE html>
<html>
<head>
	<title>分类管理</title>
	<#include "../common.ftl">
	<style>
		.layui-table img {
			width: auto;
			height: auto ;
			max-width: 100%;
			max-height: 100%;
		}
		.layui-table-cell{	    text-align:center;	    height: 40px;	    white-space: normal;
	</style>
</head>
<body class="childrenBody">

<form class="layui-form" >
	<blockquote class="layui-elem-quote quoteBox">
		<form class="layui-form">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<input type="text" name="name"
						   class="layui-input
					searchVal" placeholder="分类名称" />
				</div>

				<div class="layui-input-inline">
					<input type="text" name="pName"
						   class="layui-input
					searchVal" placeholder="父级分类名称" />
				</div>
				<div class="layui-input-inline">
                    <select name="grade"  id="grade">
                        <option value="">分类级别</option>
                        <option value="1">一级分类</option>
                        <option value="2">二级分类</option>
                    </select>
				</div>
				<a class="layui-btn search_btn" data-type="reload">
					<i class="layui-icon">&#xe615;</i> 搜索</a>
			</div>
		</form>
	</blockquote>
	<table id="categoryList" class="layui-table"  lay-filter="category"></table>
	<script type="text/html" id="toolbarDemo">
		<div class="layui-btn-container">
			<a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
				<i class="layui-icon">&#xe624;</i>
				新 增 分 类
			</a>
		</div>
	</script>
	<div class="col-12">
		<table id="categoryImg" lay-filter="categoryImg"></table>
	</div>
	<script type="text/html" id="imgtmp">
		<img src="${ctx}/img/category/{{ d.id }}.png" style="width:108px;height:210px;" alt="图片加载失败!"/>
	</script>
	<!--操作-->
	<script id="categoryListBar" type="text/html">
		<a class="layui-btn layui-btn-xs" id="edit" lay-event="saveImg">上传图片</a>
		<a class="layui-btn layui-btn-xs" id="edit" lay-event="delImg">删除图片</a>
		<a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
	</script>
</form>
<script type="text/javascript" src="${ctx}/js/category/category.js"></script>
</body>
</html>