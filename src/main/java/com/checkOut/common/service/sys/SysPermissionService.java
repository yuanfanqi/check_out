package com.checkOut.common.service.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.checkOut.common.model.commonModel.PageData;
import com.checkOut.common.model.sys.SysPermission;

/**
 * 系统权限实体业务逻辑层接口
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年5月22日 下午4:38:15
 * @version 1.0
 * @since 
 */
public interface SysPermissionService {
	/**
	 * 新增
	 * @param record - 系统权限菜单实体
	 * @return Integer - 受影响的行数
	 * @throws Exception
	 */
	Integer add(SysPermission record) throws Exception;
	/**
	 * 修改
	 * @param record - 系统权限菜单实体
	 * @return Integer - 受影响的行数
	 * @throws Exception
	 */
	Integer modify(SysPermission record) throws Exception;
	/**
	 * 删除
	 * @param primaryKey - 主键
	 * @return Integer - 受影响的行数
	 * @throws Exception
	 */
	Integer remove(Integer primaryKey) throws Exception;
	/**
	 * 根据用户ID与权限类型查询有效的权限信息
	 * @param userId - 用户ID
	 * @param type - 权限类型
	 * @return List<SysPermission>
	 * @throws Exception
	 */
	List<SysPermission> selectByUserIdAndType(Integer userId,Short[] types,Boolean valid) throws Exception;
	/**
	 * 根据条件查询权限信息集合
	 * @param record - 系统权限菜单实体条件
	 * @return List<SysPermission>
	 * @throws Exception
	 */
	List<SysPermission> select(SysPermission record) throws Exception;
	/**
	 * 自定义条件分页查询权限实体(多表)
	 * @param record - 系统权限菜单实体条件
	 * @return PageInfo<SysPermission> - 系统权限菜单实体与分页信息集合
	 * @throws Exception
	 */
//	List<SysPermission> findPage(@Param("record")SysPermission record,@Param("page") Integer page,@Param("limit") Integer limit) throws Exception;
	PageData<SysPermission> findPage(@Param("record")SysPermission record,@Param("page") Integer page,@Param("limit") Integer limit) throws Exception;
	/**
	 * 根据条件查询权限信息集合
	 * @param record - 系统权限菜单实体条件
	 * @return List<SysPermission>
	 * @throws Exception
	 */
	List<SysPermission> selectByExample(SysPermission record) throws Exception;
	/**
	 * 根据主键查询单个菜单权限实体
	 * @param primaryKey - 主键
	 * @return SysPermission - 系统权限菜单实体
	 * @throws Exception
	 */
	SysPermission selectByPrimaryKey(Integer primaryKey) throws Exception;
	/**
	 * 根据主键查找
	 * @param primaryKey - 主键
	 * @return SysPermission - 系统权限菜单实体
	 * @throws Exception
	 */
	SysPermission findByPrimaryKey(Integer primaryKey) throws Exception;
	/**
	 * 根据类型查找父节点
	 * @param type - 类型
	 * @return List<SysPermission>
	 * @throws Exception
	 */
	List<SysPermission> findParentNode(Short type) throws Exception;
}
