package com.checkOut.common.model.sys;

import javax.persistence.*;

@Table(name = "sys_role_permission")
public class SysRolePermission {
    /**
     * primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联系统角色表ID
     */
    @Column(name = "SYS_ROLE_ID")
    private Integer sysRoleId;

    /**
     * 关联系统权限表ID
     */
    @Column(name = "SYS_PERMISSION_ID")
    private Integer sysPermissionId;

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
     * @param id primary key
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取关联系统角色表ID
     *
     * @return SYS_ROLE_ID - 关联系统角色表ID
     */
    public Integer getSysRoleId() {
        return sysRoleId;
    }

    /**
     * 设置关联系统角色表ID
     *
     * @param sysRoleId 关联系统角色表ID
     */
    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    /**
     * 获取关联系统权限表ID
     *
     * @return SYS_PERMISSION_ID - 关联系统权限表ID
     */
    public Integer getSysPermissionId() {
        return sysPermissionId;
    }

    /**
     * 设置关联系统权限表ID
     *
     * @param sysPermissionId 关联系统权限表ID
     */
    public void setSysPermissionId(Integer sysPermissionId) {
        this.sysPermissionId = sysPermissionId;
    }
}