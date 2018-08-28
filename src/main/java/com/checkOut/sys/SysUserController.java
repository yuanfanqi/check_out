package com.checkOut.sys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.checkOut.common.model.sys.SysRole;
import com.checkOut.common.model.sys.SysUser;
import com.checkOut.common.service.sys.SysRoleService;
import com.checkOut.common.service.sys.SysUserService;
import com.checkOut.common.shiro.ShiroUtil;
import com.checkOut.utils.H;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统用户控制器
 *
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @version 1.0
 * @date 创建时间：2017年5月31日 下午4:21:16
 * @since
 */
@Controller
@RequestMapping(value = "/sys")
@Api(value = "SysUserController", tags = "系统用户控制器")
public class SysUserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;


    @ApiOperation(value = "跳转到管理员修改用户密码页", notes = "跳转到管理员修改用户密码页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
    @RequestMapping(value = "/user/play/{userId}/{userName}", method = RequestMethod.GET)
    public ModelAndView play(
            @ApiParam(name = "userId", value = "用户ID", required = false) @PathVariable(value = "userId", required = false) Integer userId,
            @ApiParam(name = "userName", value = "用户名", required = false) @PathVariable(value = "userName", required = false) String userName) {
        logger.info("\n\n★进入跳转到管理员修改用户密码页方法======================================================\n");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/user-pwd");
        mv.addObject("userId", userId);
        mv.addObject("userName", userName);
        return mv;
    }

    /**
     * 跳转到系统用户列表页
     *
     * @return String - 页面
     */
    @ApiOperation(value = "跳转到系统用户列表页", notes = "跳转到系统用户列表页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
    @RequestMapping(value = "/user/show", method = RequestMethod.GET)
    @RequiresPermissions("user:index")
    public String show() {
        logger.info("\n\n★进入跳转到系统用户列表页方法======================================================\n");
        return "sys/user-list";
    }

    /**
     * 跳转到单个用户详情页
     *
     * @param id - 系统用户ID
     * @return String - 页面
     */
    @ApiOperation(value = "跳转到单个用户详情页", notes = "跳转到单个用户详情页", httpMethod = "GET", produces = MediaType.TEXT_HTML_VALUE)
    @RequestMapping(value = "/user/fetch/{id}", method = RequestMethod.GET)
    public ModelAndView fetch(@ApiParam(name = "id", value = "菜单或权限ID", required = false) @PathVariable(value = "id", required = false) Integer id) {
        logger.info("\n\n★进入跳转到单个用户详情页方法======================================================\n");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/user-detail");

        SysUser sysUser = new SysUser();
        List<SysRole> sysRoles = new ArrayList<>();

        try {
            sysRoles = sysRoleService.selectAll();
        } catch (Exception ex) {
            logger.error("查询所有系统角色实体集合出错", ex);
        }
        mv.addObject("sysRoles", sysRoles);

        if (H.isNotBlank(id) && id != 0) {

            //=====================================校验是否有查看权限=====================================//
            boolean isPermitted = SecurityUtils.getSecurityManager().isPermitted(ShiroUtil.getSubject().getPrincipals(), "user:fetch");
            logger.info("是否有查看修改权限isPermitted=" + isPermitted);
            if (!isPermitted) {
                throw new UnauthorizedException("权限不足");
            }
            //=====================================校验是否有查看权限=====================================//

            try {
                sysUser = sysUserService.selectByPrimaryKey(id);
            } catch (Exception e) {
                logger.error("根据主键查询单个菜单权限实体出错", e);
            }
        }
        mv.addObject("sysUser", sysUser);

        return mv;
    }

    /**
     * 添加单个用户
     *
     * @param sysUser - 系统用户实体
     * @return Map<String, Object> - 执行结果报文
     */
    @ApiOperation(value = "添加单个用户", notes = "添加单个用户", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("user:add")
    public Map<String, Object> add(@ModelAttribute(value = "sysUser") @Valid SysUser sysUser, BindingResult bindingResult, Integer[] roleIds) {
        logger.info("\n\n★进入添加单个用户及角色方法======================================================\n");

        Map<String, Object> res = new HashMap<String, Object>(16);
        res.put("status", false);
        res.put("msg", "操作失败");

        String msg = H.checkValidation(bindingResult);

        if (H.isNotBlank(msg)) {
            res.put("msg", msg);
            return res;
        }

        try {
            logger.info("roleIds=" + roleIds);
            Integer count = sysUserService.add(sysUser, roleIds);
            if (H.isNotBlank(count)) {
                res.put("status", true);
                res.put("msg", "添加单个用户成功");
                res.put("data", count);
            }
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equalsIgnoreCase("DuplicateKeyException")) {
                res.put("status", false);
                res.put("msg", "用户名已被使用");
            }
            logger.error("添加单个用户出错", e);
        }

        return res;
    }


    /**
     * 删除单个用户
     *
     * @param id - 系统用户ID
     * @return Map<String, Object> - 执行结果报文
     */
    @ApiOperation(value = "删除单个用户", notes = "删除单个用户", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("user:remove")
    public Map<String, Object> remove(
            @ApiParam(name = "id", value = "系统用户ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.info("\n\n★进入删除单个用户方法======================================================\n");

        Map<String, Object> res = new HashMap<>(16);
        res.put("status", false);
        res.put("msg", "删除单个用户失败");
        try {
            SysUser sysUser = sysUserService.selectByPrimaryKey(id);
            if (H.isNotBlank(sysUser) && sysUser.getUserName().equals("admin")) {
                res.put("status", false);
                res.put("msg", "删除单个用户失败,禁止删除超级管理员!");
            } else {
                Integer count = sysUserService.remove(id);
                if (H.isNotBlank(count)) {
                    res.put("status", true);
                    res.put("msg", "删除单个用户成功");
                    res.put("data", count);
                }
            }
        } catch (Exception e) {
            logger.error("删除单个用户出错", e);
        }
        return res;
    }

    /**
     * 修改单个用户及角色
     *
     * @param sysUser - 系统用户实体
     * @return Map<String, Object> - 执行结果报文
     */
    @ApiOperation(value = "修改单个用户及角色", notes = "修改单个用户及角色", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    @RequiresPermissions("user:modify")
    public Map<String, Object> modify(@ModelAttribute(name = "sysUser") @Valid SysUser sysUser, BindingResult bindingResult, Integer[] roleIds) {
        logger.info("\n\n★进入修改单个用户及角色方法======================================================\n");

        Map<String, Object> res = new HashMap<String, Object>(16);
        res.put("status", false);
        res.put("msg", "操作失败");

        String msg = H.checkValidation(bindingResult);
        if (H.isNotBlank(msg)) {
            res.put("msg", msg);
            return res;
        }

        try {
            Integer count = sysUserService.modify(sysUser, roleIds);
            if (H.isNotBlank(count)) {
                res.put("status", true);
                res.put("msg", "修改单个用户成功");
                res.put("data", count);
            }
        } catch (Exception e) {
            logger.error("修改单个用户出错", e);
        }

        return res;
    }

    /**
     * 修改单个用户信息
     *
     * @param userId        用户ID
     * @param userName      用户名
     * @param password      用户密码
     * @param nickName      用户昵称
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改单个用户及角色", notes = "修改单个用户及角色", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/user/pwd", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> modify(
            @ApiParam(name = "userId", value = "用户ID", required = true) @RequestParam(value = "userId", required = true) @Valid Integer userId,
            @ApiParam(name = "userName", value = "用户名", required = true) @RequestParam(value = "userName", required = true) @Valid String userName,
            @ApiParam(name = "password", value = "用户密码", required = true) @RequestParam(value = "password", required = true) @Valid String password,
            @ApiParam(name = "nickName", value = "用户昵称", required = true) @RequestParam(value = "nickName", required = true) @Valid String nickName,
            BindingResult bindingResult) {
        logger.info("\n\n★进入修改单个用户信息方法======================================================\n");

        Map<String, Object> res = new HashMap<>(16);
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                if (((FieldError) objectError).getField().equals("phone")) {
                    sb.append("手机号码：").append(objectError.getDefaultMessage()).append("</br>");
                } else if (((FieldError) objectError).getField().equals("email")) {
                    sb.append("邮箱：").append(objectError.getDefaultMessage()).append("</br>");
                } else if (((FieldError) objectError).getField().equals("nickName")) {
                    sb.append("昵称：").append(objectError.getDefaultMessage()).append("</br>");
                }
            }
            res.put("status", false);
            res.put("msg", sb.toString());
            return res;
        }
        try {
            SysUser record = new SysUser();
            record.setId(userId);
            record.setUserName(userName);
            record.setNickName(nickName);
            SysUser sysUser = sysUserService.selectOne(record);
            if (H.isNotBlank(sysUser)) {
                // 得到加密用的盐
                String salt = sysUser.getSalt();
                password = H.encSource(password, salt, 1);
                sysUser.setPassword(password);
            }
            Integer count = sysUserService.modify(sysUser, null);
            if (H.isNotBlank(count)) {
                res.put("status", true);
                res.put("msg", "修改单个用户成功");
                res.put("data", count);
            }
        } catch (Exception e) {
            logger.error("修改单个用户出错", e);
        }
        return res;
    }

    /**
     * 条件分页查询系统用户列表
     *
     * @param sysUser 页面传入的用户对象条件
     * @param page    当前页码
     * @param limit   每页大小
     * @param sidx    排序字段
     * @param order   排序规则
     * @return 
     */
    @ApiOperation(value = "条件分页查询系统用户列表", notes = "条件分页查询系统用户列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/user/search", method = RequestMethod.POST)
    @ResponseBody
    public List<SysUser> search(@ModelAttribute(value = "sysUser") SysUser sysUser,
                                    @ApiParam(name = "page", value = "当前页码", required = true, defaultValue = "1") @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                    @ApiParam(name = "limit", value = "每页大小", required = true, defaultValue = "10") @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
                                    @ApiParam(name = "sidx", value = "排序字段", required = false) @RequestParam(value = "sidx", required = false) String sidx,
                                    @ApiParam(name = "order", value = "排序规则", required = false) @RequestParam(value = "order", required = false) String order) {
        logger.info("\n\n★进入条件分页查询系统用户列表方法======================================================\n");

        List<SysUser> findPage = null;
        try {
            findPage = sysUserService.findPage(sysUser, page, limit);
        } catch (Exception e) {
            logger.error("根据条件分页查询系统用户列表出错", e);
        }

        return findPage;
    }

    @ApiOperation(value = "修改用户密码", notes = "修改用户密码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/user/password", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changepassword(String username, String password, String newpassword, HttpServletRequest request) throws ServletException, IOException {
        logger.info("\n\n★进入修改用户密码方法======================================================\n");

        Map<String, Object> res = new HashMap<>(16);
        res.put("status", false);
        res.put("msg", "修改不成功哦");
        SysUser record = new SysUser();
        SysUser record2 = new SysUser();
        record.setUserName(username);
        try {
            record2 = sysUserService.selectOne(record);
        } catch (Exception e2) {
            logger.error("查询修改密码的用户出错");
        }
        //此时只能用username去查询 因为密码加密了不能直接传密码去查询
        password = H.encSource(password, record2.getSalt(), 1);
        record.setPassword(password);
        record.setSalt(record2.getSalt());
        Integer count1 = 0;
        try {
            count1 = sysUserService.existuser(record);
            logger.info("进入了---查询密码是否正确，不知道结果下一步：");
            logger.info("count1结果！~~~~~~~~~~~~~~~~" + count1.toString());
        } catch (Exception e1) {
            logger.error("查询用户salt错误");
        }

        //更新密码
        int count = 0;
        if (count1 == 1) {
            logger.info("结果，用密码查询到了，用户密码正确，所以下一步可以修改了");
            password = H.encSource(newpassword, record2.getSalt(), 1);
            record.setPassword(password);
            record.setId(record2.getId());
            try {
                count = sysUserService.changepwd(record);
            } catch (Exception e) {
                logger.error("修改这步报错了ssss");
            }
            if (count == 1) {
                logger.info("修改成功了~");
                res.put("status", true);
                res.put("msg", "修改密码成功");
                return res;
            }
        } else {
            logger.info("密码错误了哦~~~~~~~~~~~~");
            res.put("status", false);
            res.put("msg", "原密码错误");
            return res;
        }
        return res;
    }

    @ApiOperation(value = "根据用户ID及用户名查询对应的角色ID", notes = "根据用户ID及用户名查询对应的角色ID", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/user/role", method = RequestMethod.POST)
    @ResponseBody
    public List<Integer> role(@ApiParam(name = "userId", value = "用户ID", required = false) @RequestParam(value = "userId", required = true) Integer userId,
                              @ApiParam(name = "userName", value = "用户名", required = false) @RequestParam(value = "userName", required = true) String userName) {
        logger.info("\n\n★进入根据用户ID及用户名查询对应的角色ID方法======================================================\n");

        List<Integer> roleIds = new ArrayList<>();
        try {
            roleIds = sysRoleService.findRoleIds(userId, userName);
        } catch (Exception e) {
            logger.error("根据用户ID及用户名查询对应的角色ID");
        }
        return roleIds;

    }
}
