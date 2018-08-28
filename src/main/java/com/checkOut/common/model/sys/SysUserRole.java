package com.checkOut.common.model.sys;

import javax.persistence.*;

@Table(name = "sys_user_role")
public class SysUserRole {
    /**
     * primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联系统用户表ID
     */
    @Column(name = "SYS_USER_ID")
    private Integer sysUserId;

    /**
     * 关联系统角色表ID
     */
    @Column(name = "SYS_ROLE_ID")
    private Integer sysRoleId;

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
     * 获取关联系统用户表ID
     *
     * @return SYS_USER_ID - 关联系统用户表ID
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * 设置关联系统用户表ID
     *
     * @param sysUserId 关联系统用户表ID
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
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
}