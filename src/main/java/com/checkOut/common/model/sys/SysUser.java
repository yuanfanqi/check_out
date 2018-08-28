package com.checkOut.common.model.sys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Email;
import org.springframework.core.annotation.AliasFor;

import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "sys_user")
public class SysUser implements Serializable{
    /**
     * primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "USER_NAME")
	@Pattern(regexp="^(\\S){1,30}$",message="用户名不能为空，最长不得超过30个字符")
    private String userName;

    /**
     * 用户密码
     */
    @Column(name = "PASSWORD")
    @Size (min=6, max=20, message="密码:密码不能为空,长度只能在6-20个字符之间")
    private String password;

    /**
     * 加密用盐
     */
    @Column(name = "SALT")
    private String salt;

    /**
     * 昵称
     */
    @Column(name = "NICK_NAME")
	@Pattern(regexp="^(\\S){0,30}$",message="用户昵称最长不得超过30个字符")

    private String nickName;

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
     * 是否被锁定
     */
    @Column(name = "LOCKED")
    private Boolean locked;

    /**
     * 是否在线
     */
    @Column(name = "ONLINE")
    private Boolean online;

    /**
     * 上次登录时间
     */
    @Column(name = "LOGIN_TIME")
    private Date loginTime;

    /**
     * 上次登录IP
     */
    @Column(name = "LOGIN_IP")
    private String loginIp;

    /**
     * 是否有效
     */
    @Column(name = "VALID")
    private Boolean valid;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 手机号码
     */
    @Column(name = "PHONE")
    @Pattern(regexp="^[\\d]{11}$",message="手机号码:手机号码长度必须为11位的纯数字")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "EMAIL")
    @Email (regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",message="邮箱:邮箱格式不正确,示例:'investoday@com.cn'")
    private String email;
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
     * 获取用户名
     *
     * @return USER_NAME - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取用户密码
     *
     * @return PASSWORD - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取加密用盐
     *
     * @return SALT - 加密用盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置加密用盐
     *
     * @param salt 加密用盐
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取昵称
     *
     * @return NICK_NAME - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
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
     * 获取是否被锁定
     *
     * @return LOCKED - 是否被锁定
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * 设置是否被锁定
     *
     * @param locked 是否被锁定
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * 获取是否在线
     *
     * @return ONLINE - 是否在线
     */
    public Boolean getOnline() {
        return online;
    }

    /**
     * 设置是否在线
     *
     * @param online 是否在线
     */
    public void setOnline(Boolean online) {
        this.online = online;
    }

    /**
     * 获取上次登录时间
     *
     * @return LOGIN_TIME - 上次登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置上次登录时间
     *
     * @param loginTime 上次登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取上次登录IP
     *
     * @return LOGIN_IP - 上次登录IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置上次登录IP
     *
     * @param loginIp 上次登录IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * 获取是否有效
     *
     * @return VALID - 是否有效
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 设置是否有效
     *
     * @param valid 是否有效
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
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

    /**
     * 获取手机号码
     *
     * @return PHONE - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取邮箱
     *
     * @return EMAIL - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}