//提交登录
function doLogin(){
	var $username = $("#username");
	var $password = $("#password");
	var $randomcode = $("#randomcode");
	var flag = true;
	
	if(!$.trim($username.val())||""){
		$("#tipbox").show();
		$("#tips").text("请输入账户名");
		flag = false;
	}else if(!$.trim($password.val())||""){
		$("#tipbox").show();
		$("#tips").text("请输入密码");
		flag = false;
	}else if(!$.trim($randomcode.val())||""){
		$("#tipbox").show();
		$("#tips").text("请输入验证码");
		flag = false;
	}
	if(flag){
		var index;
		$.ajax({
			type: "post",
	        url: "/sys/doLogin",
	        data: {"username": $username.val(), "password": $password.val(), "randomcode": $randomcode.val()},
	        dataType: "json",
	        success:function(result){
	        	if(!result||""){
	        		parent.layer.alert("请刷新页面或稍后再试");
	        	}else{
	        		var status = result.status;
	        		var msg = result.msg;
	        		if(status){
	        			location.href="/sys/index";
	        		}else{
	        			$("#tips").text(msg);
	        			$("#tipbox").show();
	        			$("#validateCodeImg").attr("src","/sys/randcode?a=" + Math.random());
	        		}
	        	}
	        },
	        error:function(){
	        	layer.open({
	        		content:"当前会话已过期!点击确定刷新页面后重新登录",
	        		yes:function(){
	        			location.reload();
	        		},
	        		success:function(){
	        			$("#username,#password,#randomcode").unbind("keydown");
	        		}
	        	});
	        },
			beforeSend:function(){
				index = parent.layer.load(1,{shade:0.5});
			},
			complete:function(){
				parent.layer.close(index);
			}
		});
	}
}

$(function(){
	if(!$("#tips").text()||""){
		$("#tipbox").hide();
	}
	//获取验证码
	$("#validateCodeImg").on("click",function(){
		$(this).attr("src","/sys/randcode?a=" + Math.random());
	});
	$("#validateCodeBtn").on("click",function(){
		$("#validateCodeImg").attr("src","/sys/randcode?a=" + Math.random());
	});
	
	//点击提交登陆信息
	$("#loginBtn").on("click",function(){
		doLogin();
	});
	
	$("#username,#password,#randomcode").bind("keydown",function(event){
		//回车键的键值为13
		if (event.keyCode==13){
			doLogin();
		}
	});
	
});