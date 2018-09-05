package com.checkOut.common.mapper.businessFunction;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.pageModel.PageData;
import tk.mybatis.mapper.common.Mapper;

/**
 * 商品表-数据访问层接口
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 14:06:15
 * @version 1.0
 * @since 
 */
@Repository
public interface TableGoodsMapper extends Mapper<TableGoods>{

	/**
	 * 条件分页清单列表查询
	 * @param tableGoods
	 * @param page
	 * @param limit
	 * @param sidx
	 * @param order
	 * @return
	 */
	public PageData<TableGoods> selectPage(@Param("tableGoods")TableGoods tableGoods, @Param("page")Integer page, @Param("limit")Integer limit, @Param("sidx")String sidx, @Param("order") String order);

}