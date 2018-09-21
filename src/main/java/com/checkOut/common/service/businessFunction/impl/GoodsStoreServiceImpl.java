package com.checkOut.common.service.businessFunction.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.mapper.businessFunction.GoodsStoreMapper;
import com.checkOut.common.model.businessFunction.GoodsStore;
import com.checkOut.common.model.commonModel.PageData;
import com.checkOut.common.service.businessFunction.GoodsStoreService;
import com.checkOut.utils.H;

/**
 * 商品库存表-业务层实现类
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-19 10:50:32
 * @version 1.0
 * @since 
 */
@Service("goodsStoreService")
public class GoodsStoreServiceImpl implements GoodsStoreService {
	@Autowired
	private GoodsStoreMapper goodsStoreMapper;

	@Override
	public void modify(GoodsStore record) throws Exception {
		//库存更改只对最新日期进行操作
		Date maxInsertDate = goodsStoreMapper.selectMaxInsert(record.getGoodsId());
		System.out.println(maxInsertDate);
		record.setInsertDate(maxInsertDate);
		goodsStoreMapper.updateById(record);
	}

	@Override
	public PageData<GoodsStore> selectPage(GoodsStore record, Integer page, Integer limit, String sidx, String order)
			throws Exception {
		PageData<GoodsStore> pageData = new PageData<>();
		int selectCount = goodsStoreMapper.selectCount(record);
		//分页参数处理
		Map<String, Integer> pageOperation = H.pageOperation(page, limit, selectCount);
		
		if(H.isBlank(sidx) && H.isBlank(order)){
			sidx = "INSERT_DATE";
			order = "DESC";
		}
		List<GoodsStore> selectPage = goodsStoreMapper.selectPage(record, pageOperation.get("start"), pageOperation.get("end"), sidx, order);
		
		pageData.setList(selectPage);
		pageData.setPageNum(page);
		pageData.setPages(pageOperation.get("pages"));
		pageData.setTotal(selectCount);
		return pageData;
	}

}
