<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="../js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
    <link rel="stylesheet" href="../layui/css/layui.mobile.css">
    <link rel="stylesheet" href="../css/register.css">
    <title>河北联通</title>
</head>
<body>
	<% 
		String mobile = request.getParameter("mobile");
		String openId = request.getParameter("openId");
	%>
    <div class="header">
            <div class="header">
                    <img src="../img/header.png" alt="">
                </div>
                <div class="hederTitle">
                    <span>更换号码</span>
                </div>
    </div>
    <div class="content">
    		<input type="hidden" id="openid" value="<%=openId %>"/>
        <span>当前号码：</span><span id="userNum"><%=mobile %></span>
    </div>
    <div class="sub">
        <div class="btn">
            <button type="button" id="deteil">查看流量详情</button>
        </div>
        <div class="btn">
                <button type="button" id="changeNum">更换号码</button>
        </div>
    </div>
</body>
</html>
<script>
    $("#deteil").click(function(){
        $("#deteil").css("background-color","#0069D9");
        var openid = $("#openid").val();
        setTimeout(function(){
            $("#deteil").css("background-color","#199ED7");
        },150)
        window.location.href = "http://mobile99.uninforun.com/unicom-hb/Home/UserDetail?openId="+openid;
    });
    $("#changeNum").click(function(){
        $("#changeNum").css("background-color","#199ED7");
        $("#changeNum").css("color","white");
        var openid = $("#openid").val();
        setTimeout(function(){
            $("#changeNum").css("background-color","white");
            $("#changeNum").css("color","#199ED7");
        },150)
        //window.location.href="http://localhost:8085/HSDT_Activity_Port/AlipayLife/view/binding.jsp?openId="+openid;//本地
       window.location.href="http://221.192.138.29:8089/HSDT_Activity_Port/AlipayLife/view/binding.jsp?openId="+openid;
        
    });
  
</script>
