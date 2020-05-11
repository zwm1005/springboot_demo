layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    // 用户登录 表单提交  进行登录操作
    form.on('submit(login)', function (data) {
        //获取表单元素  用户名和密码
        data = data.field;
        console.log(data);
        /**
         * 参数非空校验
         */
        if(data.username=="undefined"||data.username==""||data.username.trim()==""){
            layer.msg("用户名不能为空!");
            return false;
        }
        if(data.password=="undefined"||data.password==""||data.password.trim()==""){
            layer.msg("用户密码不能为空!");
            return false;
        }

        $.ajax({
           type:"post",
            url:ctx+"/user/login",
            data:{
               userName:data.username,
                userPwd:data.password
            },
            dataType:"json",
            success:function (resultInfo) {
                if(resultInfo.code==200){
                    layer.msg("登陆成功!");
                    var result = resultInfo.result;
                    $.cookie("userIdStr",result.userIdStr);
                    $.cookie("userName",result.userName);
                    $.cookie("trueName",result.trueName);
                    //如果用户勾选了记住密码 设置失效日期为七天
                    if($("input[type='checkbox']").is(':checked')){
                        $.cookie("userIdStr",result.userIdStr,{expires:7});
                        $.cookie("userName",result.userName,{expires:7});
                        $.cookie("trueName",result.trueName,{expires:7});
                    }
                    window.location.href=ctx+"/main";

                }else{
                    layer.msg(resultInfo.msg);
                }
            }

        });



















/*        data = data.field;
        if ( data.username =="undefined" || data.username =="" || data.username.trim()=="") {
            layer.msg('用户名不能为空');
            return false;
        }
        if ( data.password =="undefined" || data.password =="" || data.password.trim()=="")  {
            layer.msg('密码不能为空');
            return false;
        }
        $.ajax({
            type:"post",
            url:ctx+"/user/login",
            data:{
                userName:data.username,
                userPwd:data.password
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    layer.msg('登录成功', function () {
                        var result =data.result;
                        $.cookie("userIdStr",result.userIdStr);
                        $.cookie("userName",result.userName);
                        $.cookie("trueName",result.trueName);
                        // 如果点击记住我 设置cookie 过期时间7天
                        if($("input[type='checkbox']").is(':checked')){
                            // 写入cookie 7天
                            $.cookie("userIdStr",result.userIdStr, { expires: 7 });
                            $.cookie("userName",result.userName, { expires: 7 });
                            $.cookie("trueName",result.trueName, { expires: 7 });
                        }
                        window.location.href=ctx+"/main";
                    });
                }else{
                    layer.msg(data.msg);
                }
            }
        });*/
        return false;
    });





    // 打开验证邮箱页面
    function openAddOrUpdateCusDevPlanDialog(id){
        var url  =  ctx+"/user/verify?to="+$("input[name='id']").val();
        console.log($("input[name='id']").val());
        var title="计划项管理-添加计划项";
        if(id){
            url = url+"&id="+id;
            title="计划项管理-更新计划项";
        }
        layui.layer.open({
            title : title,
            type : 2,
            area:["700px","400px"],
            maxmin:true,
            content : url
        });
    }


});