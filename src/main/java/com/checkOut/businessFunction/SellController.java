package com.checkOut.businessFunction;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.commonModel.PageData;
import com.checkOut.common.service.businessFunction.TableGoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 销售功能相关 管理控制器
 */
@Controller
@RequestMapping(value = "/sell")
@Api(value = "SellController", tags = "销售功能控制器")
public class SellController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TableGoodsService tableGoodsService;

	@ApiOperation(value = "跳转到销售列表页", notes = "跳转到销售列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@RequiresPermissions("sell:index")
	public String show() {
		logger.info("\n\n★进入跳转到 销售 列表页方法======================================================\n");
		return "/businessFunction/sell-index";
	}
	
	/**
	 * 根据参数模糊查询商品记录(分页)
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	@ApiOperation(value = "根据参数模糊查询商品记录(分页)", notes = "根据参数模糊查询商品记录(分页)", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@RequiresPermissions("sell:search")
	@ResponseBody
	public PageData<TableGoods> search(
			@ApiParam(name = "paramName", value = "参数名", required = false)@RequestParam(value = "paramName", required = false) String paramName,
			@ApiParam(name = "paramValue", value = "参数值", required = false)@RequestParam(value = "paramValue", required = false) String paramValue,
			@ApiParam(name = "page", value = "当前页码", required = true, defaultValue = "1") @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页大小", required = true, defaultValue = "10") @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
			@ApiParam(name = "sidx", value = "排序字段", required = false) @RequestParam(value = "sidx", required = false) String sidx,
			@ApiParam(name = "order", value = "排序规则", required = false) @RequestParam(value = "order", required = false) String order) {
		logger.info("\n\n★进入跳转到 根据参数模糊查询商品记录(分页) 列表页方法======================================================\n");
		
		PageData<TableGoods> pageInfo = new PageData<>();
		try {
			pageInfo = tableGoodsService.likeSelect(paramName, paramValue);
		} catch (Exception e) {
			logger.error("根据参数模糊查询商品记录(分页)出错");
		}
		return pageInfo;
	}
}
