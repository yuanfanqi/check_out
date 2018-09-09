package com.checkOut.businessFunction;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.pageModel.PageData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 库存管理控制器
 */
@Controller
@RequestMapping(value = "/inventory")
@Api(value = "InventoryController", tags = "库存管理控制器")
public class InventoryController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation(value = "跳转到商品库存清单列表页", notes = "跳转到商品库存清单列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@RequiresPermissions("inventory:show")
	public String show() {
		logger.info("\n\n★进入跳转到 商品清单库存 列表页方法======================================================\n");
		return "inventory-list";
	}
	/**
	 * 该控制类好像没有存在的必要，删除位 预定
	 * 
	 */
	
	//清单分页
	public PageData<TableGoods> search(
			@ModelAttribute(value = "tableGoods") TableGoods tableGoods,
			@ApiParam(name = "page", value = "当前页码", required = true, defaultValue = "1") @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页大小", required = true, defaultValue = "10") @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
			@ApiParam(name = "sidx", value = "排序字段", required = false) @RequestParam(value = "sidx", required = false) String sidx,
			@ApiParam(name = "order", value = "排序规则", required = false) @RequestParam(value = "order", required = false) String order) {
		logger.info("\n\n★进入跳转到 条件分页查询库存 清单列表页方法======================================================\n");
		
		PageData<TableGoods> pageInfo = new PageData<>();
		
		return pageInfo;
	}
}
