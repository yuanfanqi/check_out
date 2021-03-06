package com.checkOut.common.mapper.businessFunction;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.checkOut.common.model.businessFunction.GoodsStore;

import tk.mybatis.mapper.common.Mapper;

/**
 * 商品库存表-数据访问层接口
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-19 10:50:32
 * @version 1.0
 * @since 
 */
@Repository
public interface GoodsStoreMapper extends Mapper<GoodsStore> {

	/**
	 * 根据id更新记录
	 * @param record
	 * @return
	 */
	public Integer updateById(@Param("record") GoodsStore record);
	
	/**
	 * 查询记录最大插入日期 
	 * @param goodsId
	 * @return
	 */
	public Date selectMaxInsert(@Param("goodsId")String goodsId);
	
	/**
	 * 条件分页清单列表查询
	 * @param goodsStore
	 * @param start
	 * @param end
	 * @param sidx
	 * @param order
	 * @return
	 */
	public List<GoodsStore> selectPage(@Param("goodsStore")GoodsStore goodsStore, @Param("start")Integer start, @Param("end")Integer end, @Param("sidx")String sidx, @Param("order") String order);
}