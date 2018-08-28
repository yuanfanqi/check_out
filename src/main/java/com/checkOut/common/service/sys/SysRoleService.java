package com.checkOut.common.service.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.checkOut.common.model.sys.SysRole;

/**
 * 系统角色实体业务逻辑层接口
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年5月31日 下午7:57:00
 * @version 1.0
 * @since 
 */
public interface SysRoleService {
	/**
	 * 新增
	 * @param record - 系统角色实体
	 * @return Integer - 受影响的行数
	 * @throws Exception
	 */
	Integer add(SysRole record,Integer[] permissionIdsArr) throws Exception;
	/**
	 * 删除
	 * @param primaryKey - 系统角色实体主键
	 * @return Integer - 受影响的行数
	 * @throws Exception
	 */
	Integer remove(Integer primaryKey) throws Exception;
	/**
	 * 修改
	 * @param record - 系统角色实体
	 * @return - 受影响的行数
	 * @throws Exception
	 */
	Integer modify(SysRole record,Integer[] permissionIdsArr) throws Exception;
	/**
	 * 根据条件查询单个系统角色实体
	 * @param record - 系统角色实体条件
	 * @return SysRole
	 */
	SysRole selectOne(SysRole record) throws Exception;
	/**
	 * 查询所有系统角色实体集合
	 * @return List<SysRole>
	 * @throws Exception
	 */
	List<SysRole> selectAll() throws Exception;
	/**
	 * 根据条件查询系统角色实体集合
	 * @param record - 系统角色实体条件
	 * @return List<SysRole>
	 * @throws Exception
	 */
	List<SysRole> selectByExample(SysRole record) throws Exception;
	/**
	 * 自定义条件分页查询系统角色实体
	 * @param record - 系统角色实体
	 * @return PageInfo<SysRole> - 系统角色实体与分页信息集合
	 * @throws Exception
	 */
	List<SysRole> findPage(@Param("record")SysRole record,@Param("page") Integer page,@Param("limit") Integer limit) throws Exception;
	/**
	 * 根据用户ID查询所对应的有效的角色ID数组
	 * @return List<Integer> - 用户ID
	 * @throws Exception
	 */
	List<Integer> findRoleIds(Integer userId,String userName) throws Exception;
	/**
	 * 根据角色PrimaryKey查询系统角色实体
	 * @return SysRole - 用户ID
	 * @throws Exception
	 */
	SysRole selectByPrimaryKey(Integer id) throws Exception;
	/**
	 * 根据角色ID查询该角色所拥有的权限ID数组
	 * @return permissionId - 权限ID
	 * @throws Exception
	 */
	List<Integer> queryPermissionIdList(Integer id) throws Exception;
}
