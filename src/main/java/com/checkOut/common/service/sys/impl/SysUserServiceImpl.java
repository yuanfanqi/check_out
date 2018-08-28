package com.checkOut.common.service.sys.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.checkOut.common.mapper.sys.SysUserMapper;
import com.checkOut.common.mapper.sys.SysUserRoleMapper;
import com.checkOut.common.model.sys.SysUser;
import com.checkOut.common.model.sys.SysUserRole;
import com.checkOut.common.service.sys.SysUserService;
import com.checkOut.common.shiro.ShiroRealm;
import com.checkOut.utils.H;

import tk.mybatis.mapper.entity.Example;

/**
 * 系统用户实体业务逻辑层实现类
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年5月21日 下午8:28:21
 * @version 1.0
 * @since 
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private ShiroRealm shiroRealm;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Integer count = null;
	private SysUser sysUser;
	private List<SysUser> sysUsers;

	@Override
	public SysUser selectOne(SysUser record) throws Exception {
		if(record!=null){
			sysUser = sysUserMapper.selectOne(record);
		}
		return sysUser;
	}

	@Override
	public Integer add(SysUser record,Integer[] roleIdsArr) throws Exception {
		if(H.isNotBlank(record)){
			String salt = H.makeSalt();
			record.setPassword(H.encSource(record.getPassword(), salt, 1));
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			record.setLocked(false);
			record.setOnline(false);
			record.setSalt(salt);
			count = sysUserMapper.insertSelective(record);
			insertRoleIds(record.getId(), roleIdsArr);
			shiroRealm.clearCached();
		}
		return count;
	}

	/**
	 * 删除用户对应角色ID后再新增用户对应的角色ID
	 * @param userId
	 * @param roleIdsArr
	 * @throws Exception
	 */
	private void insertRoleIds(Integer userId, Integer[] roleIdsArr) throws Exception {
		if(H.isNotBlank(roleIdsArr)&&roleIdsArr.length>0){
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setSysUserId(userId);
			Integer deleteCount = sysUserRoleMapper.delete(sysUserRole);
			logger.info("根据用户ID删除角色deleteCount="+deleteCount);
			Integer insertCount = sysUserRoleMapper.insertBatch(userId,roleIdsArr);
			logger.info("为用户添加角色insertCount="+insertCount);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Integer remove(Integer primaryKey) throws Exception {
		if(H.isNotBlank(primaryKey)){
			SysUserRole record = new SysUserRole();
			record.setSysUserId(primaryKey);
			sysUserRoleMapper.delete(record);
			count = sysUserMapper.deleteByPrimaryKey(primaryKey);
			shiroRealm.clearCached();
		}
		return count;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Integer modify(SysUser record,Integer[] roleIds) throws Exception {
		if(H.isNotBlank(record)&&H.isNotBlank(record.getId())){
			record.setUpdateTime(Calendar.getInstance().getTime());
			count = sysUserMapper.updateByPrimaryKeySelective(record);
			insertRoleIds(record.getId(),roleIds);
			shiroRealm.clearCached();
		}
		return count;
	}

	@Override
	public Integer changepwd(SysUser record) throws Exception {
		Integer count =sysUserMapper.updateByPrimaryKeySelective(record);
		return count;
	}

	@Override
	public Integer existuser(SysUser record) throws Exception {
		Integer count=sysUserMapper.selectCount(record);
		return count;
	}

	/**
     * 条件分页查询系统用户列表
     *
     * @param sysUser 页面传入的用户对象条件
     * @param page    当前页码
     * @param limit   每页大小
     * @param sidx    排序字段
     * @param order   排序规则
     * @return 
     */
	@Override
	public List<SysUser> findPage(SysUser record, Integer page, Integer limit) throws Exception {

//		sysUserMapper.
		return null;
	}
	
	@Override
	public SysUser selectByPrimaryKey(Integer primaryKey) throws Exception {
		if(H.isNotBlank(primaryKey)){
			sysUser = sysUserMapper.selectByPrimaryKey(primaryKey);
		}
		return sysUser;
	}

	@Override
	public List<String> findUserNamesByPermissionSign(String permissionSign) throws Exception {
		List<String> userNames = null;
		if(H.isNotBlank(permissionSign)){
			userNames = sysUserMapper.findUserNamesByPermissionSign(permissionSign);
		}
		if(H.isBlank(userNames)){
			userNames = new ArrayList<>();
		}
		userNames.add("admin");
		return userNames;
	}
/*
	@Override
	public PageInfo<SysUser> findPage(SysUser record) throws Exception {
		if (H.isNotBlank(record) && H.isNotBlank(record.getPage()) && H.isNotBlank(record.getRows())) {
			PageHelper.startPage(record.getPage(), record.getRows());
			sysUsers = sysUserMapper.selectByExample(this.convertExample(record));
			if (H.isNotBlank(sysUsers)) {
				pageInfo = new PageInfo<>(sysUsers);
			}
		}
		return pageInfo;
	}

	public Example convertExample(SysUser record) throws Exception {
		Example example = null;
		if (H.isNotBlank(record)) {
			example = new Example(record.getClass());
			Example.Criteria criteria = example.createCriteria();
			Field[] fields = record.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID")) {
					continue;
				}
				StringBuffer getMethodName = new StringBuffer("get")
						.append(field.getName().substring(0, 1).toUpperCase()).append(field.getName().substring(1));
				Method getMethod = record.getClass().getMethod(getMethodName.toString());
				if (H.isNotBlank(getMethod.invoke(record))) {
					criteria.andEqualTo(field.getName(), getMethod.invoke(record));
				}
			}
			if (H.isNotBlank(record.getSortList())) {
				Map<String, String> sortOrder = null;
				StringBuffer condition = new StringBuffer();
				for (int i = 0; i < record.getSortList().size(); i++) {
					sortOrder = record.getSortList().get(i);
					condition.append(sortOrder.get("sortField") + " ")
							.append(sortOrder.get("sortDirection") + ",");
				}
				condition.deleteCharAt(condition.length() - 1);
				example.setOrderByClause(condition.toString());
			}
		}
		return example;
	}*/

}
