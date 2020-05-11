layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //营销机会列表展示
    var  tableIns = table.render({
        elem: '#categoryList',
        url : ctx+'/category/list',
        cellMinWidth : 138,
        width:'100%',
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "categoryListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'categoryImg', title: '图片',align:"center",templet: "#imgtmp"},
            {field: 'name', title: '名称',align:"center"},
            {field: 'parentId', title: '父级分类',  align:'center'},
            {field: 'grade', title: '级别', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'updateDate', title: '更新时间', align:'center'},
            {title: '操作',width:'30%', templet:'#categoryListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click",function () {
        console.log(ctx+'===================');
        var parentName = $("input[name='parentName']").val();
        if($("#grade").val()==1){
            if (parentName!=null && parentName!=''){
                layer.msg("一级分类暂不支持存在父级分类!")
            }
        }

        table.reload("categoryListTable",{
            page:{
                curr:1
            },
            where:{
                name:$("input[name='name']").val(),// 分类名
                pName:$("input[name='pName']").val(),// 父级分类名
                grade:$("#grade").val()    //分类级别
            }
        })
    });


    // 头工具栏事件
    table.on('toolbar(category)',function (obj) {
        switch (obj.event) {
            case "add":
                openAddOrUpdateCategoryDialog();
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



    table.on('tool(category)',function (obj) {
          var layEvent =obj.event;
          if(layEvent === "edit"){
              openAddOrUpdateCategoryDialog(obj.data.id);
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
              openAddCategoryImgDialog(obj.data.id);
          }

    });



    /**
     * 打开添加或更新对话框
     */
    function openAddOrUpdateCategoryDialog(id) {
        console.log(ctx+'===================');
        var title="分类管理-分类添加";
        var url=ctx+"/category/addOrUpdateCategoryPage";
        if(id){
            title="分类管理-分类更新";
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
    function openAddCategoryImgDialog(id) {
        var title="分类管理-上传分类图片";
        var url=ctx+"/category/toSaveImgPage?cId="+id;
        layui.layer.open({
            title:title,
            type:2,
            area:["700px","500px"],
            maxmin:true,
            content:url
        })
    }
});
