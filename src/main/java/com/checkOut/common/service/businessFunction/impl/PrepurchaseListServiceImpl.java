package com.checkOut.common.service.businessFunction.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.mapper.businessFunction.PrepurchaseListMapper;
import com.checkOut.common.mapper.businessFunction.TableGoodsMapper;
import com.checkOut.common.model.businessFunction.PrepurchaseList;
import com.checkOut.common.model.businessFunction.TableGoods;
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
	@Autowired
	private TableGoodsMapper tableGoodsMapper;

	@Override
	public Integer add(String goodsId, Integer prepurchaseNum) {
		PrepurchaseList record = new PrepurchaseList();
		record.setGoodsId(goodsId);
		record.setPrepurchaseNum(prepurchaseNum);
		//查询商品表 goodId有关的信息
		TableGoods selectByPrimaryKey = tableGoodsMapper.selectByPrimaryKey(goodsId);
		record.setGoodsName(selectByPrimaryKey.getGoodsName());
//		record.setLastPrice(selectByPrimaryKey.getGoodsBid), 商品进价
		
		return prepurchaseListMapper.insertSelective(record);
	}

}
