$(function(){
	//表单验证
    $('#bwListForm')
        .bootstrapValidator({
            message: '无效值',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
            	securityCode: {
                    message: '基金代码格式不正确',
                    validators: {
                        notEmpty: {
                            message: '基金代码必填,非空'
                        },
                        regexp: {
                            regexp: /^\d{6}$/,
                            message: '基金代码只能是6位数字'
                        }
                    }
                },
                publishDate: {
                    message: '发布日期不能为空',
                    validators: {
                        notEmpty: {
                            message: '发布日期必填,非空'
                        }
                    }
                },
                limitDate: {
                    message: '限制日期不能为空',
                    validators: {
                        notEmpty: {
                            message: '限制日期必填,非空'
                        }
                    }
                }
            },
            submitButtons: '#createBtn'
        })
        .on('success.form.bv', function (e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            var index;

            //区分新增和修改
            var url = '';
            var data = $form.serializeObject();
			if(isEmpty(obj.type) || isEmpty(obj.publishDate) || isEmpty(obj.securityCode) || isEmpty(obj.fundType)){
					data["securityName"] = resFX.fundName;
					data["fundType"] = resFX.fundType;
					url = '/bwList';
					var msg = '添加成功!';
			}else{
				data["securityName"] = obj.securityName;
				data["fundType"] = obj.fundType;
				data["securityCode"] = obj.securityCode;
				url = '/bwList/modify';
				var msg = '修改成功!';
			}
            $.ajax({
                type: 'POST',
                url: url,
                contentType:'application/x-www-form-urlencoded',
    			data:data,
    			dataType:'json',
                success: function (res) {
                	if (res.status == true) {
                	$('#bwListForm').data('bootstrapValidator').disableSubmitButtons(true);
                	console.log(res);
                	parent.layer.alert(msg,function(index){
                      location.href = '/bwList/show';
                      parent.layer.closeAll();
                  });
                 } else {
                         parent.layer.alert(res.msg);
                         console.log(res.error);
                         console.trace(res.stack);
                     }
                },
				error:function(XMLHttpRequest, textStatus, errorThrown){
					console.error(XMLHttpRequest);
					console.error(textStatus);
					console.error(errorThrown);
					parent.layer.alert("服务内部处理异常,请联系管理员!");
				},
				beforeSend:function(){
					layerIndex = parent.layer.load(1,{shade:0.5});
				},
				complete:function(){
					parent.layer.close(layerIndex);
				}
            });

        });
});