$(function(){
	// 商品进货名单
	$("#jqGrid").jqGrid({
		url: "/prepurchase/search",
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
			label:"预进货数量",
			name:"prepurchaseNum",
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
				label:"操作",
				name:"operation",
				formatter:function(value, options, row){
	                //从名单中移除 + 修改进货数量
					var delBtn = "<a href='javascript:void(0);' onclick='delBtn(\""+ row.goodsId +"\")'>从名单中移除</a>"
	                var updateBtn = "<a href='javascript:void(0);' onclick='updateBtn(\""+ row.goodsId +"\")'>修改进货件数</a>"
					return  delBtn+ '&emsp;&emsp;' +updateBtn;
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

//从名单中移除
function delBtn(goodsId){
	$.ajax({
		type:"POST",
		url:"/prepurchase/delete",
		dataType:"JSON",
		data:{"goodsId":goodsId},
		success:function(result){
				parent.layer.alert(result.msg);
				$("#jqGrid").jqGrid().trigger('reloadGrid');
		},
		error:function(){
			parent.layer.alert("删除数据出现异常");
		},
		beforeSend:function(){
			index = parent.layer.load(1,{shade:0.5});
		},
		complete:function(){
			parent.layer.close(index);
		}
	});
}
//修改进货数量
function updateBtn(goodsId){
	parent.layer.open({
		  type: 2,
		  title: "商品进货名单补充",
		  shadeClose: true,
		  shade: 0.8,
		  area: ['30%', '35%'],
		  content: '/prepurchase/index?goodsId=' + goodsId + '&doNext=modify',
		  end: function () {
		        location.reload();
		      }
		});
//	$.ajax({
//		type:"POST",
//		url:"/prepurchase/modify",
//		dataType:"JSON",
//		data:{"goodsIds":dataArr},
//		success:function(result){
//				parent.layer.alert(result.msg);
//				$("#jqGrid").jqGrid().trigger('reloadGrid');
//		},
//		error:function(){
//			parent.layer.alert("修改数据出现异常");
//		},
//		beforeSend:function(){
//			index = parent.layer.load(1,{shade:0.5});
//		},
//		complete:function(){
//			parent.layer.close(index);
//		}
//	});
}
