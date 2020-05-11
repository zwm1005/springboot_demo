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
    <div class="layui-upload">
        <button type="button" class="layui-btn" id="categoryImg">点击上传图片</button>
        <div class="layui-upload-list">
            <img class="layui-upload-img" id="preView">
            <p id="demoText"></p>
        </div>
    </div>
</form>

<script src="${ctx}/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('upload', function(){
        var $ = layui.jquery
            ,upload = layui.upload;
        //普通图片上传
        var uploadInst = upload.render({
            elem: '#categoryImg'
            ,url: ctx+"/category/saveImg?cId="+$("input[name='cId']").val()//改成您自己的上传接口
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#preView').attr('src', result); //图片链接（base64）
                });
            }
            ,done: function(res){
                //如果上传失败
                if(res.code == 200){
                    layer.msg('上传成功!');
                    //重新刷新父容器  重新加载表格
                    parent.location.reload();
                }else{
                    layer.msg(res.msg);
                    //演示失败状态，并实现重传
                    var demoText = $('#demoText');
                    demoText.html('<span style="color: #FF5722;" id="span">上传失败!</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function(){
                        uploadInst.upload();
                    });

                   // return layer.msg(res.msg);
                }
                //上传成功
            }
        /*    ,error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }*/
        }); });
    </script>
</body>
</html>