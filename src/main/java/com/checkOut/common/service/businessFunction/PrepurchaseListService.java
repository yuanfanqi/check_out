package com.checkOut.common.service.businessFunction;

import com.checkOut.common.model.businessFunction.PrepurchaseList;
import com.checkOut.common.model.commonModel.PageData;

/**
 * 预进货名单表-业务层接口
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 13:42:03
 * @version 1.0
 * @since 
 */
public interface PrepurchaseListService{
	
	/**
	 * 进货名单添加操作
	 * @param goodsId
	 * @param prepurchaseNum
	 * @return
	 */
	public Integer add(String goodsId, Integer prepurchaseNum) throws Exception;
	
	/**
	 * 分页查询
	 * @param record
	 * @param page
	 * @param limit
	 * @param sidx
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public PageData<PrepurchaseList> selectPage(PrepurchaseList record, Integer page, Integer limit,String sidx, String order) throws Exception;

	/**
	 * 进货名单 商品删除
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public Integer delete(String goodsId) throws Exception;
	
	/**
	 * 进货名单 商品修改
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public Integer modify(PrepurchaseList prepurchaseList) throws Exception;
}
