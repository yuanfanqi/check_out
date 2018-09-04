package com.checkOut.common.service.sys.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.checkOut.common.mapper.sys.SysRoleMapper;
import com.checkOut.common.mapper.sys.SysRolePermissionMapper;
import com.checkOut.common.model.sys.SysRole;
import com.checkOut.common.model.sys.SysRolePermission;
import com.checkOut.common.service.sys.SysRoleService;
import com.checkOut.common.shiro.ShiroRealm;
import com.checkOut.utils.H;

/**
 * 系统角色实体业务逻辑层实现类
 * 
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年5月31日 下午8:00:25
 * @version 1.0
 * @since
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;
	@Autowired
	private ShiroRealm shiroRealm;
	private Integer count = null;
	private SysRole sysRole;
	private List<SysRole> sysRoles;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
	public Integer add(SysRole record, Integer[] permissionIdsArr) throws Exception {
		if (H.isNotBlank(record)) {
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			count = sysRoleMapper.insertSelective(record);
			this.insertPermissionIds(record.getId(), permissionIdsArr);
			logger.info("角色id为" + record.getId());
			shiroRealm.clearCached();
		}
		return count;
	}

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
	public Integer remove(Integer primaryKey) throws Exception {
		if (H.isNotBlank(primaryKey)) {
			count = sysRoleMapper.deleteByPrimaryKey(primaryKey);
			SysRolePermission sysRolePermission = new SysRolePermission();
			sysRolePermission.setSysRoleId(primaryKey);
			Integer deleteCount = sysRolePermissionMapper.delete(sysRolePermission);
			logger.info("删除角色权限中间表数据：" + deleteCount + "条");
			shiroRealm.clearCached();
		}
		return count;
	}

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
	public Integer modify(SysRole record, Integer[] permissionIdsArr) throws Exception {
		if (H.isNotBlank(record) && H.isNotBlank(record.getId())) {
			record.setUpdateTime(Calendar.getInstance().getTime());
			count = sysRoleMapper.updateByPrimaryKeySelective(record);
			this.insertPermissionIds(record.getId(), permissionIdsArr);
			shiroRealm.clearCached();
		}
		return count;
	}

	@Override
	public SysRole selectOne(SysRole record) throws Exception {
		if (record != null) {
			sysRole = sysRoleMapper.selectOne(record);
		}
		return sysRole;
	}

	@Override
	public List<SysRole> selectAll() throws Exception {
		return sysRoleMapper.selectAll();
	}

	@Override
	public List<SysRole> findPage(SysRole record, Integer page, Integer limit) throws Exception {
		// TODO 未做分页（role目前没有做分页，后期补全）
		List<SysRole> select = sysRoleMapper.select(record);
		return select;
	}
	
	@Override
	public List<SysRole> selectByExample(SysRole record) throws Exception {
		if (H.isNotBlank(record)) {
			sysRoles = sysRoleMapper.selectByExample(record);
		}
		return sysRoles;
	}

	@Override
	public List<Integer> findRoleIds(Integer userId, String userName) throws Exception {
		List<Integer> ids = new ArrayList<>();
		if (H.isNotBlank(userId) && H.isNotBlank(userName)) {
            Map<String, Object> params = new HashMap<>(16);
            params.put("userId", userId);
			params.put("userName", userName);
			ids = sysRoleMapper.findRoleIds(params);
		}
		return ids;
	}

	@Override
	public SysRole selectByPrimaryKey(Integer id) throws Exception {
		if (id != null) {
			sysRole = sysRoleMapper.selectByPrimaryKey(id);
		}
		return sysRole;
	}

	@Override
	public List<Integer> queryPermissionIdList(Integer id) throws Exception {
		return sysRoleMapper.findRolePermission(id);
	}

	/**
	 * 删除角色对应的权限后再新增角色权限
	 *
     * @param roleId
     * @param premissionIdsArr
     * @throws Exception
	 */
	private void insertPermissionIds(Integer roleId, Integer[] premissionIdsArr) throws Exception {
		SysRolePermission sysRolePermission = new SysRolePermission();
		if (H.isNotBlank(premissionIdsArr) && premissionIdsArr.length > 0) {
			sysRolePermission.setSysRoleId(roleId);
			Integer deleteCount = sysRolePermissionMapper.delete(sysRolePermission);
			logger.info("根据角色ID删除权限deleteCount=" + deleteCount);
			
			Integer insertCount = sysRolePermissionMapper.insertBatch(roleId, premissionIdsArr);
			logger.info("为角色添加权限insertCount=" + insertCount);
		} else {
			sysRolePermission.setSysRoleId(roleId);
			Integer deleteCount = sysRolePermissionMapper.delete(sysRolePermission);
			logger.info("根据角色ID删除权限deleteCount=" + deleteCount);
		}
	}

/*
	@Override
	public PageInfo<SysRole> findPage(SysRole record) throws Exception {
		pageInfo = null;
		if (H.isNotBlank(record) && H.isNotBlank(record.getPage()) && H.isNotBlank(record.getRows())) {
			PageHelper.startPage(record.getPage(), record.getRows());
			sysRoles = sysRoleMapper.selectByExample(this.convertExample(record));
			if (H.isNotBlank(sysRoles)) {
				pageInfo = new PageInfo<>(sysRoles);
			}
		}
		return pageInfo;
	}
	public Example convertExample(SysRole record) throws Exception {
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
