package com.checkOut.common.model.sys;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Table(name = "sys_role")
public class SysRole {
    /**
     * primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "ROLE_NAME")
	@Pattern(regexp="^[\\u4e00-\\u9fa5]{1,10}$|^[\\S]{1,20}$",message="角色名称不能为空，最长不得超过20个字符")
    private String roleName;

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
    @Pattern(regexp="^[\\u4e00-\\u9fa5]{0,50}$|^[\\S]{1,100}$",message="备注:备注内容不能超过一百个字符")
    private String remark;

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
     * 获取角色名称
     *
     * @return ROLE_NAME - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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
     * @param valid 是否有效(0:否|1:是)
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
     * @param createTime 创建时间
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
     * @param updateTime 更新时间
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
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}