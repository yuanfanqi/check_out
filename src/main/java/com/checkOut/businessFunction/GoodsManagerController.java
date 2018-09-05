package com.checkOut.businessFunction;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.pageModel.PageData;
import com.checkOut.common.service.businessFunction.TableGoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 商品管理控制器
 */
@Controller
@RequestMapping(value = "/goods")
@Api(value = "GoodsManagerController", tags = "商品管理控制器")
public class GoodsManagerController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TableGoodsService tableGoodsService;

	@ApiOperation(value = "跳转到商品清单列表页", notes = "跳转到商品清单列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@RequiresPermissions("goods:show")
	public String show() {
		logger.info("\n\n★进入跳转到 商品清单 列表页方法======================================================\n");
		return "/businessFunction/goods-list";
	}
	
	/**
	 * 条件分页清单列表查询
	 * @param tableGoods
	 * @param page
	 * @param limit
	 * @param sidx
	 * @param order
	 * @return
	 */
	@ApiOperation(value = "条件分页清单列表查询", notes = "", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public PageData<TableGoods> search(
			@ModelAttribute(value = "tableGoods") TableGoods tableGoods,
			@ApiParam(name = "page", value = "当前页码", required = true, defaultValue = "1") @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页大小", required = true, defaultValue = "10") @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
			@ApiParam(name = "sidx", value = "排序字段", required = false) @RequestParam(value = "sidx", required = false) String sidx,
			@ApiParam(name = "order", value = "排序规则", required = false) @RequestParam(value = "order", required = false) String order) {
		logger.info("\n\n★进入条件分页清单列表查询方法======================================================\n");

		PageData<TableGoods> pageInfo = new PageData<>();
		pageInfo = tableGoodsService.selectPage(tableGoods, page, limit, sidx, order);
		return pageInfo;
	}
}
