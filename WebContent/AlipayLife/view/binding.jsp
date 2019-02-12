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
    <link rel="stylesheet" href="../layui/css/layui.css">
    <link rel="stylesheet" href="../css/binding.css">
    <title>河北联通</title>
    <style>
    </style>
</head>

<body>
	<%
		String openId = request.getParameter("openId");
	%>
    <div class="header">
        <img src="../img/header.png" alt="">
    </div>
    <div class="hederTitle">
        <span>绑定号码</span>
    </div>
    <div class="content">
        <form id="formData">
            <div class="numData">
                <div class="numLeft">
                    <p>手机号</p>
                </div>
                <div class="numRight">
                    <input type="text" name="phone" id="phone" value="" placeholder="请输出手机号">
                </div>
            </div>
            <div class="codeData">
                <div class="codeLeft">
                    <p>验证码</p>
                </div>
                <div class="codeRight">
                    <input type="text" name="code" id="code" value="" placeholder="请输出验证码">
                </div>
                <div class="getCode">
                    <!-- <p id="codeMsg">获取验证码</p> -->
                    <input type="button" class="verifyBtn" onclick="setTime(this)" value="获取验证码"></input>
                </div>
            </div>
            <input type="checkbox" checked id="chose">我已阅读并同意活动规则
            <div class="btn">
                <button type="button" class="layui-btn layui-btn-normal" id="btn" onclick="subData()">立即绑定</button>
            </div>
            <input type="hidden" id="openid" value="<%=openId %>"/>
        </form>
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

        <!-- 验证消息 -->
        <span class="msg" style="display:none">
        </span>
    </div>
</body>

</html>
<script>
    $("#chose").change(function () {
        
        var checkbox = $("input[type='checkbox']").is(':checked');
        console.log(checkbox);
        if (checkbox == false) {
            // $("#btn").removeAttr("disabled", false);
            $("#btn").css("background", "#E4E4E4");
            // return false;
        } else if(checkbox == true ){
            // $("#btn").attr("disabled", true);
            $("#btn").css("background", "#199ED7");
        }
    });
    // 获取验证码
    //计时器

  var count = 60;
 	function setTime(val){
 		var phone = $("#phone").val();
   	var phoneNum = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|19[0-9])[0-9]{8}$/;

    if (phone == "" || phone == null) {
        $(".msg").show();
        $(".msg").html("手机号码不能为空");
        setTimeout(() => {
            $(".msg").hide();
            $(".msg").html("");
        }, 2000);
        return false;
    } else if (!phoneNum.test(phone)) {
        $(".msg").show();
        $(".msg").html("请填写正确的手机号");
        setTimeout(() => {
            $(".msg").hide();
            $(".msg").html("");
        }, 2000);
        return false;
    }
    if (count == 0) {
		val.removeAttribute("disabled");
  		val.value="获取验证码";
  		 count = 60;
		return;
  		}else{
  			val.setAttribute("disabled",true);
  			val.value="重新发送("+count+")";
  			count--;
  		}
    setTimeout(function(){
 			setTime(val)
 		},1000)
 	};



$(".verifyBtn").click(function() {
	var phone = $("#phone").val();
   	var phoneNum = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|19[0-9])[0-9]{8}$/;
   	var code = $("#code").val;
	if (phone == "" || !phoneNum.test(phone)) {
		$(".msg").show();
		$(".msg").html("请填写正确的手机号");
		setTimeout(() => {
    			$(".msg").hide();
    			$(".msg").html("");
		}, 2000);
		return false;
   	}else{
   		$.ajax({
   			url:'http://221.192.138.29:8089/HSDT_Activity_Port/sendcode',
			type:'POST',
			dataType:'json',
			data:{
				phoneNumber:phone
			},
			success:function(result){
				if(result.result&&result.code){
					layer.msg('获取验证码成功！');
				}else{
					layer.msg('发送失败！')
				}
			}
           });
   	}

});
    
// 验证码 发送成功
// $(".msg").show();
//                 $(".msg").html("验证码已发送，请查收"); 
//                 setTimeout(() => {
//                     $(".msg").hide();
//                     $(".msg").html("");
//                 }, 2000);

    //表单提交
    function subData() {
        var phone = $("#phone").val();
        var phoneNum = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|19[0-9])[0-9]{8}$/;
        var checkbox = $("input[type='checkbox']").is(':checked');
        console.log(checkbox);
        var code = $("#code").val();
        var openId = $('#openid').val();
        if ($("input[type='checkbox']").is(':checked') == false) {
            $(".msg").show();
            $(".msg").html("请阅读并同意活动规则");
            setTimeout(() => {
                $(".msg").hide();
                $(".msg").html("");
            }, 2000);
            return false;
        }
        // else{
        //         $(".msg").hide();
        //         console.log("123123")
        // } 
        if (phone == "" || phone == null) {
            $(".msg").show();
            $(".msg").html("手机号码不能为空");
            setTimeout(() => {
                $(".msg").hide();
                $(".msg").html("");
            }, 2000);
            return false;
        } else if (!phoneNum.test(phone)) {
            $(".msg").show();
            $(".msg").html("请填写正确的手机号！");
            setTimeout(() => {
                $(".msg").hide();
                $(".msg").html("");
            }, 2000);
            return false;
        } else if (code == "" || code == null) {
            $(".msg").show();
            $(".msg").html("请填写验证码");
            setTimeout(() => {
                $(".msg").hide();
                $(".msg").html("");
            }, 2000);
            return false;
        } else{
    		$.ajax({
    			url:'http://221.192.138.29:8089/HSDT_Activity_Port/savebind',//验证码验证和号码与支付宝ID进行绑定
    			type:'POST',
    			dataType:'json',
    			data:{
    				mobile:phone,
    				code:code,
    				openId:openId
    			},
    			success:function(result){
    				if(result.code==0){
    					$(".msg").show();
    		            $(".msg").html("绑定成功！");
    		            setTimeout(() => {
    		                $(".msg").hide();
    		                $(".msg").html("");
    		            }, 2000);
    		            window.location.href="http://221.192.138.29:8089/HSDT_Activity_Port/AlipayLife/view/bindsuc.jsp?mobile="+phone+"&openId="+openId;
    				}else if(result.code==1){
    					$(".msg").show();
    		            $(".msg").html("验证码不正确！");
    		            setTimeout(() => {
    		                $(".msg").hide();
    		                $(".msg").html("");
    		            }, 2000);
    				}else{
    					$(".msg").show();
    		            $(".msg").html("号码绑定失败，请稍后重试.......!");
    		            setTimeout(() => {
    		                $(".msg").hide();
    		                $(".msg").html("");
    		            }, 2000);
    				}
    			}
    		});
    } 
    }
   
</script>