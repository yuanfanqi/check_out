package com.checkOut.common.service.businessFunction;


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
	public Integer add(String goodsId, Integer prepurchaseNum);
	
}
