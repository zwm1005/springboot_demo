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
					searchVal" placeholder="商品名称" />
				</div>

				<div class="layui-input-inline">
					<input type="text" name="categoryName"
						   class="layui-input
					searchVal" placeholder="分类名称" />
				</div>
				<div class="layui-input-inline">
                    <select name="level"  id="level">
                        <option value="">价格区间</option>
						<option value="0">0-100</option>
						<option value="1">100-500</option>
						<option value="2">500-2000</option>
						<option value="3">2000-5000</option>
                        <option value="4">5000以上</option>
                    </select>
				</div>
				<a class="layui-btn search_btn" data-type="reload">
					<i class="layui-icon">&#xe615;</i> 搜索</a>
			</div>
		</form>
	</blockquote>
	<table id="productList" class="layui-table"  lay-filter="product"></table>
	<script type="text/html" id="toolbarDemo">
		<div class="layui-btn-container">
			<a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
				<i class="layui-icon">&#xe624;</i>
				新 增 商 品
			</a>
		</div>
	</script>
	<div class="col-12">
		<table id="categoryImg" lay-filter="categoryImg"></table>
	</div>
	<script type="text/html" id="imgtmp">
		<img src="http://q8qdwot0o.bkt.clouddn.com/{{ d.id }}.jpg" style="width:108px;height:210px;"alt="图片加载失败!"/>
		<#--<img src="${ctx}/img/product/main/{{ d.id }}.png" style="width:108px;height:210px;" alt="图片加载失败!"/>-->
	</script>
	<!--操作-->
	<script id="categoryListBar" type="text/html">
		<a class="layui-btn layui-btn-xs" id="edit" lay-event="saveImg">上传主图</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" id="edit" lay-event="delImg">删除主图</a>
		<a class="layui-btn layui-btn-xs layui-btn-warm" id="edit" lay-event="editImg">详图管理</a>
		<a class="layui-btn layui-btn-xs layui-btn-normal" id="edit" lay-event="editField">属性管理</a>
		<a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
	</script>
</form>
<script type="text/javascript" src="${ctx}/js/product/product.js"></script>
</body>
</html>