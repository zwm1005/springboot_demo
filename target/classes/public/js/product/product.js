layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //营销机会列表展示
    var  tableIns = table.render({
        elem: '#productList',
        url : ctx+'/product/list',
        cellMinWidth : 92,
        width:'100%',
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "productListTable",
        cols : [[
            {type: "checkbox",fixed:"center"},
            {field: "id", title:'编号', fixed:"true"},
            {field: 'productMainImg', title: '主图',align:"center",templet: "#imgtmp"},
            {field: 'name', title: '名称',align:"center"},
            {field: 'subTitle', title: '标题',  align:'center'},
            {field: 'originalPrice', title: '原价',  align:'center'},
            {field: 'promotePrice', title: '促销价',  align:'center'},
            {field: 'cid', title: '分类',align:'center'},
            {field: 'createDate', title: '创建时间' ,align:'center'},
            {field: 'updateDate', title: '更新时间', align:'center'},
            {title: '操 作', templet:'#categoryListBar',width:'35%' ,fixed:"right",align:"center", minWidth:150}
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click",function () {

        table.reload("productListTable",{
            page:{
                curr:1
            },
            where:{
                name:$("input[name='name']").val(),// 商品名
                categoryName:$("input[name='categoryName']").val(),// 分类名
                level:$("#level").val()    //价格区间
            }
        })
    });


    // 头工具栏事件
    table.on('toolbar(product)',function (obj) {
        switch (obj.event) {
            case "add":
                openAddOrUpdateProductDialog();
                break;
            case "del":
                //console.log(table.checkStatus(obj.config.id).data);
                delSaleChance(table.checkStatus(obj.config.id).data);
                break;
            case "delImg":
                delCategoryImg();
                break;
        }
    });


    function delSaleChance(datas){
        /**
         * 批量删除
         *   datas:选择的待删除记录数组
         */
        if(datas.length==0){
            layer.msg("请选择待删除记录!");
            return;
        }

        layer.confirm("确认删除选中的 "+datas.length+" 条记录?",{icon:3,title: "机会数据管理"
            //btn:['01','02']  可自己定制确认框的两个按钮上的字  默认为: 确认  取消
        },function (index) {
            layer.close(index);
            // ids=10&ids=20&ids=30
            var ids="ids=";
            for(var i=0;i<datas.length;i++){
                if(i<datas.length-1){
                    ids=ids+datas[i].id+"&ids=";
                }else{
                    ids=ids+datas[i].id;
                }
            }

            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/deleteBatch",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        layer.msg("删除成功!");
                        tableIns.reload();
                    }else{
                        layer.msg(data.msg);
                    }
                }
            })



        })


    }



    table.on('tool(product)',function (obj) {
          var layEvent =obj.event;
          if(layEvent === "edit"){
              openAddOrUpdateProductDialog(obj.data.id);
          }else if(layEvent === "del"){
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
          }else if(layEvent === "saveImg"){
              openAddProductMainImgDialog(obj.data.id);
          }else if(layEvent === "editImg"){
              openAddProductDetialImgDialog(obj.data.id);
          }

    });



    /**
     * 打开添加或更新对话框
     */
    function openAddOrUpdateProductDialog(id) {
        console.log(ctx+'===================');
        var title="商品管理-商品添加";
        var url=ctx+"/product/addOrUpdateProductPage";
        if(id){
            title="商品管理-商品更新";
            url=url+"?id="+id;
        }
        layui.layer.open({
            title:title,
            type:2,
            area:["700px","500px"],
            maxmin:true,
            content:url
        })
    }

    function openAddProductMainImgDialog(id) {
        var title="详图管理-上传商品主图";
        var url=ctx+"/product/toSaveMainImgPage?flag=1&pId="+id;
        layui.layer.open({
            title:title,
            type:2,
            area:["700px","500px"],
            maxmin:true,
            content:url
        })
    }


    function openAddProductDetialImgDialog(id) {
        var title="商品管理-详图管理";
        var url=ctx+"/product/toDetailImgPage?flag=2&pId="+id;
        layui.layer.open({
            title:title,
            type:2,
            area:["850px","620px"],
            maxmin:true,
            content:url
        })
    }
});
