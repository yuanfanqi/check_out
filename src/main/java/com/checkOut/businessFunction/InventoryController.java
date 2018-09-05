package com.checkOut.businessFunction;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	@RequiresPermissions("")
	public String show() {
		logger.info("\n\n★进入跳转到 商品清单库存 列表页方法======================================================\n");
		return "";
	}
	
}
