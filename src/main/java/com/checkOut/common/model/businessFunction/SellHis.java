package com.checkOut.common.model.businessFunction;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售历史表-实体
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 14:11:43
 * @version 1.0
 * @since 
 */
@Table(name = "sell_his")
public class SellHis implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     * 日期
     */
    @Id
    @Column(name = "sell_date")
	private Date sellDate;
	/**
     * 商品名称
     */
    @Column(name = "goods_name")
	private String goodsName;
	/**
     * 商品ID（商品条码）
     */
    @Column(name = "goods_id")
	private String goodsId;
	/**
     * 销售数量
     */
    @Column(name = "sell_num")
	private Integer sellNum;
	/**
     * 实际销售价格
     */
    @Column(name = "sell_price")
	private BigDecimal sellPrice;

	/**
	 * 设置：日期
	 */
	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}
	/**
	 * 获取：日期
	 * @return sell_date - 日期
	 */
	public Date getSellDate() {
		return sellDate;
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
	 * 设置：销售数量
	 */
	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}
	/**
	 * 获取：销售数量
	 * @return sell_num - 销售数量
	 */
	public Integer getSellNum() {
		return sellNum;
	}
	/**
	 * 设置：实际销售价格
	 */
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	/**
	 * 获取：实际销售价格
	 * @return sell_price - 实际销售价格
	 */
	public BigDecimal getSellPrice() {
		return sellPrice;
	}
}
