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
import com.checkOut.common.model.sys.SysRole;
import com.checkOut.common.service.sys.SysPermissionService;
import com.checkOut.common.service.sys.SysRoleService;
import com.checkOut.common.shiro.ShiroUtil;
import com.checkOut.utils.H;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 角色管理
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@Controller
@RequestMapping(value = "/sys")
//@Api(value = "SysRoleController", tags = "角色管理控制器")
public class SysRoleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 跳转到角色列表页
	 * @return String - 页面
	 */
//	@ApiOperation(value = "跳转到角色列表页", notes = "跳转到角色列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/role/show", method = RequestMethod.GET)
	@RequiresPermissions("role:index")
	public String show() {
        logger.info("\n\n★进入跳转到角色列表页方法======================================================\n");
        return "sys/role-list";
	}

	/**
	 * 跳转到添加或修改角色页面
	 * @param id - 菜单或权限ID
	 * @return String - 页面
	 */
//	@ApiOperation(value = "跳转到添加或修改角色页面", notes = "跳转到添加或修改角色页面", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
	@RequestMapping(value = "/role/fetch/{id}", method = RequestMethod.GET)
	public ModelAndView fetch(@ApiParam(name = "id", value = "菜单或权限ID", required = false) @PathVariable(value = "id", required = false) Integer id) {
        logger.info("\n\n★进入跳转到添加或修改角色页面方法======================================================\n");

		ModelAndView mv = new ModelAndView();
		mv.setViewName("sys/role-detail");
		SysRole sysRole = new SysRole();
		mv.addObject("sysRole", sysRole);
		
		SysPermission record = new SysPermission();
		record.setValid(true);
		List<SysPermission> allPermissions = new ArrayList<>();
		try {
			allPermissions = sysPermissionService.select(record);
		} catch (Exception e) {
			logger.error("查询系统权限列表出错", e);
		}
		SysPermission root = new SysPermission();
		root.setId(0);
		root.setPermissionName("一级菜单");
		root.setParentId(-1);
		allPermissions.add(root);
		mv.addObject("allPermissions", allPermissions);

		if (H.isNotBlank(id) && id != 0) {

			// =====================================校验是否有查看权限=====================================//
			boolean isPermitted = SecurityUtils.getSecurityManager().isPermitted(ShiroUtil.getSubject().getPrincipals(), "role:fetch");
			logger.info("是否有查看修改权限isPermitted=" + isPermitted);
			if (!isPermitted) {
				throw new UnauthorizedException("权限不足");
			}
			// =====================================校验是否有查看权限=====================================//

			try {
				sysRole = sysRoleService.selectByPrimaryKey(id);
				mv.addObject("sysRole", sysRole);
			} catch (Exception e) {
				logger.error("根据主键查询单个角色出错", e);
			}
		}
		return mv;
	}

	/**
	 * 条件分页查询系统角色列表
	 * @param sysRole  - 系统角色实体
	 * @param page - 当前页码
	 * @param limit - 每页大小
	 * @param sidx - 排序字段
	 * @param order  - 排序规则
	 * @return PageInfo<SysPermission> - 分页信息
	 */
	@ApiOperation(value = "条件分页查询系统角色列表", notes = "条件分页查询系统角色列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/role/search", method = RequestMethod.POST)
	@ResponseBody
	public List<SysRole> search(@ModelAttribute(value = "sysRole") SysRole sysRole,
			@ApiParam(name = "page", value = "当前页码", required = true, defaultValue = "1") @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "limit", value = "每页大小", required = true, defaultValue = "10") @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
			@ApiParam(name = "sidx", value = "排序字段", required = false) @RequestParam(value = "sidx", required = false) String sidx,
			@ApiParam(name = "order", value = "排序规则", required = false) @RequestParam(value = "order", required = false) String order) {
        logger.info("\n\n★进入条件分页查询系统角色列表方法======================================================\n");

        List<SysRole> findPage = null;
		try {
			findPage = sysRoleService.findPage(sysRole, page, limit);
		} catch (Exception e) {
			logger.error("根据条件分页查询角色信息集合出错", e);
		}
		return findPage;
	}

	/**
	 * 根据角色id查询角色信息
	 * @param id  - 角色ID
	 * @return Map<String, Object> - 执行结果报文
	 */
	@ApiOperation(value = "根据角色id查询角色信息", notes = "根据角色id查询角色信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchRoleById(@ApiParam(name = "id", value = "角色ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.info("\n\n★进入根据角色id查询角色信息方法======================================================\n");

		Map<String, Object> res = new HashMap<>();
		System.out.println(id);
		return res;
	}

	/**
	 * 添加角色
	 * @param sysRole
	 * @param permissionIds  - 角色对应多个权限id字符串
	 * @return Map<String, Object> - 执行结果报文
	 */
	@ApiOperation(value = "添加角色", notes = "添加角色", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("role:add")
	public Map<String, Object> add(@ModelAttribute(value = "sysRole") @Valid SysRole sysRole, BindingResult bindingResult, String permissionIds) {
        logger.info("\n\n★进入添加角色方法======================================================\n");

		if (sysRole.getValid() == null) {
			sysRole.setValid(true);
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("status", false);
		res.put("msg", "操作失败");

		String msg = H.checkValidation(bindingResult);
		if (H.isNotBlank(msg)) {
			res.put("msg", msg);
			return res;
		}

		Integer count = 0;
		Integer[] permissionKeys = null;
		if (H.isNotBlank(permissionIds)) {
			String fistPermissionId = permissionIds.substring(0, 1);
			if (fistPermissionId.equals("0")) {
				permissionIds = permissionIds.substring(2, permissionIds.length());
			}
			String[] ids = permissionIds.split(",");
			permissionKeys = new Integer[ids.length];
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].equals("null")) {
					permissionKeys[i] = Integer.valueOf(ids[i]);
				}
			}
		}
		try {
			count = sysRoleService.add(sysRole, permissionKeys);
			if (H.isNotBlank(count)) {
				res.put("status", true);
				res.put("msg", "角色添加成功");
				res.put("data", count);
			}
		} catch (Exception e) {
			if (e.getClass().getSimpleName().equalsIgnoreCase("DuplicateKeyException")) {
				res.put("status", false);
				res.put("msg", "角色名已被使用");
			}
			logger.error("角色添加出错", e);
		}

		return res;
	}

	/**
	 * 修改角色
	 * @param sysRole
	 * @param permissionIds  - 角色对应多个权限id字符串
	 * @return Map<String, Object> - 执行结果报文
	 */
	@ApiOperation(value = "修改角色", notes = "修改角色", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/role", method = RequestMethod.PUT)
	@ResponseBody
	@RequiresPermissions("role:modify")
	public Map<String, Object> modify(@ModelAttribute(name = "sysUser") @Valid SysRole sysRole, BindingResult bindingResult, String permissionIds) {
        logger.info("\n\n★进入修改角色方法======================================================\n");

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("status", false);
		res.put("msg", "操作失败");

		String msg = H.checkValidation(bindingResult);
		if (H.isNotBlank(msg)) {
			res.put("msg", msg);
			return res;
		}

		Integer[] permissionKeys = null;
		if (H.isNotBlank(permissionIds)) {
			String fistPermissionId = permissionIds.substring(0, 1);
			if (fistPermissionId.equals("0")) {
				permissionIds = permissionIds.substring(2, permissionIds.length());
			}
			String[] ids = permissionIds.split(",");
			permissionKeys = new Integer[ids.length];
			for (int i = 0; i < ids.length; i++) {
				if (!ids[i].equals("null")) {
					permissionKeys[i] = Integer.valueOf(ids[i]);
				}
			}
		}
		try {
			Integer count = 0;
			sysRoleService.modify(sysRole, permissionKeys);
			if (H.isNotBlank(count)) {
				res.put("status", true);
				res.put("msg", "角色修改成功");
				res.put("data", count);
			}
		} catch (Exception e) {
			if (e.getClass().getSimpleName().equalsIgnoreCase("DuplicateKeyException")) {
				res.put("status", false);
				res.put("msg", "角色名已被使用");
			}
			logger.error("修改角色出错", e);
		}

		return res;
	}

	/**
	 * 删除角色
	 * @param id - 角色ID
	 * @return Map<String, Object> - 执行结果报文
	 */
	@ApiOperation(value = "删除角色", notes = "删除角色", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("role:remove")
	public Map<String, Object> remove(@ApiParam(name = "id", value = "角色ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.info("\n\n★进入删除角色方法======================================================\n");

		Map<String, Object> res = new HashMap<>();
		res.put("status", false);
		res.put("msg", "角色删除失败");

		try {
			SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
			if (H.isNotBlank(sysRole) && sysRole.getRoleName().equals("超级管理员")) {
				res.put("status", false);
				res.put("msg", "删除角色失败,禁止删除超级管理员角色!");
			} else {
				Integer count = sysRoleService.remove(id);
				if (H.isNotBlank(count)) {
					res.put("status", true);
					res.put("msg", "角色删除成功");
					res.put("data", sysRole);
				}
			}
		} catch (Exception e) {
			logger.error("删除角色出错", e);
		}
		return res;
	}

	/**
	 * 条件查询系统菜单权限列表
	 * @return List<SysPermission> - 系统菜单权限集合
	 */
	@ApiOperation(value = "查询系统权限列表", notes = "查询系统权限列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/role/tree", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<SysPermission> tree() {
        logger.info("\n\n★进入查询系统权限列表方法======================================================\n");

		SysPermission record = new SysPermission();
		record.setValid(true);
		List<SysPermission> permissionIds = new ArrayList<>();
		try {
			permissionIds = sysPermissionService.select(record);
		} catch (Exception e) {
			logger.error("查询系统权限列表出错", e);
		}
		SysPermission root = new SysPermission();
		root.setId(0);
		root.setPermissionName("一级菜单");
		root.setParentId(-1);
		permissionIds.add(root);
		return permissionIds;
	}
	
	/**
	 * 根据角色ID查询角色所对应的权限ID集合
	 * @param-id:角色ID
	 * @return-List<SysPermission>:角色所对应的权限ID集合
	 */
	@ApiOperation(value = "根据角色ID查询角色所对应的权限集合", notes = "根据角色ID查询角色所对应的权限集合", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/role/permissions", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<Integer> permissions(@ApiParam(name = "id", value = "角色ID", required = true) @RequestParam(value = "id", required = true) Integer id) {
        logger.info("\n\n★进入根据角色ID查询角色所对应的权限集合方法======================================================\n");

		List<Integer> permissionIds = null;
		try {
			permissionIds = sysRoleService.queryPermissionIdList(id);
			if(permissionIds.size() > 0){
				permissionIds.add(0);
			}
		} catch (Exception e) {
			logger.error("查询系统权限列表出错", e);
		}
		return permissionIds == null ? new ArrayList<>() : permissionIds;
	}
}
