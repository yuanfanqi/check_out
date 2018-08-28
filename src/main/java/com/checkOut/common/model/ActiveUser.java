package com.checkOut.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.checkOut.common.model.sys.SysPermission;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2017/3/25 0025
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class ActiveUser implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 账户名
     */
    private String userName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 上次登陆IP
     */
    private String loginIp;
    /**
     * 上次登陆时间
     */
    private Date loginTime;
    /**
     * 菜单树
     */
    private SysPermission menuTree;
    /**
     * 权限集合
     */
    private List<SysPermission> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SysPermission getMenuTree() {
        return menuTree;
    }

    public void setMenuTree(SysPermission menuTree) {
        this.menuTree = menuTree;
    }

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "ActiveUser [id=" + id + ", userName=" + userName + ", name=" + name + ", loginIp=" + loginIp + ", loginTime=" + loginTime + ", menuTree=" + menuTree + ", permissions=" + permissions
				+ "]";
	}

}
