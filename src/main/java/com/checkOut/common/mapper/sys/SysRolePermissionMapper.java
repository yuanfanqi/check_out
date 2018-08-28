package com.checkOut.common.mapper.sys;

import tk.mybatis.mapper.common.Mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.checkOut.common.model.sys.SysRolePermission;

@Repository
public interface SysRolePermissionMapper extends Mapper<SysRolePermission> {
	/**
	 *批量新增
	 * @param roleId
	 * @param permissionIds
	 * @return
	 * @throws Exception
	 */
	Integer insertBatch(@Param(value="roleId") Integer roleId,@Param(value="permissionIds")Integer[] permissionIds) throws Exception;
}