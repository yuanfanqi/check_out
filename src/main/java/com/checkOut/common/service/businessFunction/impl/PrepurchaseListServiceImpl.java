package com.checkOut.common.service.businessFunction.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.mapper.businessFunction.PrepurchaseListMapper;
import com.checkOut.common.mapper.businessFunction.TableGoodsMapper;
import com.checkOut.common.model.businessFunction.PrepurchaseList;
import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.commonModel.PageData;
import com.checkOut.common.service.businessFunction.PrepurchaseListService;
import com.checkOut.utils.H;

/**
 * 预进货名单表-业务层实现类
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 13:42:03
 * @version 1.0
 * @since 
 */
@Service("prepurchaseListService")
public class PrepurchaseListServiceImpl implements PrepurchaseListService {
	@Autowired
	private PrepurchaseListMapper prepurchaseListMapper;
	@Autowired
	private TableGoodsMapper tableGoodsMapper;

	@Override
	public Integer add(String goodsId, Integer prepurchaseNum) {
		PrepurchaseList record = new PrepurchaseList();
		record.setGoodsId(goodsId);
		record.setPrepurchaseNum(prepurchaseNum);
		//查询商品表 goodId有关的信息
		TableGoods selectByPrimaryKey = tableGoodsMapper.selectByPrimaryKey(goodsId);
		record.setGoodsName(selectByPrimaryKey.getGoodsName());
//		record.setLastPrice(selectByPrimaryKey.getGoodsBid), 商品进价
		
		return prepurchaseListMapper.insertSelective(record);
	}

	@Override
	public PageData<PrepurchaseList> selectPage(PrepurchaseList record, Integer page, Integer limit, String sidx,
			String order) throws Exception {
		PageData<PrepurchaseList> pageData = new PageData<>();
		
		int count = prepurchaseListMapper.selectCount(record);
		//页面数据处理
		Map<String, Integer> pageOperation = H.pageOperation(page, limit, count);
		if(H.isBlank(sidx) && H.isBlank(order)){
			sidx = "UPDATE_TIME";
			order = "DESC";
		}
		List<PrepurchaseList> selectPage = prepurchaseListMapper.selectPage(record, pageOperation.get("start"), pageOperation.get("end"), sidx, order);
		
		pageData.setTotal(count);
		pageData.setPageNum(page);
		pageData.setList(selectPage);
		pageData.setPages(pageOperation.get("pages"));
		
		return pageData;
	}

	@Override
	public Integer delete(String goodsId) throws Exception {
		return prepurchaseListMapper.deleteByPrimaryKey(goodsId);
	}

	@Override
	public Integer modify(PrepurchaseList prepurchaseList) throws Exception {
		return prepurchaseListMapper.updateByPrimaryKeySelective(prepurchaseList);
	}

	@Override
	public List<PrepurchaseList> doExport(String themeCode) throws Exception {
		return prepurchaseListMapper.selectForExport(themeCode);
	}

	@Override
	public List<String> selectOldPrepurchaseList() throws Exception {
		return null;
	}

}
