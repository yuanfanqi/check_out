package com.checkOut.common.mapper.businessFunction;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.checkOut.common.model.businessFunction.TableGoods;

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
	public List<TableGoods> selectPage(@Param("tableGoods")TableGoods tableGoods, @Param("start")Integer start, @Param("end")Integer end, @Param("sidx")String sidx, @Param("order") String order);

	/**
	 * 根据id数组进行商品信息删除操作
	 * @param goodsIds
	 * @return
	 */
	public Integer deleteByIds(@Param("goodsIds") List<String> goodsIds);
	
	/**
	 * 按照给的字段进行模糊查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<TableGoods> likeSelect(@Param("goodsId")String goodsId,@Param("goodsName")String goodsName);
}