package com.checkOut.common.service.businessFunction.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.mapper.businessFunction.PrepurchaseListMapper;
import com.checkOut.common.service.businessFunction.PrepurchaseListService;

/**
 * 预进货名单表-业务层实现类
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 13:42:03
 * @version 1.0
 * @since 
 */
@Service("prepurchaseListService")
public class PrepurchaseListServiceImpl implements PrepurchaseListService {
	@Autowired
	private PrepurchaseListMapper prepurchaseListMapper;

}
