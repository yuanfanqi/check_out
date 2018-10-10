package com.checkOut.businessFunction;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.checkOut.common.controller.ExceptionController;
import com.checkOut.common.service.businessFunction.PrepurchaseListService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 预进货列表管理控制器
 */
@Controller
@RequestMapping(value = "/prepurchase")
@Api(value = "PrepurchaseController", tags = "预进货列表管理控制器")
public class PrepurchaseController extends ExceptionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	PrepurchaseListService prepurchaseListService;

	/**
	 * 跳转到预进货列表页
	 * 
	 * @return
	 */
	@ApiOperation(value = "跳转到预进货列表页", notes = "跳转到预进货列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@RequiresPermissions("prepurchase:show")
	public String show() {
		logger.info("\n\n★进入跳转到 预进货 列表页方法======================================================\n");
		return "/businessFunction/prepurchase-list";
	}

	// 库存清单列表

	// 进货名单数量的设置页面
	@ApiOperation(value = "跳转到进货名单数量的设置页面", notes = "跳转到进货名单数量的设置页面", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("prepurchase:index")
	public ModelAndView show(
			@ApiParam(name = "goodsId", value = "商品ID", required = false) @RequestParam(value = "goodsId", required = false) String goodsId
			) {
		logger.info("\n\n★进入跳转到 进货名单数量的设置页面 方法======================================================\n");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/businessFunction/prepurchase-update");
		mv.addObject("goodsId", goodsId);
		return mv;
	}

	/**
	 * 库存名单添加操作
	 * 
	 * @param goodsId
	 * @param goodsName
	 * @param prepurchaseNum
	 * @return Map<String, Object>
	 */
	@ApiOperation(value = "库存名单添加操作", notes = "库存名单添加操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	@RequiresPermissions("prepurchase:add")
	public Map<String, Object> add(
			@ApiParam(name = "goodsId", value = "商品ID", required = true) @RequestParam(value = "goodsId", required = true) String goodsId,
			// @ApiParam(name = "goodsName", value = "商品名称", required = true)@RequestParam(value = "goodsName", required = true) String goodsName,
			@ApiParam(name = "prepurchaseNum", value = "预计补货数量", required = true) @RequestParam(value = "prepurchaseNum", required = true) Integer prepurchaseNum
	) {
		logger.info("\n\n★进入跳转到 进货名单数量的设置页面 方法======================================================\n");
		
		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "库存名单添加操作失败");
		
		
		
		return res;
	}
}
