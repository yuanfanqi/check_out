$(function(){
			//当点击取消时
			$("#backBtn").on("click",function(){
				parent.layer.closeAll();
			});
			//当点击确定时
			$("#saveBtn").on("click",function(){
//				var $userIdVal = $.trim($("#userId").val());
				var $userIdVal = parseInt($("#userId").val());
				var $userNameVal = $.trim($("#userName").text());
				var $passwordVal = $.trim($("#password").val());
				var $rePasswordVal = $.trim($("#rePassword").val());
				var reg1 = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
				var $firstLatter = $passwordVal.substring(0,1);
				
				if(isEmpty($passwordVal)){
					layer.msg("新密码不能为空");
					$("#password").focus();
					return;
				}else if(isEmpty($rePasswordVal)){
					layer.msg("确认密码不能为空");
					$("#rePassword").focus();
					return;
				}else if(!isNaN($firstLatter)){
					layer.alert("新密码不能以数字开头");
					$("#password").focus();
					return;
				}else if(reg1.test($passwordVal)==false){
					console.log(false);
					layer.msg("新密码长度必须为8-30位之间，不能以数字开头，且必须包含数字特殊符号和英文字符，请重新输入");
					$("#password").focus();
					return;
				}else if($passwordVal != $rePasswordVal){
					layer.msg("两次密码不一致");
					return;
				}else{
					$.ajax({
						type:"PUT",
						url:"/sys/user/pwd",
						data:{
							userId:$userIdVal,
							userName:$userNameVal,
							password:$passwordVal
						},
						dataType:"JSON",
						success: function(result) {
							if(result.status==false){
								parent.layer.alert(result.msg);
							}else{
								parent.layer.alert("操作成功",function(index){
									history.back();
									parent.layer.closeAll();
								});
							}
						},
						error: function() {
							parent.layer.msg("执行操作过程中出现错误");
						},
						beforeSend: function() {
							index = parent.layer.load(1, {
								shade: 0.5
							});
						},
						complete: function() {
							parent.layer.close(index);
						}
					});
				}
			});
		});