package com.checkOut.common.mapper.sys;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.checkOut.common.model.sys.SysUserRole;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SysUserRoleMapper extends Mapper<SysUserRole> {
	/**
	 * 批量新增
	 * @param userId - 用户ID
	 * @param roleIds - 角色ID数组
	 * @return Integer - 受影响的行数
	 * @throws Exception
	 */
	Integer insertBatch(@Param(value="userId") Integer userId,@Param(value="roleIds") Integer[] roleIds) throws Exception;
}