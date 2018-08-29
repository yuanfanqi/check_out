$(function () {
    $("#jqGrid").jqGrid({
        url: "/sys/role/search",
        mtype: "POST",
        datatype: "json",
        height : 'auto',
		rowList : [10,30,50],
		scrollOffset: 0,
        colModel: [			
			{ 
			 label: '角色ID', 
			 name: 'id',
			 width: 45, 
			 hidden: true,
			 key: true 
			},{  
			 label: '角色名称', 
			 name: 'roleName', 
			 width: 75 ,
			 sortable: false
			},{  
			 label: '状态', 
			 name: 'valid', 
			 width: 75 ,
			 formatter:function(value, options, row){
					var result ="";
					if(value){
						result = "<span class='label label-success'>正常</span>";
					}else{
						result = "<span class='label label-default'>禁用</span>";
					}
					return result;
			  },
			 sortable: false
			},{ 
			 label: '创建时间', 
			 name: 'createTime', 
			 width: 80,
			 formatter:function(value, options, row){
					var result ="";
					result = new Date(value).pattern("yyyy/MM/dd HH:mm:ss");
					return result;
				},
			sortable: true
			},{ 
			 label: '更新时间', 
			 name: 'updateTime', 
		     width: 80,
		     formatter:function(value, options, row){
					var result ="";
					result = new Date(value).pattern("yyyy/MM/dd HH:mm:ss");
					return result;
				},
			sortable: true
			},{
			label: '备注', 
			name: 'remark',
			width: 100 ,
			sortable: false
			}
		],
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
    $("#removeBtn").on("click",function(){
 		var selectedId = getSelectedRow();
 		if(selectedId){
 			parent.layer.confirm("确认删除吗?", {
				btn : [ '是', '否' ]
			}, function() {
 			var index;
 			$.ajax({
 				type:"DELETE",
 				url:"/sys/role/"+selectedId,
 				dataType:"JSON",
 				success:function(response){
 					if(response.status==false){
 						parent.layer.alert(response.msg);
 					}else{
 						if($("#roleName").val()!="超级管理员"){
 							parent.layer.alert("操作成功");
 						 	$("#jqGrid").jqGrid().trigger('reloadGrid');
 						}
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
 		},function() {
			parent.layer.closeAll();
		});
 		
 		}
 	});
    $("#modifyBtn").on("click",function(){
 		var selectedId = getSelectedRow();
 		if(selectedId){
 			location.href="/sys/role/fetch/"+selectedId;
 		}
 	});
});