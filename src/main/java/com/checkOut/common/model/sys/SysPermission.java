package com.checkOut.common.model.sys;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;


@Table(name = "sys_permission")
public class SysPermission {
	/**
	 * primary key
	 */
	@Id
	@Column(name = "ID")
	private Integer id;

	/**
	 * 权限名称
	 */
	@Column(name = "PERMISSION_NAME")
	@Pattern(regexp="^(\\S){1,20}$",message="权限或菜单名称不能为空，最长不得超过20个字符")
	private String permissionName;

	/**
	 * 权限类型(0:目录|1:菜单|2:权限)
	 */
	@Column(name = "TYPE")
	private Short type;

	/**
	 * 权限标识
	 */
	@Column(name = "PERMISSION_SIGN")
	@Pattern(regexp="^(\\w+):(\\w+)$|^(\\w+):(\\w+)+(,(\\w+):(\\w+))*$",message="权限标识不能为空,如有多个请用逗号隔开。示例：user:list,user:add")
	private String permissionSign;

	/**
	 * 父菜单ID
	 */
	@Column(name = "PARENT_ID")
	private Integer parentId;
	
	/**
	 * 父菜单名称
	 */
	@Transient
	private String parentName;

	/**
	 * 链接地址
	 */
	@Column(name = "URL")
	@Pattern(regexp="^(/(\\w+))*$",message="链接地址应以/分隔；示例：/user/list")
	private String url;

	/**
	 * 排列序号
	 */
	@Range(min=0,max=99,message="排列序号值应在0-100之间！")
	@Column(name = "SORT")
	private Integer sort;

	/**
	 * 是否有效(0:否|1:是)
	 */
	@Column(name = "VALID")
	private Boolean valid;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;

	/**
	 * 子菜单
	 */
	@Transient
	private List<SysPermission> childs;
	
	/**
	 * font awsome所使用的图标代码
	 */
	@Column(name = "ICON")
	private String icon;

	/**
	 * 获取primary key
	 *
	 * @return ID - primary key
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置primary key
	 *
	 * @param id
	 *            primary key
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取权限名称
	 *
	 * @return PERMISSION_NAME - 权限名称
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * 设置权限名称
	 *
	 * @param permissionName
	 *            权限名称
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName == null ? null : permissionName.trim();
	}

	/**
	 * 获取权限类型(1:菜单|2:权限)
	 *
	 * @return TYPE - 权限类型(1:菜单|2:权限)
	 */
	public Short getType() {
		return type;
	}

	/**
	 * 设置权限类型(1:菜单|2:权限)
	 *
	 * @param type
	 *            权限类型(1:菜单|2:权限)
	 */
	public void setType(Short type) {
		this.type = type;
	}

	/**
	 * 获取权限标识
	 *
	 * @return PERMISSION_SIGN - 权限标识
	 */
	public String getPermissionSign() {
		return permissionSign;
	}

	/**
	 * 设置权限标识
	 *
	 * @param permissionSign
	 *            权限标识
	 */
	public void setPermissionSign(String permissionSign) {
		this.permissionSign = permissionSign == null ? null : permissionSign.trim();
	}

	/**
	 * 获取父菜单ID
	 *
	 * @return PARENT_ID - 父菜单ID
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 设置父菜单ID
	 *
	 * @param parentId
	 *            父菜单ID
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取链接地址
	 *
	 * @return URL - 链接地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置链接地址
	 *
	 * @param url
	 *            链接地址
	 */
	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	/**
	 * 获取排列序号
	 *
	 * @return SORT - 排列序号
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * 设置排列序号
	 *
	 * @param sort
	 *            排列序号
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 获取是否有效(0:否|1:是)
	 *
	 * @return VALID - 是否有效(0:否|1:是)
	 */
	public Boolean getValid() {
		return valid;
	}

	/**
	 * 设置是否有效(0:否|1:是)
	 *
	 * @param valid
	 *            是否有效(0:否|1:是)
	 */
	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	/**
	 * 获取创建时间
	 *
	 * @return CREATE_TIME - 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取更新时间
	 *
	 * @return UPDATE_TIME - 更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置更新时间
	 *
	 * @param updateTime
	 *            更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取备注
	 *
	 * @return REMARK - 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 *
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * 获取子菜单
	 *
	 * @return List<SysPermission> - 子菜单
	 */
	public List<SysPermission> getChilds() {
		return childs;
	}

	/**
	 * 设置子菜单
	 *
	 * @param childs
	 *            子菜单
	 */
	public void setChilds(List<SysPermission> childs) {
		this.childs = childs;
	}

	/**
	 * 获取font awsome所使用的图标代码
	 *
	 * @return ICON - font awsome所使用的图标代码
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置font awsome所使用的图标代码
	 *
	 * @param icon
	 *            font awsome所使用的图标代码
	 */
	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	/**
	 * 获取父菜单Name
	 *
	 * @return parentName - 父菜单Name
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * 设置父菜单Name
	 *
	 * @param parentName - 父菜单Name
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}