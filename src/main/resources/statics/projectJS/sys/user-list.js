$(function() {
	$("#jqGrid").jqGrid({
		caption:"用户列表",
		url: "/sys/user/search",
		mtype: "POST",
		datatype: "json",
		height: 'auto',
		rowNum: 10,
		rowList: [10, 30, 50],
		scrollOffset: 0,
		colModel: [{
			label: '用户ID',
			name: 'id',
			width: 40,
			key: true,
			hidden:true
		}, {
			label: '用户名',
			name: 'userName',
			width: 40,
			formatter:function(value,options,row){
				var result = "<a href=/sys/user/fetch/"+row.id+">"+value+"</a>";
				return result;
			},
			sortable: true
		}, {
			label: '昵称',
			name: 'nickName',
			width: 50,
			sortable: false
		}, {
			label: '手机号码',
			name: 'phone',
			width: 50,
			sortable: false
		}, {
			label: '邮箱',
			name: 'email',
			width: 50,
			sortable: false
		}, {
			label: '是否被锁定',
			name: 'locked',
			width: 60,
			formatter:function(value,options,row){
				var result ="";
				if(value){
					result = "<i class='fa fa-lock' aria-hidden='true'></i>";
				}else{
					result = "<i class='fa fa-unlock' aria-hidden='true'></i>";
				}
				return result;
			},
			hidden:true,
			sortable: false
		}, {
			label: '上次登录时间',
			name: 'loginTime',
			width: 100,
			formatter:function(value, options, row){
				var result ="";
				if(!isEmpty(value)){
					result = new Date(value).pattern("yyyy/MM/dd HH:mm:ss");
				}
				return result;
			},
			sortable: false
		}, {
			label: '上次登录IP',
			name: 'loginIp',
			width: 100,
			sortable: false
		}, {
			label: '状态',
			name: 'valid',
			width: 50,
			formatter : function(value, options, row) {
				return value ? '<span class="label label-success">正常</span>' : '<span class="label label-danger">禁用</span>';
			},
			sortable: true
		}, {
			label: '创建时间',
			name: 'createTime',
			width: 60,
			formatter:function(value, options, row){
				var result ="";
				if(!isEmpty(value)){
					result = new Date(value).pattern("yyyy/MM/dd HH:mm:ss");
				}
				return result;
			},
			sortable: true
		}, {
			label: '备注',
			name: 'remark',
			width: 50,
			sortable: false
		}],
		autowidth: true,
		multiselect: true,
		onSelectRow: function(rowId, status, e) {
			var rowIds = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
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
	//当点击修改按钮时
	$("#modifyBtn").on("click",function(){
		var selectedId = getSelectedRow();
		if(selectedId){
			location.href="/sys/user/fetch/"+selectedId;
		}
	});
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
					url:"/sys/user/"+selectedId,
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
						parent.layer.alert("删除用户异常");
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
});