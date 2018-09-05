package com.checkOut.common.service.businessFunction.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.mapper.businessFunction.SellHisMapper;
import com.checkOut.common.service.businessFunction.SellHisService;

/**
 * 销售历史表-业务层实现类
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 14:11:43
 * @version 1.0
 * @since 
 */
@Service("sellHisService")
public class SellHisServiceImpl implements SellHisService {
	@Autowired
	private SellHisMapper sellHisMapper;


}
