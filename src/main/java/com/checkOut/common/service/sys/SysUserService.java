package com.checkOut.common.service.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.checkOut.common.model.sys.SysUser;

/**
 * 系统用户实体业务逻辑层接口
 * 
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年5月21日 下午8:27:05
 * @version 1.0
 * @since
 */
public interface SysUserService {

	/**
	 * 新增
	 * @param record - 系统用户实体
	 * @param roleIdsArr - 角色ID数组
	 * @return Integer - 受影响的行数
	 * @throws Exception
	 */
	Integer add(SysUser record,Integer[] roleIdsArr) throws Exception;
	/**
	 * 删除
	 * @param primaryKey - 系统用户实体主键
	 * @return Integer - 受影响的行数
	 * @throws Exception
	 */
	Integer remove(Integer primaryKey) throws Exception;
	/**
	 * 修改
	 * @param record - 系统用户实体
	 * @param roleIds - 角色ID数组
	 * @return - 受影响的行数
	 * @throws Exception
	 */
	Integer modify(SysUser record,Integer[] roleIds) throws Exception;
	/**
	 * 根据条件查询单个系统用户实体
	 * @param record - 系统用户实体条件
	 * @return SysUser
	 */
	SysUser selectOne(SysUser record) throws Exception;
	/***
	 * 修改用户密码
	 * @return - 受影响的行数
	 * @param record - 系统用户实体条件
	 */
	Integer changepwd(SysUser record) throws Exception;
	/***
	 * 是否存在用户
	 * @param record
	 * @return Interger找到的行数
	 * @throws Exception
	 */
	Integer existuser(SysUser record) throws Exception;
	/**
	 * 根据主键查询系统用户实体
	 * @param primaryKey - 系统用户实体主键
	 * @return SysUser
	 * @throws Exception
	 */
	SysUser selectByPrimaryKey(Integer primaryKey) throws Exception;
	/**
	 * 自定义条件分页查询权限实体
	 * @param record - 系统用户实体
	 * @return PageInfo<SysUser> - 系统用户实体与分页信息集合
	 * @throws Exception
	 */
	List<SysUser> findPage(@Param("record")SysUser record,@Param("page") Integer page,@Param("limit") Integer limit) throws Exception;
	/**
	 * 根据权限标识查询对应该权限的用户的用户名集合
	 * @param permissionSign - 权限标识
	 * @return List<String> - 对应该权限的用户的用户名集合
	 * @throws Exception
	 */
	List<String> findUserNamesByPermissionSign(String permissionSign) throws Exception;
}
