var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "parentId",
			rootPId : -1
		},
		key : {
			name : "permissionName",
			url : "nourl"
		}
	},
	check : {
		enable : true,
		nocheckInherit : true
	}
};
var ztree;
$(function() {

	var roleId = $("#roleId").val();
	ztree = $.fn.zTree.init($("#permissionTree"), setting, allPermissions);
	ztree.expandAll(true);
	if(!isEmpty($('#roleId').val())){
		$.ajax({
			url:'/sys/role/permissions',
			type:'get',
			contentType:'application/x-www-form-urlencoded',
			data:{id:$('#roleId').val()},
			dataType:'json',
			success:function(response){
				$.each(response,function(index,value){
					var node = ztree.getNodeByParam("id", value, null);
					ztree.checkNode(node, true, false);
				});
			},
			error:function(){
				parent.layer.alert("删除权限异常");
			},
			beforeSend:function(){
				index = parent.layer.load(1,{shade:0.5});
			},
			complete:function(){
				parent.layer.close(index);
			}
		});
	}

	// 当点击返回按钮时
	$("#backBtn").on("click", function() {
		history.back();
	});
	// 当点击保存按钮时
	$("#saveBtn").on("click", function() {
		var index;
		var $roleName = $("#roleName");
		var $remark = $("#remark");
		var ajaxType = "";

		var nodes = ztree.getCheckedNodes(true);
		var permissionNodes = [];

		for (var i = 0; i < nodes.length; i++) {
			permissionNodes.push(nodes[i].id);
		}

		$("#allPermissions").val(permissionNodes);
		if (!roleId || "") {
			ajaxType = "POST";
		} else {
			ajaxType = "PUT";
		}

		// 校验角色名称是否为空
		if (!$roleName.val() || "") {
			parent.layer.alert("角色名称不能为空!");
			$roleName.focus();
			return;
		}
		$.ajax({
			type : ajaxType,
			url : "/sys/role",
			data : $("#roleDetailForm").serialize(),
			dataType : "json",
			success : function(response) {
				if (response.status == false) {
					parent.layer.alert(response.msg);
				} else {
					parent.layer.alert("操作成功", function(index) {
						history.back();
						parent.layer.closeAll();
					});
				}
			},
			error : function() {
				parent.layer.alert("新增或修改角色异常");
			},
			beforeSend : function() {
				index = parent.layer.load(1, {
					shade : 0.5
				});
			},
			complete : function() {
				parent.layer.close(index);
			}
		});
	});
});
