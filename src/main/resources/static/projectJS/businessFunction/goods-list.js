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
			name:"goodsName",
			formatter:function(value, options, row){
				return "<a href=/goods/fetch?goodsId="+row.goodsId+">"+ value +"</a>";
			}
			},
			{
			label:"商品分类",
			name:"goodsType",
			formatter:function(value, options, row){
				var result = "";
				switch(value)
				{
				case 1:
					result = "洗浴用品";
				  break;
				case 2:
					result = "护肤品";
				  break;
				case 3:
					result = "服饰包包";
				  break;
				}
				return result;
			}
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
			formatter:function(value, options, row){
				//库存可以点，点击之后跳转库存管理页面，可以修改库存详细？？
				return value;
			},
			sortable:true
			},
			{
			label:"修改时间",
			name:"updateTime",
			formatter:function(value, options, row){
				var result = "";
                if (!isEmpty(value)) {
                    result = new Date(value).pattern("yyyy-MM-dd HH:mm");
                }
                return result;
			},
			sortable:true
			},
			{
			label:"保质期",
			name:"exp",
			formatter:function(value, options, row){
				var result = "";
                if (!isEmpty(value)) {
                    result = new Date(value).pattern("yyyy-MM-dd");
                    //保质期可以点，点击之后跳转保质期管理页面，可以查看保质期详情？？
                }
                return result;
			}
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
	//多条件搜索
	$("#searchBtn").on("click", function () {
		var grid = $("#jqGrid");
		console.log(serializeObject("#goodsForm"))
		grid.setGridParam({
			postData: {
				type: "type",
				page: "page",
				limit: "limit",
				sidx: "sidx",
				order: "order",
				goodsNum: $("#goodsNum").val(),
				tableGoods: serializeObject("#goodsForm")
			}
		}).trigger('reloadGrid');
		
	});
});