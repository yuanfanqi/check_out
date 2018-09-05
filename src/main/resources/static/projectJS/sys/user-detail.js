
$(function() {
	//当点击修改用户密码时
	$("#changePasswordBtn").on("click",function(){
		var $userIdVal = $.trim($("#userId").val());
		var $userNameVal = $.trim($("#userName").val());
		
		if(!isEmpty($userIdVal) && isEmpty(!$userNameVal)){
			parent.layer.open({
				  type: 2,
				  title: "修改用户"+$userNameVal+"的密码",
				  shadeClose: true,
				  shade: 0.8,
				  area: ['30%', '40%'],
				  content: '/sys/user/play/'+$userIdVal+'/'+$userNameVal
				});
		}
	});
	//如果为admin超级管理员,则锁定所有输入框,防止被修改
	if($("#isAdmin").val()==1&&$("#userId").val().length>0){
		$("input").attr("disabled","disabled");
	}else if($("#isAdmin").val()==0&&$("#userId").val().length>0){
		$("#userName,#password").attr("disabled","disabled");
	}
	//遍历所有角色
	if($("#userId").val()&&$("#userName").val()){
		$.ajax({
			type:"POST",
			url:"/sys/user/role",
			data:{"userId":$("#userId").val(),"userName":$("#userName").val()},
			dataType:"JSON",
			success:function(response){
				var $inputArr = $("input[type=checkbox]");
				$.each(response,function(index,val){
					$.each($inputArr,function(idex,node){
						if(node.value==val){
							$(this).attr("checked",true);
						}
					});
				});
			}
		});
	}
	//当点击返回按钮时
	$("#backBtn").on("click",function(){
		history.back();
	});
	//当点击保存按钮时
	//表单验证
    $('#userForm')
        .bootstrapValidator({
            message: '无效值',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
            	userName: {
                    message: '用户名不正确',
                    validators: {
                        notEmpty: {
                            message: '用户名必填,非空'
                        }
                    }
                },
                password: {
                    message: '密码格式不正确',
                    validators: {
                        notEmpty: {
                            message: '密码必填,非空'
                        },
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: '密码长度必须在6 - 20位之间'
                        }
                    }
                },
                email: {
                    message: '邮箱格式不正确',
                    validators: {
                        notEmpty: {
                            message: '邮箱必填,非空'
                        },
                        regexp: {
                            regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                            message: '请输入格式正确的邮箱地址'
                        }
                    }
                },
                phone: {
                    message: '手机号码格式不正确',
                    validators: {
                        notEmpty: {
                            message: '手机号码必填,非空'
                        },
                        regexp: {
                            regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                            message: '手机号码必须是13,14,15,17,18开头的11位纯数字'
                        },
                    }
                },
            },
            submitButtons: '#saveBtn'
        })
        .on('success.form.bv', function (e) {
        	console.info("0000");
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            var index;

            var $userIdVal = $("#userId").val();
    		var ajaxType;
    		var index;
    		if(!$userIdVal||""){
    			ajaxType = "POST";
    		}else{
    			ajaxType = "PUT";
    		}
    		
            $.ajax({
    			type:ajaxType,
    			url : "/sys/user",
    			data : $("#userForm").serialize(),
    			dataType : "json",
    			success:function(response){
    				if(response.status==false){
    					parent.layer.alert(response.msg);
    				}else{
    					parent.layer.alert("操作成功",function(index){
    						history.back();
    						parent.layer.closeAll();
    					});
    				}
    			},
    			error:function(){
    				parent.layer.alert("新增或修改用户异常");
    			},
    			beforeSend:function(){
    				index = parent.layer.load(1,{shade:0.5});
    			},
    			complete:function(){
    				parent.layer.close(index);
    			}
    		});
            

        });

});