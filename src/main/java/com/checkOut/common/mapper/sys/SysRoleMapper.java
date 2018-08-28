package com.checkOut.common.mapper.sys;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import org.springframework.stereotype.Repository;

import com.checkOut.common.model.sys.SysRole;

@Repository
public interface SysRoleMapper extends Mapper<SysRole> {
	/**
	 * 根据用户ID查询所对应的有效的角色ID数组
	 * @return userId - 用户ID
	 * @throws Exception
	 */
	List<Integer> findRoleIds(Map<String, Object> params) throws Exception;
	/**
	 * 根据角色ID查询该角色所拥有的权限ID数组
	 * @return sysPermissionId - 权限ID
	 * @throws Exception
	 */
	List<Integer> findRolePermission(Integer id) throws Exception;
}