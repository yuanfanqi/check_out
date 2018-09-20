package com.checkOut.common.model.businessFunction;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表-实体
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 14:06:15
 * @version 1.0
 * @since 
 */
@Table(name = "table_goods")
public class TableGoods implements Serializable {
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
     * 商品分类
     */
    @Column(name = "goods_type")
    private Integer goodsType;
	/**
     * 商品价格
     */
    @Column(name = "goods_price")
	private BigDecimal goodsPrice;
	/**
     * 商品进价
     */
    @Column(name = "goods_bid")
	private BigDecimal goodsBid;
	/**
     * 修改时间
     */
    @Column(name = "update_time")
	private Date updateTime;
    /**
     * 供货商
     */
    @Column(name = "supplier")
	private Integer supplier;
    
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
	 * 设置：商品分类
	 */
	public Integer getGoodsType() {
		return goodsType;
	}
	/**
	 * 获取：商品分类
	 * @return goods_type - 商品分类
	 */
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	/**
	 * 设置：商品价格
	 */
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	/**
	 * 获取：商品价格
	 * @return goods_price - 商品价格
	 */
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	/**
	 * 设置：商品进价
	 */
	public void setGoodsBid(BigDecimal goodsBid) {
		this.goodsBid = goodsBid;
	}
	/**
	 * 获取：商品进价
	 * @return goods_bid - 商品进价
	 */
	public BigDecimal getGoodsBid() {
		return goodsBid;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 * @return update_time - 修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：供货商
	 */
	public void setSupplier(Integer supplier) {
		this.supplier = supplier;
	}
	/**
	 * 获取：供货商
	 * @return supplier - 供货商
	 */
	public Integer getSupplier() {
		return supplier;
	}
}
