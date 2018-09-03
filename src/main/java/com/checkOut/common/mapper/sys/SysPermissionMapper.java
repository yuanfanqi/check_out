package com.checkOut.common.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import org.springframework.stereotype.Repository;

import com.checkOut.common.model.sys.SysPermission;

@Repository
public interface SysPermissionMapper extends Mapper<SysPermission> {
	/**
	 * 根据用户ID与权限类型查询有效的权限信息
	 * @param userId 用户ID
	 * @param valid 是否有效
	 * @param types 权限类型
	 * @return List<SysPermission>
	 * @throws Exception
	 */
	List<SysPermission> selectByUserIdAndType(@Param("userId") Integer userId, @Param("valid") Boolean valid, @Param("types") Short[] types) throws Exception;
	/**
	 * 自定义分页条件查询菜单权限信息
	 * @param record - 系统权限菜单实体
	 * @return List<SysPermission>
	 * @throws Exception
	 */
	List<SysPermission> findPage(@Param("record") SysPermission record, @Param("start") Integer start, @Param("end") Integer end) throws Exception;
	/**
	 * 根据主键查找
	 * @param primaryKey - 主键
	 * @return SysPermission - 系统权限菜单实体
	 * @throws Exception
	 */
	SysPermission findByPrimaryKey(@Param("primaryKey") Integer primaryKey) throws Exception;
}