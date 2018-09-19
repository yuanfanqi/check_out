var global = {
		total:0,
		data:[],
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
	
	$("#save").on("click",doDBOperator);
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
	//prompt层
	parent.layer.prompt({title: '输入商品数量，并确认', formType: 0}, function(text, index){
		parent.layer.close(index);
		doAdd(arr,text);
	});
}

function doAdd(arr,text){
	//遍历数组global.data，如果存在相同的id则，不添加行
	var mark = false;
	for(var i = 0 ; i < global.data.length ; i++){
		if(global.data[i].goodsId == arr["0"].goodsId){
			mark = true;
		}
	}
	if(mark){
		parent.layer.alert('该商品已进入清单!');
		return;
	}
	var num = text;
	var totalCash = arr["0"].goodsPrice * num;
	//添加行
    var liHtml = '<pre id='+ arr["0"].goodsId +'><li>'+ arr["0"].goodsName +'</li><li>'+ arr["0"].goodsPrice +'</li><li>'+ num +'</li><li>'+ totalCash +'</li><li><a href="javascript:void(0)" onclick="remove(this)"><i class="fa fa-minus-circle" aria-hidden="true"></i></a></li></pre>';

    $('.list-show').append(liHtml);

    //将该数据存入data属性中
    var obj = {
    		goodsId:arr["0"].goodsId,
    		goodsPrice:arr["0"].goodsPrice,
    		num:num
    }
    global.data.push(obj);
    //累加操作
    global.total = Number(global.total)+Number(totalCash);
    $("#total").text(global.total);
}

function remove(target){
	    parent.layer.confirm("确认删除该项商品吗?", {
	        btn: ['是', '否']
	    }, function () {
	    	var id = $(target).parent().parent().attr("id");
	    	var totalCash = 0;
	    	//删除dom元素之前得先进行总金额的调整
	    	for(var i = 0 ; i < global.data.length ; i++){
	    		if( id == global.data[i].goodsId ){
	    			totalCash = global.data[i].num * global.data[i].goodsPrice;
	    	        //删除global.data的相关数据
	    			global.data.splice(i,1);
	    		}
	    	}
	    	global.total = Number(global.total) - Number(totalCash);
	        $("#total").text(global.total);
	        //直接删除dom元素
	        $(target).parent().parent().remove();

	        parent.layer.closeAll();
	    }, function () {
	        parent.layer.closeAll();
	    });
}

//点击销售按键后需要进行数据库的记录更新 相关的库有销售历史表、和商品库存表
function doDBOperator(){
	if(global.data.length > 0){
		//将数据传入后台
		
	}else{
		parent.layer.alert('当前没有选中的商品!');
	}
}

