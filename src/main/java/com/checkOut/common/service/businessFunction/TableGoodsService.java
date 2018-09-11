package com.checkOut.common.service.businessFunction;

import java.util.List;

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
	public PageData<TableGoods> selectPage(TableGoods tableGoods, Integer page, Integer limit,String sidx, String order) throws Exception;

	/**
	 * 单个商品信息添加
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public Integer add(TableGoods record) throws Exception; 
	
	/**
	 * 按主键查询记录
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public TableGoods select(String goodsId) throws Exception;
	
	/**
	 * 根据主键修改商品信息
	 * @param record
	 * @throws Exception
	 */
	public void modify(TableGoods record) throws Exception;
	
	/**
	 * 验证是否goodsId已经存在于数据库中
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(String goodsId) throws Exception;
	
	/**
	 * 商品信息删除操作
	 * @param goodsIds
	 * @throws Exception
	 */
	public Integer deleteByIds(List<String> goodsIds) throws Exception;
}
