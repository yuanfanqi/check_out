package com.checkOut.common.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.checkOut.common.model.Table;
import com.checkOut.common.model.commonModel.PageData;
import com.checkOut.common.service.SysGeneratorService;

/**
 * 代码生成控制器
 * @author Raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年9月7日 下午6:21:13
 * @version 1.0
 * @since 
 */
@Controller
@RequestMapping(value="/sys")
public class SysGenratorController {
	
	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 跳转到数据库表记录列表页
	 * @return String - 页面
	 */
	@RequestMapping(value = "/generator/show", method = RequestMethod.GET)
	@RequiresPermissions("generator:index")
	public String show(){
        logger.info("\n\n★进入跳转到数据库表记录列表页方法======================================================\n");

		return "sys/generator-list";
	}
	
	/**
	 * 条件分页查询数据库表记录列表
	 * @param table - 表实体
	 * @param page - 当前页码
	 * @param limit - 每页大小
	 * @return PageInfo<Map<String, Object>> - 执行结果报文
	 */
	@RequestMapping(value = "/generator/search", method = RequestMethod.POST)
	@ResponseBody
	public PageData<Map<String, Object>> search(@ModelAttribute(value = "table") Table table,
												@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
												@RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
        logger.info("\n\n★进入条件分页查询数据库表记录列表方法======================================================\n");

        PageData<Map<String, Object>> pageInfo = new PageData<>();
		try {
			pageInfo = sysGeneratorService.queryList(table, page, limit);
		} catch (Exception e) {
			logger.error("根据条件分页查询权限信息集合出错", e);
		}

		return pageInfo;
	}

	/**
	 * 根据表名生成代码
	 * @param tableSchema 表前缀
	 * @param tableName 表名称
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/generator/code/{tableName}")
	public void code(
			@PathVariable(value = "tableName", required = true) String tableName,
			HttpServletResponse response) throws IOException{
        logger.info("\n\n★进入根据表名生成代码方法======================================================\n");

		try {
			byte[] data = sysGeneratorService.generateCode(tableName);

			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\""+tableName+".zip\"");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream; charset=UTF-8");

			IOUtils.write(data, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
