package com.checkOut.common.model.businessFunction;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 预进货名单表-实体
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 13:42:03
 * @version 1.0
 * @since 
 */
@Table(name = "prepurchase_list")
public class PrepurchaseList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     * 商品名称
     */
    @Column(name = "goods_name")
	private String goodsName;
	/**
     * 商品ID（商品条码）
     */
    @Id
    @Column(name = "goods_id")
	private String goodsId;
	/**
     * 预计补货数量
     */
    @Column(name = "prepurchase_num")
	private Integer prepurchaseNum;
	/**
     * 修改时间
     */
    @Column(name = "update_time")
	private Date updateTime;

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
	 * 设置：预计补货数量
	 */
	public void setPrepurchaseNum(Integer prepurchaseNum) {
		this.prepurchaseNum = prepurchaseNum;
	}
	/**
	 * 获取：预计补货数量
	 * @return prepurchase_num - 预计补货数量
	 */
	public Integer getPrepurchaseNum() {
		return prepurchaseNum;
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
}
