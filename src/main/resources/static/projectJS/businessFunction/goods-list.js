$(function(){
	//删除选中功能
	$("#deleteAllBtn").on("click",function(){
		
	});
	// 商品列表
	$("#jqGrid").jqGrid({
		url: "/goods/search",
		mtype: "POST",
		datatype: "json",
		height: 'auto',
		rowNum: 10,
		rowList: [10, 20, 30],
		scrollOffset: 0,
		colModel:[{
			label:"商品条码（编号）",
			name:"goodsId",
			sortable:true
			},
			{
			label:"商品名称",
			name:"goodsName"
			},
			{
			label:"商品分类",
			name:"goodsType"
			},
			{
			label:"商品价格",
			name:"goodsPrice"
			},
			{
			label:"商品进价",
			name:"goodsBid"
			},
			{
			label:"商品库存",
			name:"goodsNum",
			sortable:true
			},
			{
			label:"修改时间",
			name:"updateTime",
			sortable:true
			},
			{
			label:"保质期",
			name:"exp"
			}],
			autowidth: true,
			multiselect: true,
			onSelectRow: function(rowId, status, e) {
				var rowIds = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
			},
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
	//多条件搜索
	$("#searchBtn").on("click", function () {
		var grid = $("#jqGrid");
		grid.setGridParam({
			postData: {
				type: "type",
				page: "page",
				limit: "limit",
				sidx: "sidx",
				order: "order",
				tblBwList: serializeObject("#goodsForm")
			}
		}).trigger('reloadGrid');
		
	});
});