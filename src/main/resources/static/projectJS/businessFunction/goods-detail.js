$(function(){
	//表单验证
    $('#goodsForm')
        .bootstrapValidator({
            message: '无效值',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
            	goodsId: {
                    message: '商品条码格式不正确',
                    validators: {
                        notEmpty: {
                            message: '商品条码必填,非空'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: '商品条码只能是数字'
                        }
                    }
                },
                goodsName: {
                    message: '商品名称不能为空',
                    validators: {
                        notEmpty: {
                            message: '商品名称必填,非空'
                        }
                    }
                },
                goodsPrice: {
                    message: '商品价格不能为空',
                    validators: {
                        notEmpty: {
                            message: '商品价格必填,非空'
                        }
                    }
                },
                goodsType: {
                    message: '商品类型不能为空',
                    validators: {
                        notEmpty: {
                            message: '商品类型必填,非空'
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
			if(isEmpty(tableGoods.goodsId)){
//					data["goodsName"] = resFX.fundName;
//					data["goodsType"] = resFX.fundType;
					url = '/goods/add';
					var msg = '添加成功!';
			}else{
				data["goodsName"] = tableGoods.goodsName;
				data["goodsType"] = tableGoods.goodsType;
				data["goodsId"] = tableGoods.goodsId;
				url = '/goods/modify';
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
                	$('#goodsForm').data('bootstrapValidator').disableSubmitButtons(true);
                	console.log(res);
                	parent.layer.alert(msg,function(index){
                      location.href = '/goods/show';
                      parent.layer.closeAll();
                  });
                 } else {
                         parent.layer.alert(res.msg);
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
    
    //验证是否为修改界面
	if(!isEmpty(tableGoods.goodsId)){
		$('#goodsId').attr('disabled', true);
		$('#goodsName').attr('disabled', true);
		$('#goodsNum').attr('disabled', true);
	}
	
	
  /*后期有可能用到   关于表单提交修改之后会出现的按钮显示问题
   * //日期校验是否为空
    $("#publishDate,#limitDate").on('change',function(){
    	var publishDate = $("#publishDate").val();
    	var limitDate = $("#limitDate").val();
        if(!$.isEmpty(publishDate) || !$.isEmpty(limitDate)){
//        	$('#createBtn').attr('disabled', true);
        	$('#bwListForm').data('bootstrapValidator').resetForm();
        }
    });
	
	//初始化selectpicker
	$('#fundType').selectpicker({noneSelectedText:'未匹配到该基金相关信息'});*/
});

//商品条码验证：1、是否存在于数据库 2、关联id和name
function goodsIdFX(){
	var goodsId = $("#goodsId").val();
	//判断是否符合校验规则
	if(!$.isEmpty(goodsId)){
		//判断是否唯一存在于数据库
		$.ajax({
		url:'/goods/isExist',
		type:'POST',
		data:{goodsId},
		dataType:'json',
		success:function(result){
			if(!result.status){
				parent.layer.alert(result.msg);
				$('#createBtn').attr("disabled",true);
			}
		}
	});
	}else{
		console.log("不符合校验规则的不处理");
	}
}
