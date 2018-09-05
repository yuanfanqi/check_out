package com.checkOut.common.mapper.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.checkOut.common.model.sys.SysUser;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SysUserMapper extends Mapper<SysUser> {
	/**
	 * 根据权限标识查询对应该权限的用户的用户名集合
	 * @param permissionSign - 权限标识
	 * @return List<String> - 对应该权限的用户的用户名集合
	 * @throws Exception
	 */
	List<String> findUserNamesByPermissionSign(String permissionSign) throws Exception;
}