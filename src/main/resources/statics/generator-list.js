$(function(){
	//初始化jqGrid
	$('#jqGrid').jqGrid(config);
	
	//查询事件
//	$('#searchBtn').bind('click',triggerGrid);
});

function triggerGrid(){
	$("#jqGrid").setGridParam({
//		postData:{
//			tableName:$('#tableName').val(),
//			tableSchema:$('#tableSchema').val()
//		},
		page:1
	}).trigger("reloadGrid");
}

var config = {
		url: "/sys/generator/search",
		mtype: "POST",
		datatype: "json",
		height: 'auto',
		rowList: [10, 30, 50],
		rowNum: 10,
		scrollOffset: 0,
		colModel: [{
			label: '表名称',
			name: 'tableName',
			key: true,
			sortable: true
		}, {
			label: 'Schema',
			name: 'tableSchema',
			sortable: true
		}, {
			label: '记录数',
			name: 'tableRows',
			sortable: true
		}, {
			label: '引擎类型',
			name: 'engine',
			sortable: false
		}, {
			label: '注释',
			name: 'tableComment',
			sortable: false
		}, {
			label: '创建时间',
			name: 'createTime',
			formatter: function(value, options, row) {
				var result = "";
				result = new Date(value).pattern("yyyy/MM/dd HH:mm:ss");
				return result;
			},
			sortable: true
		}, {
			label: '操作',
			name: 'operation',
			formatter: function(value, options, row) {
					return  "<a href='/sys/generator/code/"+row.tableName+"/"+row.tableSchema+"' class='btn btn-success btn-xs' title='生成代码'>生成代码</a>";
			}
		}],
		autowidth: true,
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
}