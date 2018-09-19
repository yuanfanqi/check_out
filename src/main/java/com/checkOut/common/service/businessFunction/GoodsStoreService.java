package com.checkOut.common.service.businessFunction;

import com.checkOut.common.model.businessFunction.GoodsStore;

/**
 * 商品库存表-业务层接口
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-19 10:50:32
 * @version 1.0
 * @since 
 */
public interface GoodsStoreService {
	/**
	 * 根据id修改商品信息
	 * @param record
	 * @throws Exception
	 */
	public void modify(GoodsStore record) throws Exception;
}
