package com.checkOut.businessFunction;

import java.util.HashMap;
import java.util.List;
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

import com.checkOut.common.controller.ExceptionController;
import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.pageModel.PageData;
import com.checkOut.common.service.businessFunction.TableGoodsService;
import com.checkOut.utils.H;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 商品管理控制器
 */
@Controller
@RequestMapping(value = "/goods")
@Api(value = "GoodsManagerController", tags = "商品管理控制器")
public class GoodsManagerController extends ExceptionController{

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
	@ApiOperation(value = "条件分页清单列表查询", notes = "条件分页清单列表查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
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
		
		try {
			pageInfo = tableGoodsService.selectPage(tableGoods, page, limit, sidx, order);
		} catch (Exception e) {
			logger.error("条件分页清单列表查询出错",e);
		}
		return pageInfo;
	}
	
	/**
	 * 跳转到商品信息添加或修改页面
	 * @param goodsId
	 * @param goodsName
	 * @return
	 */
	@ApiOperation(value = "跳转到商品信息添加或修改页面", notes = "跳转到商品信息添加或修改页面", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/fetch", method = RequestMethod.GET)
	@RequiresPermissions("goods:fetch")
	@ResponseBody
	public ModelAndView goodsFetch(
			@ApiParam(name = "goodsId", value = "商品条码", required = false)@RequestParam(value = "goodsId", required = false) String goodsId
//			@ApiParam(name = "goodsName", value = "商品名称", required = false)@RequestParam(name = "goodsName", value = "商品名称", required = false) String goodsName
			){
		logger.info("\n\n★进入跳转到商品信息添加或修改页面方法======================================================\n");
		
		ModelAndView mv = new ModelAndView();
		TableGoods tableGoods = new TableGoods();
		mv.setViewName("/businessFunction/goods-detail");
		if(H.isNotBlank(goodsId)){
			try {
				tableGoods = tableGoodsService.select(goodsId);
			} catch (Exception e) {
				logger.error("根据主键查询商品信息出错",e);
			}
		}
		mv.addObject("tableGoods",H.isNotBlank(tableGoods) ? tableGoods : new TableGoods());
		return mv;
	}
	
	/**
	 * 单个商品信息添加
	 * @param tableGoods
	 * @return
	 */
	@ApiOperation(value = "单个商品信息添加", notes = "单个商品信息添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresPermissions("goods:add")
	@ResponseBody
	public Map<String, Object> add(
			@ModelAttribute(value = "tableGoods") TableGoods tableGoods
			){
		logger.info("\n\n★进入单个商品信息添加方法======================================================\n");
		
		Map<String, Object> res= new HashMap<>();
		res.put("status", false);
		res.put("msg", "添加失败");
		
		try {
			tableGoodsService.add(tableGoods);
			res.put("status", true);
			res.put("msg", "添加成功");
		} catch (Exception e) {
			logger.error("单个商品信息添加出错",e);
		}
		
		return res;
	}
	
	/**
	 * 修改单个商品信息
	 * @param tableGoods
	 * @return
	 */
	@ApiOperation(value = "修改单个商品信息", notes = "修改单个商品信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@RequiresPermissions("goods:modify")
	@ResponseBody
	public Map<String, Object> modify(@ModelAttribute(value = "tableGoods") TableGoods tableGoods){
		logger.info("\n\n★进入单个商品信息修改方法======================================================\n");
		
		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "修改失败");
		try {
			tableGoodsService.modify(tableGoods);
			res.put("status", true);
			res.put("msg", "修改成功");
		} catch (Exception e) {
			logger.error("修改单个商品信息出错",e);
		}
		return res;
	}
	
	/**
	 * 验证是否是唯一存在的商品信息
	 * @param goodsId
	 * @return
	 */
	@ApiOperation(value = "验证是否是唯一存在的商品信息", notes = "验证是否是唯一存在的商品信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/isExist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isExist(
			@ApiParam(name = "goodsId", value = "商品条码", required = true)@RequestParam(value = "goodsId", required = true) String goodsId
			){
		logger.info("\n\n★进入验证是否是唯一存在的商品信息方法======================================================\n");
		
		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "该商品条码已存在");
		try {
			if(!tableGoodsService.isExist(goodsId)){
				res.put("status", true);
				res.put("msg", "该商品条码未存在，可以添加");
			}
		} catch (Exception e) {
			logger.error("信息唯一性验证出错");
		}
		return res;
	}
	
	/**
	 * 跳转到 商品库存数量 方法
	 * @param goodsId
	 * @return
	 */
	@ApiOperation(notes = "跳转到 商品库存数量 方法", value = "跳转到 商品库存数量 方法", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/inventory/index", method = RequestMethod.GET)
	@RequiresPermissions("inventory:index")
	public ModelAndView inventoryIndex(
			@ApiParam(name = "goodsId", value = "商品条码（ID）", required = true)@RequestParam(value = "goodsId", required = true) String goodsId, HttpServletRequest request
			){
		logger.info("\n\n★进入跳转到 商品库存数量 方法======================================================\n");
		
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/businessFunction/inventory-update");
		TableGoods tblGoods = new TableGoods();
		tblGoods.setGoodsId(goodsId);
		mView.addObject("tblGoods",tblGoods);
		return mView;
	}
	
	/**
	 * 更改商品库存数量
	 * @param goodsId
	 * @return
	 */
	@ApiOperation(value = "更改商品库存数量", notes = "更改商品库存数量", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/inventory/modify", method = RequestMethod.POST)
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
		
		TableGoods record = new TableGoods();
		record.setGoodsId(goodsId);
		record.setGoodsNum(goodsNum);
		try {
			tableGoodsService.modify(record);
			res.put("status", true);
			res.put("msg", "库存修改成功");
		} catch (Exception e) {
			logger.error("更改商品库存数量出错");
		}
		return res;
	}
	
	/**
	 * 商品信息删除操作
	 * @param goodsIds
	 * @return
	 */
	@ApiOperation(value = "删除商品信息", notes = "删除商品信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@RequiresPermissions("goods:delete")
	@ResponseBody
	public Map<String, Object> delete(
			@ApiParam(name = "goodsIds", value = "商品条码数组", required = true)@RequestParam(value = "goodsIds", required = true) List<String> goodsIds
			){
		logger.info("\n\n★进入删除商品信息方法======================================================\n");
			
		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "删除失败");
		
		try {
			Integer count = tableGoodsService.deleteByIds(goodsIds);
			res.put("status", true);
			res.put("msg", "成功删除"+ count+"条商品信息");
		} catch (Exception e) {
			logger.error("删除商品操作出错");
		}
		return res;
	}
}
