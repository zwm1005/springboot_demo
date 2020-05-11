<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
        <meta charset="utf-8">
        <title>Layui</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="${ctx}/lib/layui-v2.5.5/css/layui.css"  media="all">
        <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;" enctype="multipart/form-data">
    <input type="hidden" id="cId" name="cId" value="${(cId)!}">
    <input type="hidden" id="pId" name="pId" value="${(pId)!}">
    <input type="hidden" id="flag" name="flag" value="${(flag)!}">
    <div class="layui-upload">

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>上传多张图片</legend>
        </fieldset>

        <div class="layui-upload">
            <button type="button" class="layui-btn" id="test2">多图片上传</button>
            <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                预览图：
                <div class="layui-upload-list" id="demo2"></div>
            </blockquote>
        </div>
        <button type="button" class="layui-btn" id="testListAction">开始上传</button>
    </div>
</form>

<script src="${ctx}/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('upload', function(){
        var $ = layui.jquery
            ,upload = layui.upload;
        //$("#testListAction").click(function () {


            //多图片上传
            upload.render({
                elem: '#test2'
                ,url: ctx+"/product/saveImg?cId="+$("input[name='cId']").val()+"&flag="+$("input[name='flag']").val()+"&pId="+$("input[name='pId']").val() //改成您自己的上传接口
                ,multiple: true
                ,before: function(obj){
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
                    });
                }
                ,done: function(res){
                    //上传完毕
                    //如果上传失败
                    if(res.code == 200){
                        layer.msg('上传成功!');
                        //重新刷新父容器  重新加载表格
                        parent.location.reload();
                    }else {
                        layer.msg(res.msg);
                    }
                }
            })

    });
    </script>
</body>
</html>