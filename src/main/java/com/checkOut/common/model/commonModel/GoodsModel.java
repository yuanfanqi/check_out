package com.checkOut.common.model.commonModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表-实体
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 14:06:15
 * @version 1.0
 * @since 
 */
public class GoodsModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     * 商品ID（商品条码）
     */
	private String goodsId;
	/**
     * 商品名称
     */
	private String goodsName;
    /**
     * 商品分类
     */
    private Integer goodsType;
	/**
     * 商品价格
     */
	private BigDecimal goodsPrice;
	/**
     * 商品进价
     */
	private BigDecimal goodsBid;
	/**
     * 修改时间
     */
	private Date updateTime;
    /**
     * 商品数量（库存）
     */
	private Integer goodsNum;
    /**
     * 保质期
     */
	private Date exp;
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public BigDecimal getGoodsBid() {
		return goodsBid;
	}
	public void setGoodsBid(BigDecimal goodsBid) {
		this.goodsBid = goodsBid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public Date getExp() {
		return exp;
	}
	public void setExp(Date exp) {
		this.exp = exp;
	}
	
}
