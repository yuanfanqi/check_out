package com.checkOut.common.mapper.businessFunction;

import java.sql.Date;

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
	
	public Date selectMaxInsert(@Param("goodsId")String goodsId);
}