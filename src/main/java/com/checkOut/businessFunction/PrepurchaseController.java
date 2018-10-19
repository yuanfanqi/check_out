package com.checkOut.businessFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.checkOut.common.model.businessFunction.PrepurchaseList;
import com.checkOut.common.model.commonModel.PageData;
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

	/**
	 * 进货名单数量的设置页面
	 * 
	 * @param goodsId
	 * @return
	 */
	@ApiOperation(value = "跳转到进货名单数量的设置页面", notes = "跳转到进货名单数量的设置页面", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresPermissions("prepurchase:index")
	public ModelAndView show(
			@ApiParam(name = "goodsId", value = "商品ID", required = false) @RequestParam(value = "goodsId", required = false) String goodsId,
			@ApiParam(name = "doNext", value = "", required = false) @RequestParam(value = "doNext", required = false) String doNext) {
		logger.info("\n\n★进入跳转到 进货名单数量的设置页面 方法======================================================\n");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/businessFunction/prepurchase-update");
		mv.addObject("goodsId", goodsId);
		mv.addObject("doNext", doNext);
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
	// @RequiresPermissions("prepurchase:add")
	@ResponseBody
	public Map<String, Object> add(
			@ApiParam(name = "goodsId", value = "商品ID", required = true) @RequestParam(value = "goodsId", required = true) String goodsId,
			// @ApiParam(name = "goodsName", value = "商品名称", required =
			// true)@RequestParam(value = "goodsName", required = true) String
			// goodsName,
			@ApiParam(name = "prepurchaseNum", value = "预计补货数量", required = true) @RequestParam(value = "prepurchaseNum", required = true) Integer prepurchaseNum) {
		logger.info("\n\n★进入跳转到 进货名单数量的设置页面 方法======================================================\n");

		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "库存名单添加操作失败");

		try {
			Integer add = prepurchaseListService.add(goodsId, prepurchaseNum);
			if (add > 0) {
				res.put("status", true);
				res.put("msg", "库存名单添加成功");
			}
		} catch (Exception e) {
			logger.info("库存名单添加操作出错" + e);
		}

		return res;
	}

	/**
	 * 条件分页 进货清单列表
	 * 
	 * @param prepurchaseList
	 * @param page
	 * @param limit
	 * @param sidx
	 * @param order
	 * @return
	 */
	@ApiOperation(value = "条件分页 进货清单列表", notes = "条件分页 进货清单列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@RequiresPermissions("prepurchase:search")
	@ResponseBody
	public PageData<PrepurchaseList> search(@ModelAttribute(value = "PrepurchaseList") PrepurchaseList prepurchaseList,
			@ApiParam(name = "page", value = "当前页码", required = true, defaultValue = "1") @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页大小", required = true, defaultValue = "10") @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
			@ApiParam(name = "sidx", value = "排序字段", required = false) @RequestParam(value = "sidx", required = false) String sidx,
			@ApiParam(name = "order", value = "排序规则", required = false) @RequestParam(value = "order", required = false) String order) {
		logger.info("\n\n★进入跳转到 库存清单列表 方法======================================================\n");

		PageData<PrepurchaseList> pageInfo = new PageData<>();
		try {
			pageInfo = prepurchaseListService.selectPage(prepurchaseList, page, limit, sidx, order);
		} catch (Exception e) {
			logger.info("条件分页出错" + e);
		}

		return pageInfo;
	}

	/**
	 * 进货清单列表 商品删除
	 * 
	 * @param goodsId
	 * @return
	 */
	@ApiOperation(value = "进货清单列表 商品删除", notes = "进货清单列表 商品删除", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@RequiresPermissions("prepurchase:delete")
	@ResponseBody
	public Map<String, Object> delete(
			@ApiParam(name = "goodsId", value = "商品ID", required = true) @RequestParam(value = "goodsId", required = true) String goodsId) {
		logger.info("\n\n★进入跳转到 库存清单列表商品删除 方法======================================================\n");

		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "进货名单删除操作失败");

		try {
			Integer count = prepurchaseListService.delete(goodsId);
			res.put("status", true);
			res.put("msg", "成功删除" + count + "条商品信息");
		} catch (Exception e) {
			logger.info("进货名单删除操作出错" + e);
		}

		return res;
	}

	/**
	 * 进货清单列表 商品修改
	 * 
	 * @param prepurchaseList
	 * @return
	 */
	@ApiOperation(value = "进货清单列表 商品修改", notes = "进货清单列表 商品修改", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@RequiresPermissions("prepurchase:modify")
	@ResponseBody
	public Map<String, Object> modify(@ModelAttribute(value = "PrepurchaseList") PrepurchaseList prepurchaseList) {
		logger.info("\n\n★进入跳转到 库存清单列表商品修改 方法======================================================\n");

		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "进货名单修改操作失败");

		try {
			Integer modify = prepurchaseListService.modify(prepurchaseList);
			if (modify > 0) {
				res.put("status", true);
				res.put("msg", "成功修改商品信息");
			}
		} catch (Exception e) {
			logger.info("进货名单修改操作出错" + e);
		}

		return res;
	}

	/**
	 * 通过名单期数代码进行导出
	 * 
	 * @param themeCode
	 * @return
	 */
	@ApiOperation(value = "通过名单期数代码进行导出", notes = "通过名单期数代码进行导出", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/doExport", method = RequestMethod.POST)
	@ResponseBody
	public List<PrepurchaseList> doExport(
			@ApiParam(name = "themeCode", value = "名单期数代码", required = false) @RequestParam(value = "themeCode", required = false) String themeCode
			) {
		logger.info("\n\n★进入跳转到 通过名单期数代码进行导出 方法======================================================\n");

		List<PrepurchaseList> data = new ArrayList<>();
		try {
			data = prepurchaseListService.doExport(themeCode);
		} catch (Exception e) {
			logger.info("进货名单查询导出操作出错" + e);
		}
		return data;
	}

	// 查询往期名单
	@ApiOperation(value = "查询往期名单", notes = "查询往期名单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/searchForOld", method = RequestMethod.POST)
	@ResponseBody
	public List<String> searchForOld(

	) {
		logger.info("\n\n★进入跳转到 查询往期名单 方法======================================================\n");

		List<String> res = new ArrayList<>();
		try {
			res = prepurchaseListService.selectOldPrepurchaseList();
		} catch (Exception e) {
			logger.info("查询往期名单出错" + e);
		}
		return res;
	}
}
