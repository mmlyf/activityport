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
    <link rel="stylesheet" href="../css/bindSuccess.css">
    <title>河北联通</title>
</head>
<body>
	<% 
		String phone = request.getParameter("mobile");
		String openId = request.getParameter("openId");
	%>
    <div class="header">
        <div class="header">
                <img src="../img/header.png" alt="">
            </div>
            <div class="hederTitle">
                <span class="bdsuccess">绑定成功</span>
                <div class="userPhone">
                    <span>您的手机号</span><span><%=phone %></span>
                </div>
                <div>
                    <span class="successMsg">恭喜您绑定成功</span>
                </div>
                <input type="hidden" id="openId" value="<%=openId %>"/>
            </div>
    </div>
    <div class="sub">
        <div class="btn">
            <button type="button" id="deteil">查看流量详情</button>
        </div>
    </div>
    <div class="reader">
        <div>
            <span>活动规则</span>
        </div>
        <div>
            <span>1、首次绑定河北联通支付宝生活号的用户，可获赠200M免费流量。</span>
        </div>
        <div>
            <span>2、领取规则：关注当月可赠送200M流量，需保持手机正常状态且已关注支付宝生活号河北联通官方旗舰店。</span>
        </div>
        <div>
            <span>3、参与用户为河北联通2G、3G、4G用户</span>
        </div>
        <div>
            <span>4、活动期间，同一号码仅赠一次。</span>
        </div>
        <div>
            <span>5、免费流量仅当月生效，剩余流量清零并不迁转</span>
        </div>
        <div>
            <span>6、个性化封顶产品、固网融合套餐、停机、欠费、黑名单、身份资料不全、未修改初始密码、特殊资费套餐等情况可能导致无法领取。</span>
        </div>
        <div>
            <span>7、赠送流量使用详情可在生活号余额查询中查询，如遇延时请稍后查询</span>
        </div>
    </div>
	<script>
		$("#deteil").click(function() {
			var openid = $("#openId").val();
			window.location.href = "http://mobile99.uninforun.com/unicom-hb/Home/UserDetail?openId="+openid;
		});
	</script>
</body>
</html>