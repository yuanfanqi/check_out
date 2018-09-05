package com.checkOut.common.service.businessFunction;

import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.pageModel.PageData;

/**
 * 商品表-业务层接口
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 14:06:15
 * @version 1.0
 * @since 
 */
public interface TableGoodsService {
	
	/**
	 * 条件分页清单列表查询
	 * @param tableGoods
	 * @param page
	 * @param limit
	 * @param sidx
	 * @param order
	 * @return
	 */
	public PageData<TableGoods> selectPage(TableGoods tableGoods, Integer page, Integer limit,String sidx, String order);
}
