$(function(){
	// 商品库存列表
	$("#jqGrid").jqGrid({
		url: "/inventory/search",
		mtype: "POST",
		datatype: "json",
		height: 'auto',
		rowNum: 10,
		rowList: [10, 20, 30],
		scrollOffset: 0,
		postData:{goodsId:$("#goodsId").val()},
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
			label:"商品库存",
			name:"goodsNum",
			sortable:true
			},
			{
			label:"修改时间",
			name:"insertDate",
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
                }
                return result;
			}
			},
			{
				label:"操作",
				name:"",
				formatter:function(value, options, row){
					var result = "";
	                //补货btn + 其他功能按钮
					result += "<a href='javascript:void(0);' onclick='doAddOpr(\""+ row.goodsId +"\")'>加入进货名单</a>"
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
});

//进货名单 添加操作
function doAddOpr(goodsId){
	console.log(goodsId)
	parent.layer.open({
		  type: 2,
		  title: "商品进货名单补充",
		  shadeClose: true,
		  shade: 0.8,
		  area: ['30%', '35%'],
		  content: '/prepurchase/index?goodsId=' + goodsId + '&doNext=add',
		});
}