package com.checkOut.common.service.sys.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.enums.PermissionType;
import com.checkOut.common.mapper.sys.SysPermissionMapper;
import com.checkOut.common.model.pageModel.PageData;
import com.checkOut.common.model.sys.SysPermission;
import com.checkOut.common.service.sys.SysPermissionService;
import com.checkOut.common.shiro.ShiroRealm;
import com.checkOut.utils.H;

//import com.investoday.cms.common.shiro.ShiroRealm;

/**
 * 系统权限实体业务逻辑层实现类
 * 
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年5月22日 下午4:40:15
 * @version 1.0
 * @since
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	@Autowired
	private ShiroRealm shiroRealm;
	private List<SysPermission> sysPermissions = null;
	private SysPermission sysPermission = null;
	private Integer count = null;

	@Override
	public List<SysPermission> selectByUserIdAndType(Integer userId, Short[] types, Boolean valid) throws Exception {
		return sysPermissionMapper.selectByUserIdAndType(userId,valid,types);
	}

	@Override
	public List<SysPermission> select(SysPermission record) {
		if (record != null) {
			sysPermissions = sysPermissionMapper.select(record);
		}
		return sysPermissions;
	}

	@Override
	public Integer add(SysPermission record) throws Exception {
		if(H.isNotBlank(record)){

            //如果是菜单类型
            if (record.getType().equals(PermissionType.MENU.getValue())) {
                record.setPermissionSign(null);
			}else if(PermissionType.CATEGORY.getValue().equals(record.getType())){
				record.setPermissionSign(null);
			}else{
				//如果是权限
				record.setIcon(null);
				record.setUrl(null);
			}
			
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			record.setValid(true);
			
			count = sysPermissionMapper.insertSelective(record);
			shiroRealm.clearCached();
		}
		return count;
	}

	@Override
	public List<SysPermission> selectByExample(SysPermission record) throws Exception {
		if (record != null) {
			sysPermissions = sysPermissionMapper.select(record);
		}
		return sysPermissions;
	}

	@Override
	public SysPermission selectByPrimaryKey(Integer primaryKey) throws Exception {
		if(H.isNotBlank(primaryKey)){
			sysPermission = sysPermissionMapper.selectByPrimaryKey(primaryKey);
		}
		return sysPermission;
	}

	@Override
	public Integer modify(SysPermission record) throws Exception {
		if(H.isNotBlank(record)){
			if (record.getType() == 2) {
				record.setIcon(null);
				record.setUrl(null);
			}
			count = sysPermissionMapper.updateByPrimaryKeySelective(record);
			shiroRealm.clearCached();
		}
		return count;
	}

	@Override
	public Integer remove(Integer primaryKey) throws Exception {
		if(H.isNotBlank(primaryKey)){
			count = sysPermissionMapper.deleteByPrimaryKey(primaryKey);
			shiroRealm.clearCached();
		}
		return count;
	}

	@Override
	public PageData<SysPermission> findPage(SysPermission record, Integer page, Integer limit) throws Exception {
		PageData<SysPermission> pageInfo = new PageData<>();
		
		Integer start = (page-1) * limit;
		Integer end = page * limit;
		List<SysPermission> findPage = sysPermissionMapper.findPage(record, start, end);
		int total = sysPermissionMapper.selectCount(record);
		
		pageInfo.setTotal(total);
		pageInfo.setPageNum(page);
		pageInfo.setPages(limit);
		pageInfo.setList(findPage);
		
		return pageInfo;
	}
	
	@Override
	public SysPermission findByPrimaryKey(Integer primaryKey) throws Exception {
		if(H.isNotBlank(primaryKey)){
			sysPermission = sysPermissionMapper.findByPrimaryKey(primaryKey);
		}
		return sysPermission;
	}

	@Override
	public List<SysPermission> findParentNode(Short type) throws Exception {
		sysPermissions = new ArrayList<>();
		if(H.isNotBlank(type)){
			sysPermission = new SysPermission();
			switch (type) {
			case 0:
				sysPermission.setId(0);
				sysPermission.setPermissionName("一级菜单");
				sysPermission.setParentId(-1);
				sysPermissions.add(sysPermission);
				break;
			case 1:
				sysPermission.setType(PermissionType.CATEGORY.getValue());
				sysPermission.setValid(true);
				sysPermissions = sysPermissionMapper.select(sysPermission);
				break;
			case 2:
				sysPermission.setType(PermissionType.MENU.getValue());
				sysPermission.setValid(true);
				sysPermissions = sysPermissionMapper.select(sysPermission);
				break;
			default:
				break;
			}
		}
		return sysPermissions == null ? new ArrayList<>() : sysPermissions;
	}

}
