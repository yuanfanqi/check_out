package com.checkOut.common.model.businessFunction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品库存表-实体
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-19 10:50:32
 * @version 1.0
 * @since 
 */
@Table(name = "goods_store")
public class GoodsStore implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     * 商品ID（商品条码）
     */
    @Id
    @Column(name = "goods_id")
	private String goodsId;
	/**
     * 商品名称
     */
    @Column(name = "goods_name")
	private String goodsName;
	/**
     * 商品数量（库存）
     */
    @Column(name = "goods_num")
	private Integer goodsNum;
	/**
     * 添加时间
     */
    @Id
    @Column(name = "insert_date")
	private Date insertDate;
	/**
     * 保质期
     */
    @Column(name = "exp")
	private Date exp;

	/**
	 * 设置：商品ID（商品条码）
	 */
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId == null ? null : goodsId.trim();
	}
	/**
	 * 获取：商品ID（商品条码）
	 * @return goods_id - 商品ID（商品条码）
	 */
	public String getGoodsId() {
		return goodsId;
	}
	/**
	 * 设置：商品名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}
	/**
	 * 获取：商品名称
	 * @return goods_name - 商品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：商品数量（库存）
	 */
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	/**
	 * 获取：商品数量（库存）
	 * @return goods_num - 商品数量（库存）
	 */
	public Integer getGoodsNum() {
		return goodsNum;
	}
	/**
	 * 设置：添加时间
	 */
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	/**
	 * 获取：添加时间
	 * @return insert_date - 添加时间
	 */
	public Date getInsertDate() {
		return insertDate;
	}
	/**
	 * 设置：保质期
	 */
	public void setExp(Date exp) {
		this.exp = exp;
	}
	/**
	 * 获取：保质期
	 * @return exp - 保质期
	 */
	public Date getExp() {
		return exp;
	}
}
