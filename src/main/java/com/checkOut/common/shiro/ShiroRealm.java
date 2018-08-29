package com.checkOut.common.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.checkOut.common.enums.PermissionType;
import com.checkOut.common.mapper.sys.SysPermissionMapper;
import com.checkOut.common.model.ActiveUser;
import com.checkOut.common.model.sys.SysPermission;
import com.checkOut.common.model.sys.SysUser;
import com.checkOut.common.service.sys.SysPermissionService;
import com.checkOut.common.service.sys.SysUserService;
import com.checkOut.utils.H;
import com.checkOut.utils.M;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * Shiro自定义Realm
 * 
 * @author: Raymee
 * @E-mail: leohaoo@vip.qq.com
 * @date: 2017年5月14日 下午4:11:13
 * @version: 1.0
 * @since:
 */
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SysUser record;
    private static final String ADMIN = "admin";

	// 用户认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// 从token中获取账户名
		String userName = (String) token.getPrincipal();
		
		SysUser sysUser = null;
		try {
			// 根据用户名从数据库查询出用户信息
			record = new SysUser();
			record.setUserName(userName);
			sysUser = sysUserService.selectOne(record);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		//如果查询的用户为空,抛UnknownAccountException
		if (H.isBlank(sysUser)) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		
		//判断账户是否被锁定
		if(sysUser.getLocked()==true){
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		
		//判断账户是否无效
        if (sysUser.getValid() == false) {
            throw new DisabledAccountException("账号已过期,请联系管理员");
        }
        
		// 得到密码
		String password = sysUser.getPassword();

		// 得到加密用的盐
		String salt = sysUser.getSalt();

		ActiveUser activeUser = new ActiveUser();
		activeUser.setId(sysUser.getId());
		activeUser.setUserName(userName);
		activeUser.setLoginIp(sysUser.getLoginIp());
		activeUser.setLoginTime(sysUser.getLoginTime());
		activeUser.setName(sysUser.getNickName());
		List<SysPermission> menus = null;
		
		//设置菜单
        //如果是超级管理员身份
        if (ADMIN.equals(sysUser.getUserName())) {

            // 设置菜单
			Short[] types = {PermissionType.CATEGORY.getValue(),PermissionType.MENU.getValue()};
			try {
				menus = sysPermissionService.selectByUserIdAndType(null, types, null);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			activeUser.setMenuTree(M.makeMenuTree(menus));
			
		}else{//如果不是超级管理员身份
			
			// 设置菜单
			try {
				Short[] types = new Short[]{PermissionType.CATEGORY.getValue(),PermissionType.MENU.getValue()};
				menus = sysPermissionService.selectByUserIdAndType(activeUser.getId(), types, true);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			activeUser.setMenuTree(M.makeMenuTree(menus));
			
		}

		// 将activeUser设置simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password, ByteSource.Util.bytes(salt), this.getName());

		return simpleAuthenticationInfo;
	}
	
	//用户授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// 从 principals获取主身份信息
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

		// 从数据库获取到权限数据
		List<SysPermission> permissionList = null;
		
		List<String> permissions = null;

		String userName = activeUser.getUserName();
        if (ADMIN.equals(userName)) {
            // 如果是超级管理员

			// 设置权限
			SysPermission record = new SysPermission();
			record.setType((short) 2);
			record.setValid(true);
			try {
				//permissionList = sysPermissionService.selectByExample(record);
				Example example = new Example(SysPermission.class);
				Criteria criteria = example.createCriteria();
				criteria.andEqualTo("type", 2);
				criteria.andEqualTo("valid", true);
				//TODO
//				PageHelper.startPage(0, 0, false);
				permissionList = sysPermissionMapper.selectByExample(example);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (H.isNotBlank(permissionList)) {
				permissions = new ArrayList<>();
				for (SysPermission sysPermission : permissionList) {
					if (H.isNotBlank(sysPermission.getPermissionSign())) {
						permissions.add(sysPermission.getPermissionSign());
					}
				}
			}

		} else {
			// 如果不是超级管理员

			try {
				// 设置权限
				Short[] types = {PermissionType.PERMISSION.getValue()};
				permissionList = sysPermissionService.selectByUserIdAndType(activeUser.getId(), types, true);
				if (H.isNotBlank(permissionList)) {
					permissions = new ArrayList<>();
					for (SysPermission sysPermission : permissionList) {
						if (H.isNotBlank(sysPermission.getPermissionSign())) {
							permissions.add(sysPermission.getPermissionSign());
						}
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		
		return simpleAuthorizationInfo;
	}

    /**
     * 清除缓存
     */
    public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
    
    /*测试类
     * public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        int hashIterations = 1;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, ADMIN, hashIterations);
        System.out.println(obj);
    }*/
}
