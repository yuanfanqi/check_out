package com.checkOut.businessFunction;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.checkOut.common.model.businessFunction.GoodsStore;
import com.checkOut.common.model.commonModel.PageData;
import com.checkOut.common.service.businessFunction.GoodsStoreService;

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
	@Autowired
	private GoodsStoreService goodsStoreService;

	/*@ApiOperation(value = "跳转到商品库存清单列表页", notes = "跳转到商品库存清单列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@RequiresPermissions("inventory:index")
	public String show(
			@ApiParam(name = "goodsId", value = "商品ID", required = false)@RequestParam(value = "goodsId", required = false)String goodsId
			) {
		logger.info("\n\n★进入跳转到 商品清单库存 列表页方法======================================================\n");
		if (null == goodsId) {
			return "/businessFunction/inventory-list";
		}else{
			return "/businessFunction/inventory-list?goodsId=" + goodsId;
		}
	}*/
	
	@ApiOperation(value = "跳转到商品库存清单列表页", notes = "跳转到商品库存清单列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@RequiresPermissions("inventory:index")
	public ModelAndView show(
			@ApiParam(name = "goodsId", value = "商品ID", required = false)@RequestParam(value = "goodsId", required = false)String goodsId
			){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/businessFunction/inventory-list");
		mv.addObject("goodsId", goodsId);
		return mv;
	}
	
	//清单分页
	@ApiOperation(value = "条件分页查询库存清单列表", notes = "条件分页查询库存 清单列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@RequiresPermissions("inventory:search")
	@ResponseBody
	public PageData<GoodsStore> search(
			@ModelAttribute(value = "goodsStore") GoodsStore goodsStore,
			@ApiParam(name = "page", value = "当前页码", required = true, defaultValue = "1") @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页大小", required = true, defaultValue = "10") @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
			@ApiParam(name = "sidx", value = "排序字段", required = false) @RequestParam(value = "sidx", required = false) String sidx,
			@ApiParam(name = "order", value = "排序规则", required = false) @RequestParam(value = "order", required = false) String order) {
		logger.info("\n\n★进入跳转到 条件分页查询库存 清单列表页方法======================================================\n");
		
		PageData<GoodsStore> pageInfo = new PageData<>();
		try {
			pageInfo = goodsStoreService.selectPage(goodsStore, page, limit, sidx, order);
		} catch (Exception e) {
			logger.error("条件分页查询库存清单列表查询出错",e);
		}
		
		return pageInfo;
	}
	
	/**
	 * 跳转到 更改商品库存数量 方法
	 * @param goodsId
	 * @return
	 */
	@ApiOperation(notes = "跳转到 更改商品库存数量 方法", value = "跳转到 更改商品库存数量 方法", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("inventory:index")
	public ModelAndView inventoryIndex(
			@ApiParam(name = "goodsId", value = "商品条码（ID）", required = true)@RequestParam(value = "goodsId", required = true) String goodsId, HttpServletRequest request
			){
		logger.info("\n\n★进入跳转到 更改商品库存数量 方法======================================================\n");
		
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/businessFunction/inventory-update");
		mView.addObject("goodsId",goodsId);
		return mView;
	}
	
	/**
	 * 更改商品库存数量
	 * @param goodsId
	 * @return
	 */
	@ApiOperation(value = "更改商品库存数量", notes = "更改商品库存数量", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@RequiresPermissions("inventory:modify")
	@ResponseBody
	public Map<String, Object> updataInventory(
			@ApiParam(name = "goodsId", value = "商品条码", required = true)@RequestParam(value = "goodsId", required = true) String goodsId,
			@ApiParam(name = "goodsNum", value = "商品库存", required = true)@RequestParam(value = "goodsNum", required = true) Integer goodsNum
			){
		logger.info("\n\n★进入更改商品库存数量方法======================================================\n");
		
		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "库存修改失败");
		
		GoodsStore record = new GoodsStore();
		record.setGoodsId(goodsId);
		record.setGoodsNum(goodsNum);
		try {
			goodsStoreService.modify(record);
			res.put("status", true);
			res.put("msg", "库存修改成功");
		} catch (Exception e) {
			logger.error("更改商品库存数量出错" + e);
		}
		return res;
	}
}
