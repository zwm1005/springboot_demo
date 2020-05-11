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
	<input type="hidden" id="pId" name="pId" value="${(pId)!}">
	<input type="hidden" id="flag" name="flag" value="${(flag)!}">
	<input type="hidden" id="id" name="id" value="${(id)!}">
	<table id="productDetailImageList" class="layui-table"  lay-filter="product"></table>
	<script type="text/html" id="toolbarDemo">
		<div class="layui-btn-container">
			<a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
				<i class="layui-icon">&#xe624;</i>
				上 传 图 片
			</a>
		</div>
	</script>
	<div class="col-12">
		<table id="productDetailImg" lay-filter="productDetailImg"></table>
	</div>
	<script type="text/html" id="imgtmp">
		<img src="${ctx}/img/product/detail/{{ d.id }}.png" style="width:108px;height:210px;" alt="图片加载失败!"/>
	</script>
	<!--操作-->
	<script id="categoryListBar" type="text/html">
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="view">查看大图</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
	</script>
</form>
<script type="text/javascript" src="${ctx}/js/product/detail_img.js"></script>
</body>
</html>