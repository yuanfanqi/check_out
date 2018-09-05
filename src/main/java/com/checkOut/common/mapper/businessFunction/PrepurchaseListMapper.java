package com.checkOut.common.mapper.businessFunction;

import org.springframework.stereotype.Repository;

import com.checkOut.common.model.businessFunction.PrepurchaseList;

import tk.mybatis.mapper.common.Mapper;

/**
 * 预进货名单表-数据访问层接口
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 13:42:03
 * @version 1.0
 * @since 
 */
@Repository
public interface PrepurchaseListMapper extends Mapper<PrepurchaseList>{

}