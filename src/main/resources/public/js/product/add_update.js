layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    $.post(ctx+"/category/queryAllChildCategory",function (res) {
        for(var i=0;i<res.length;i++){
            if($("input[name='cid']").val() == res[i].id){
                $("#cid").append("<option value=\""+res[i].id+"\"  selected='selected' >"+res[i].name+"</option>");
            }else{
                $("#cid").append("<option value=\""+res[i].id+"\"   >"+res[i].name+"</option>");
            }
        }
        // 重新渲染下拉框内容
        layui.form.render("select");
    });

    form.on('submit(addOrUpdateProduct)',function (data) {
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var url = ctx+"/product/save";
        if($("input[name='id']").val()){
            url = ctx + "/product/update";
                var x = data.field.originalPrice.split(',');
                parseInt(x.join(""));
        }

        $.post(url,data.field,function (res) {
            if(res.code==200){
                top.layer.msg("操作成功!");
                layer.close(index);
                layer.closeAll("inframe");
                //重新刷新父容器  重新加载表格
                parent.location.reload();
            }else{
                layer.msg(res.msg)
            }
        });
        return false;
    });
});