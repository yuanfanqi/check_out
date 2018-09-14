var global = {
		total:0
}

$(function(){
	
	//按键抬起触发事件
	$("#goodsId").bind("keyup",search);
	$("#goodsName").bind("keyup",search);
	//表格展示
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
			label:"商品库存",
			name:"goodsNum"
			},{
				label:"",
				name:"",
				width:50,
				formatter:function(value, options, row){
					var result = "";
					var obj = {
							goodsId: row.goodsId,
							goodsName: row.goodsName,
							goodsPrice: row.goodsPrice,
	                        num: 1
	                    }
	                    var objArr = [];
	                    objArr.push(obj);
					result = "<a href='javascript:void(0)' onclick=add("+ JSON.stringify(objArr) +")><i class='fa fa-plus-circle fa-lg' aria-hidden='true'></a>"
					return result;
				}
			}],
			autowidth: true,
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

function search(param){
	var paramName = param.target.name;
	var paramValue = param.target.value;
	var grid = $("#jqGrid");
	grid.setGridParam({
		url: "/sell/search",
		postData: {
			type: "type",
			page: "page",
			limit: "limit",
			sidx: "sidx",
			order: "order",
			paramName:paramName,
			paramValue:paramValue
		}
	}).trigger('reloadGrid');
}

function add(arr){

	var totalCash = arr["0"].goodsPrice * arr["0"].num;
	//添加行
	/*<li>商品条码（ID）</li><li>商品名称</li><li>商品单价</li><li>购买个数</li><li>合计</li>*/
    //var liHtml = '<li><i class="fa fa-minus-circle fa-2x text-muted" aria-hidden="true"></i></li>'
    var liHtml = '<li>'+ arr["0"].goodsName +'</li><li>'+ arr["0"].goodsPrice +'</li><li>'+ arr["0"].num +'</li><li>'+ totalCash +'</li><li><a href="javascript:void(0)" onclick="remove(this)"><i class="fa fa-minus-circle" aria-hidden="true"></i></a></li>';

    $('.list-show').append(liHtml);

    //累加操作
    $("#total").text(Number(global.total)+Number(totalCash));
	
}

function remove(target){
	    parent.layer.confirm("确认删除该项商品吗?", {
	        btn: ['是', '否']
	    }, function () {
	        //1.直接删除dom元素
	        $(target).parent().parent().remove();
	        //2.调用保存风险等级数据
//	        saveRiskAvoidBtnFx();

	        parent.layer.closeAll();
	    }, function () {
	        parent.layer.closeAll();
	    });
}