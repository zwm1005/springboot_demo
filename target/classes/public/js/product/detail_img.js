layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //营销机会列表展示
    var  tableIns = table.render({
        elem: '#productDetailImageList',
        url : ctx+'/productImage/list?pId='+$("input[name='pId']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "productListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'productDetailImg', title: '商品详图',align:"center",templet: "#imgtmp"},
            {title: '操作', templet:'#categoryListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });

    // 头工具栏事件
    table.on('toolbar(product)',function (obj) {
        switch (obj.event) {
            case "add":
                openAddProductDetialImgDialog($("input[name='pId']").val());
                break;
        }
    });

    table.on('tool(product)',function (obj) {
          var layEvent =obj.event;
           if(layEvent === "del"){
              layer.confirm("确认删除当前记录?",{icon: 3, title: "分类管理管理"},function (index) {
                  $.post(ctx+"/category/delete",{id:obj.data.id},function (data) {
                      if(data.code==200){
                          layer.msg("删除成功");
                          tableIns.reload();
                      }else{
                          layer.msg(data.msg);
                      }
                  })
              })
          }else if(layEvent === "view"){
               openProductBigDetialImgDialog(obj.data.id);
           }
    });
    function openAddProductDetialImgDialog(id) {
        var title="上传商品详图";
        var url=ctx+"/product/toSaveDetailImgPage?flag=2&pId="+id;
        layui.layer.open({
            title:title,
            type:2,
            area:["700px","500px"],
            maxmin:true,
            content:url
        })
    }
    function openProductBigDetialImgDialog(id) {
        var title="商品大图";
        var url=ctx+"/product/toBigDetailImgPage?id="+id;
        layui.layer.open({
            title:title,
            type:2,
            area:["800px","600px"],
            maxmin:true,
            content:url
        })
    }
});
