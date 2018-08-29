$(function() {
	//当点击删除按钮时
	$("#removeBtn").on("click",function(){
		var selectedId = getSelectedRow();
		if(selectedId){
			parent.layer.confirm("确认删除吗?", {
				btn : [ '是', '否' ]
			}, function() {
			var index;
			$.ajax({
				type:"DELETE",
				url:"/sys/permission/"+selectedId,
				dataType:"JSON",
				success:function(response){
					if(response.status==false){
						parent.layer.alert(response.msg);
					}else{
						parent.layer.alert("操作成功");
						$("#jqGrid").jqGrid().trigger('reloadGrid');
					}
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
		}, function() {
			parent.layer.closeAll();
		});
		}
	});
	//当点击修改按钮时
	$("#modifyBtn").on("click",function(){
		var selectedId = getSelectedRow();
		if(selectedId){
			location.href="/sys/permission/fetch/"+selectedId;
		}
	});
	//格式化菜单名称
	function fmtPermissionNameFx(value, options, row){
		var result = "<a href=/sys/permission/fetch/"+row.id+">"+row.permissionName+"</a>";
		return result;
	}
	$("#jqGrid").jqGrid({
		url: "/sys/permission/search",
		mtype: "POST",
		datatype: "json",
		height: 'auto',
		rowNum: 10,
		rowList: [10, 20, 30],
		scrollOffset: 0,
		colModel: [{
			label: '菜单ID',
			name: 'id',
			width: 40,
			key: true,
			hidden:true,
			sortable: true
		}, {
			label: '菜单名称',
			name: 'permissionName',
			width: 60,
			sortable: true,
			formatter:fmtPermissionNameFx
		}, {
			label: '类型',
			name: 'type',
			width: 50,
			formatter: function(value, options, row) {
				if (value === 0) {
					return '<span class="label label-primary">目录</span>';
				}
				if (value === 1) {
					return '<span class="label label-success">菜单</span>';
				}
				if (value === 2) {
					return '<span class="label label-warning">权限</span>';
				}
			},
			sortable: false
		}, {
			label: '上级菜单',
			name: 'parentName',
			width: 60,
			formatter: function(value, options, row){
				var result = "";
				if(!value||""){
					result = "一级菜单";
				}else{
					result = value;
				}
				return result;
			},
			sortable: false
		}, {
			label: '菜单图标',
			name: 'icon',
			width: 50,
			formatter: function(value, options, row) {
				return value == null ? '' : '<i class="' + value + ' fa-lg"></i>';
			},
			sortable: false
		}, {
			label: '菜单URL',
			name: 'url',
			width: 100,
			sortable: false
		}, {
			label: '授权标识',
			name: 'permissionSign',
			width: 100,
			sortable: false
		}, {
			label: '排序号',
			name: 'sort',
			width: 50,
			sortable: true
		}, {
			label: '状态',
			name: 'valid',
			width: 50,
			sortable: false,
			formatter : function(value, options, row) {
				return value ? '<span class="label label-success">正常</span>' : '<span class="label label-danger">禁用</span>';
			}
		}],
		autowidth: true,
		multiselect: true,
		onSelectRow: function(rowId, status, e) {
			var rowIds = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
		},
		grouping:true,
        groupingView : {
          groupField : ['parentName']
        },
		viewrecords: true,
		pager: "#jqGridPager",
		jsonReader: {
			root: "list",
			page: "pageNum",
			total: "pages",
			records: "total"
		},
		prmNames: {
			page: "page",
			rows: "limit",
			order: "order"
		},
		gridComplete: function() {
			// 隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({
				"overflow-x": "hidden"
			});
		}
	});
});