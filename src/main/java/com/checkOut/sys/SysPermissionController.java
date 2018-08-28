package com.checkOut.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.checkOut.common.model.sys.SysPermission;
import com.checkOut.common.service.sys.SysPermissionService;
import com.checkOut.common.shiro.ShiroUtil;
import com.checkOut.utils.H;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统菜单及权限控制器
 * @author raymee E-mail:leohaoo
 * @date 创建时间：2017年5月25日 上午10:24:13
 * @version 1.0
 * @since 
 */
@Controller
@RequestMapping(value="/sys")
@Api(value = "SysPermissionController", tags="系统菜单及权限控制器")
public class SysPermissionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 跳转到系统菜单及权限列表页
	 * @return String - 页面
	 */
	@ApiOperation(value = "跳转到系统菜单及权限列表页", notes = "跳转到系统菜单及权限列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/permission/show", method = RequestMethod.GET)
	@RequiresPermissions("menu:index")
	public String show(){
        logger.info("\n\n★进入跳转到系统菜单及权限列表页方法======================================================\n");
        return "sys/permission-list";
	}
	
	/**
	 * 跳转到单个系统菜单及权限页
	 * @param id - 菜单或权限ID
	 * @return String - 页面
	 */
	@ApiOperation(value = "跳转到单个系统菜单及权限页", notes = "跳转到单个系统菜单及权限页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/permission/fetch/{id}", method = RequestMethod.GET)
	public ModelAndView fetch(@ApiParam(name = "id", value = "菜单或权限ID", required = false) @PathVariable(value = "id", required = false) Integer id) {
        logger.info("\n\n★进入跳转到单个系统菜单及权限页方法======================================================\n");

		ModelAndView mv = new ModelAndView();
		SysPermission sysPermission = new SysPermission();
		mv.setViewName("sys/permission-detail");
		mv.addObject("sysPermission", sysPermission);
		
		if (H.isNotBlank(id) && id != 0) {
			
			//=====================================校验是否有查看权限=====================================//
			boolean isPermitted = SecurityUtils.getSecurityManager().isPermitted(ShiroUtil.getSubject().getPrincipals(), "menu:fetch");
			logger.info("是否有查看修改权限isPermitted="+isPermitted);
			if(!isPermitted){
				throw new UnauthorizedException("权限不足");
			}
			//=====================================校验是否有查看权限=====================================//
			
			try {
				sysPermission = sysPermissionService.findByPrimaryKey(id);
				mv.addObject("sysPermission", sysPermission);
			} catch (Exception e) {
				logger.error("根据主键查询单个菜单权限实体出错", e);
			}
		}
		return mv;
	}
	
	/**
	 * 添加单个系统菜单或权限
	 * @param sysPermission - 系统菜单权限实体
	 * @return Map<String, Object> - 执行结果报文
	 */
	@ApiOperation(value = "添加单个系统菜单或权限", notes = "添加单个系统菜单或权限", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/permission", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:add")
	public Map<String, Object> add(@ModelAttribute(value = "sysPermission") @Valid SysPermission sysPermission, BindingResult bindingResult) {
        logger.info("\n\n★进入添加单个系统菜单或权限方法======================================================\n");

        Map<String, Object> res = new HashMap<>(16);
        res.put("status", false);
		res.put("msg", " 操作失败");

		String msg = H.checkValidation(bindingResult, sysPermission);
		if (H.isNotBlank(msg)) {
			res.put("msg", msg);
			return res;
		}
		
		try {
			Integer count = sysPermissionService.add(sysPermission);
			if (H.isNotBlank(count)) {
				res.put("status", true);
				res.put("msg", "添加单个系统菜单或权限成功");
			}
		} catch (Exception e) {
			logger.error("添加单个系统菜单或权限出错", e);
		}
		return res;
	}
	
	/**
	 * 删除单个系统菜单或权限
	 * @param id - 菜单或权限ID
	 * @return Map<String, Object> - 执行结果报文
	 */
	@ApiOperation(value = "删除单个系统菜单权限", notes = "删除单个系统菜单权限", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/permission/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("menu:remove")
	public Map<String, Object> remove(
			@ApiParam(name = "id", value = "菜单或权限ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.info("\n\n★进入删除单个系统菜单权限方法======================================================\n");

        Map<String, Object> res = new HashMap<>(16);
        res.put("status", false);
		res.put("msg", "删除单个系统菜单权限失败");
		
		try {
			Integer count = sysPermissionService.remove(id);
			if(H.isNotBlank(count)){
				res.put("status", true);
				res.put("msg", "删除单个系统菜单权限成功");
				res.put("data", count);
			}
		} catch (Exception e) {
			logger.error("删除单个系统菜单或权限出错", e);
		}
		return res;
	}
	
	/**
	 * 修改单个系统菜单或权限
	 * @param sysPermission - 系统菜单权限实体
	 * @return Map<String, Object> - 执行结果报文
	 */
	@ApiOperation(value = "修改单个系统菜单权限", notes = "修改单个系统菜单权限", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/permission", method = RequestMethod.PUT)
	@ResponseBody
	@RequiresPermissions("menu:modify")
	public Map<String, Object> modify(@ModelAttribute(value = "sysPermission") @Valid SysPermission sysPermission, BindingResult bindingResult) {
        logger.info("\n\n★进入修改单个系统菜单权限方法======================================================\n");

        Map<String, Object> res = new HashMap<>(16);
        res.put("status", false);
		res.put("msg", "操作失败");

		String msg = H.checkValidation(bindingResult, sysPermission);
		if (H.isNotBlank(msg)) {
			res.put("msg", msg);
			return res;
		}

		try {
			Integer count = sysPermissionService.modify(sysPermission);
			if (H.isNotBlank(count)) {
				res.put("status", true);
				res.put("msg", "修改单个系统菜单或权限成功");
				res.put("data", count);
			}
		} catch (Exception e) {
			logger.error("修改单个系统菜单或权限出错", e);
		}
		return res;
	}

	/**
	 * 条件分页查询系统菜单权限列表
	 * @param sysPermission 页面传入的系统菜单权限实体条件
	 * @param page 当前页码
	 * @param limit 每页大小
	 * @param sidx 排序字段
	 * @param order 排序规则
	 * @return PageInfo<SysPermission>
	 */
	@ApiOperation(value = "条件分页查询系统菜单权限列表", notes = "条件查询系统菜单权限列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/permission/search", method = RequestMethod.POST)
	@ResponseBody
	public List<SysPermission> search(@ModelAttribute(value = "sysPermission") SysPermission sysPermission,
			@ApiParam(name = "page", value = "当前页码", required = true, defaultValue = "1") @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页大小", required = true, defaultValue = "10") @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
			@ApiParam(name = "sidx", value = "排序字段", required = false) @RequestParam(value = "sidx", required = false) String sidx,
			@ApiParam(name = "order", value = "排序规则", required = false) @RequestParam(value = "order", required = false) String order) {
        logger.info("\n\n★进入条件分页查询系统菜单权限列表方法======================================================\n");

		List<SysPermission> pageInfo = null;
		
		try {
			pageInfo = sysPermissionService.findPage(sysPermission, page, limit);
		} catch (Exception e) {
			logger.error("根据条件分页查询权限信息集合出错", e);
		}
		
		return pageInfo;
	}
	
	/**
	 * 根据类型查找父节点
	 * @return List<SysPermission> - 系统菜单权限集合
	 */
//	@ApiOperation(value = "根据类型查找父节点", notes = "根据类型查找父节点", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value="/permission/tree/{type}",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<SysPermission> tree(@ApiParam(name = "type", value = "类型", required = false) @PathVariable(value = "type", required = true) Short type){
        logger.info("\n\n★进入根据类型查找父节点方法======================================================\n");

		List<SysPermission> datas = new ArrayList<>();
		
		try {
			datas = sysPermissionService.findParentNode(type);
		} catch (Exception e) {
			logger.error("根据类型查找父节点出错", e);
		}
		
		return datas;
	}
}
