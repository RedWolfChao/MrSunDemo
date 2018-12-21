<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>登录</title>
    <script src="resource/jquery-3.3.1.min.js"></script>
    <script src="resource/common.js"></script>
</head>
<body onload="init()">
<style>
    #id_home_sys_manager {
        display: flex;
        border: solid 1px;
        width: 100%;
        height: 100%;
    }

    .left-list {
        width: 20%;
        border-right: solid 1px;
        text-align: center;
    }

    .right-body {
        width: 80%;
    }

    .btn-default {
        background: gray;
        margin: 2px;
        color: #000;
        padding: 5px;
        display: block;
    }

    a {
        text-decoration: none;
        color: black;
    }

    .btn-select {
        display: block;
        font-weight: bold;
        background: deepskyblue;
        color: #FFF;
        margin: 2px;
        padding: 5px;
    }

    .right-body a {
        text-decoration: darkblue;
        color: darkblue;
    }
</style>
<div id="id_login_div">
    <div style="text-align: center;width:100%;font-size: 24px;">XXX系统</div>
    <div style="text-align: center;width:100%;margin-top: 10px;">账号 :
        <input id="id_input_user_id" placeholder="请输入账号"/>
    </div>
    <div style="text-align: center;width:100%;margin-top: 10px;">密码 :
        <input id="id_input_user_pass" placeholder="请输入密码" type="password"/>
    </div>
    <div style="text-align: center;width:100%;margin-top: 10px;">
        <button onclick="login()">登录</button>
    </div>
</div>
<!-- 系统管理员界面 -->
<div id="id_home_sys_manager" style="display: none;">
    <!--左侧列表-->
    <div class="left-list">
        <a id="id_user_manager" class="btn-default" href="javascript:toUserManager()">用户管理</a>
        <a id="id_pro_model_manager" class="btn-default" href="javascript:toProModelManager()">科研模板管理</a>
    </div>
    <!-- 右侧body -->
    <div class="right-body">
        <!-- 用户管理 -->
        <div id="id_sys_manager_man">
            <div>
                <a href="javascript:addUser()">添加用户</a>
            </div>
            <!-- 列表 -->
            <div>

            </div>
        </div>
        <!-- 添加用户 -->
        <div id="id_sys_manager_edit">
            <h1>我是添加用户部分</h1>
        </div>
    </div>

</div>
<script>
    //  全局变量 START
    var currUserModel;

    //  全局变量 END

    function init() {
        //  隐藏不应该被显示的部分
        $("#id_home_sys_manager").hide();
        $("#id_sys_manager_edit").hide();

    }

    function toUserManager() {
        //  ProModel Btn变回原来的颜色
        $("#id_pro_model_manager").removeClass("btn-select");
        //  UserManager Btn变色
        $("#id_user_manager").addClass("btn-select");
        //  查询所有用户
        selectAllUserList();


    }

    function selectAllUserList() {
        $.ajax({
            url: "sys",//urlPatterns
            data: {
                urlType: "selectAllUserList"
            },
            type: "GET",
            success: function (ret) {
                logI("成功",ret);

            }, error: function (ret) {

            }
        });
    }

    function toProModelManager() {
        //  UserManager Btn变回原来的颜色
        $("#id_pro_model_manager").addClass("btn-select");
        //  ProModel Btn变色
        $("#id_user_manager").removeClass("btn-select");
    }

    function login() {
        var uName = $("#id_input_user_id").val();
        var uPass = $("#id_input_user_pass").val();
        if (isEmpty(uName)) {
            alert("请输入用户名!");
            return;
        }
        if (isEmpty(uPass)) {
            alert("请输入密码!");
            return;
        }

        $.ajax({
            url: "login",//urlPatterns
            data: {
                uName: uName,
                uPass: uPass
            },
            type: "GET",
            success: function (ret) {
                //
                if (!isEmpty(ret)) {
                    if (ret.code === "0") {
                        currUserModel = ret;
                        $("#id_home_sys_manager").show();
                        $("#id_login_div").hide();
                        //  登录后应该默认进入用户管理
                        toUserManager();
                        logI("成功", ret);
                    } else {
                        alert(ret.msg);
                    }
                }

            }, error: function (ret) {
                logI("失败", ret);
            }
        });
    }

    function addUser() {
        console.log("addUser");
        //  添加用户 隐藏用户管理 显示添加用户
        $("#id_sys_manager_edit").show();
        $("#id_sys_manager_man").hide();
    }
</script>
</body>
</html>
