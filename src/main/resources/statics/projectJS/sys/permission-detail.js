var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "parentId",
			rootPId : -1
		},
		key : {
			name:"permissionName",
			url : "nourl"
		}
	}
};
var ztree;
$(function(){
	var typeVal = $("input[type=radio][name=type]:checked").val();
	if(typeVal==2){
		$("#urlBox,#sortBox,#iconBox").hide();
		$("#signBox").show();
	}else{
		$("#urlBox,#sortBox,#iconBox").show();
		$("#signBox").hide();
	}
	
	//当切换类型时
	$("input[type=radio][name=type]").on("change",function(){
		if($(this).val()==2){
			$("#urlBox,#sortBox,#iconBox").hide();
			$("#signBox").show();
		}else{
			$("#urlBox,#sortBox,#iconBox").show();
			$("#signBox").hide();
		}
	});
	//当点击返回按钮时
	$("#backBtn").on("click",function(){
		history.back();
	});
	
	//当点击保存按钮时
	$("#saveBtn").on("click",function(){
		
		var index;
		var $permissionId = $("#permissionId");
		var $permissionName = $("#permissionName");
		var ajaxType = "";
		if(!$permissionId.val()||""){
			ajaxType = "POST";
		}else{
			ajaxType = "PUT";
		}
		
		//校验权限名称是否为空
		if(!$permissionName.val()||""){
			parent.layer.alert("菜单或权限名称不能为空!");
			$permissionName.focus();
			return;
		}
		
		$.ajax({
			type:ajaxType,
			url : "/sys/permission",
			data : $("#permissionForm").serialize(),
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
			error:function(XMLHttpRequest, textStatus, errorThrown){
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
				parent.layer.alert("新增或修改权限异常");
			},
			beforeSend:function(){
				index = parent.layer.load(1,{shade:0.5});
			},
			complete:function(){
				parent.layer.close(index);
			}
		});
	});
	

		// 当点击上级菜单input时
	$("#parentPermission").on("click", function() {
		
		//加载菜单树
		$.ajax({
			type:"POST",
			url:"/sys/permission/tree/"+$("input[type=radio][name=type]:checked").val(),
			dataType:"JSON",
			success:function(res){
				ztree = $.fn.zTree.init($("#menuTree"), setting, res);
				ztree.expandAll(true);
			}
		});
		
		var index = layer.open({
			type: 1,
			offset: '50px',
			title: "选择菜单",
			area: ['300px', '450px'],
			shade: 0.3,
			shadeClose: true,
			content: $("#menuLayer"),
			btn:["确定","取消"],
			btn1: function (index) {
				var node = ztree.getSelectedNodes();
				//选择上级菜单
				$("#parentPermission").val(node[0].permissionName);
				$("#parentId").val(node[0].id);
				layer.close(index);
            },
            btn2:function(index){
            	layer.close(index);
            }
		});
	})
});
