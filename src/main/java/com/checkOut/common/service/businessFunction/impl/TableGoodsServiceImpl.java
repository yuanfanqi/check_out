package com.checkOut.common.service.businessFunction.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.mapper.businessFunction.TableGoodsMapper;
import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.pageModel.PageData;
import com.checkOut.common.service.businessFunction.TableGoodsService;

/**
 * 商品表-业务层实现类
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 14:06:15
 * @version 1.0
 * @since 
 */
@Service("tableGoodsService")
public class TableGoodsServiceImpl implements TableGoodsService {
	@Autowired
	private TableGoodsMapper tableGoodsMapper;

	@Override
	public PageData<TableGoods> selectPage(TableGoods record, Integer page, Integer limit, String sidx,
			String order) {
		PageData<TableGoods> selectPage = new PageData<>();
		TableGoods tableGoods = new TableGoods();
//		int total = tableGoodsMapper.selectCount(record);
		List<TableGoods> list = tableGoodsMapper.select(record);
		//进行分页
		Integer start = ( page - 1 ) * limit;
		Integer end = page *limit;
		
		return selectPage;
	}

}
