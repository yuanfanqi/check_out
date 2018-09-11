package com.checkOut.common.service.businessFunction.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.mapper.businessFunction.TableGoodsMapper;
import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.pageModel.PageData;
import com.checkOut.common.service.businessFunction.TableGoodsService;
import com.checkOut.utils.H;

/**
 * 商品表-业务层实现类
 * @author 77 E-mail:77@163.com
 * @date 创建时间：2018-09-05 14:06:15
 * @version 1.0
 * @since 
 */
@Service("tableGoodsService")
public class TableGoodsServiceImpl implements TableGoodsService {
	@Autowired
	private TableGoodsMapper tableGoodsMapper;

	@Override
	public PageData<TableGoods> selectPage(TableGoods record, Integer page, Integer limit, String sidx,
			String order) throws Exception {
		PageData<TableGoods> pageInfo = new PageData<>();
		int total = tableGoodsMapper.selectCount(record);
		//分页参数处理
		Map<String, Integer> pageOperation = H.pageOperation(page, limit, total);
		
		List<TableGoods> selectPage = tableGoodsMapper.selectPage(record, pageOperation.get("start"), pageOperation.get("end"), sidx, order);
		pageInfo.setList(selectPage);
		pageInfo.setPageNum(page);
		pageInfo.setPages(pageOperation.get("pages"));
		pageInfo.setTotal(total);
		
		return pageInfo;
	}

	@Override
	public Integer add(TableGoods record) throws Exception {
		int insert = tableGoodsMapper.insertSelective(record);
		return insert;
	}

	@Override
	public TableGoods select(String goodsId) throws Exception {
		TableGoods selectByPrimaryKey = tableGoodsMapper.selectByPrimaryKey(goodsId);
		return selectByPrimaryKey;
	}

	@Override
	public void modify(TableGoods record) throws Exception {
		tableGoodsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public boolean isExist(String goodsId) throws Exception {
		TableGoods record = new TableGoods();
		boolean res = false;
		record.setGoodsId(goodsId);
		int count = tableGoodsMapper.selectCount(record);
		if(count > 0){
			res = true;
		}
		return res;
	}

	@Override
	public Integer deleteByIds(List<String> goodsIds) throws Exception {
		Integer count = tableGoodsMapper.deleteByIds(goodsIds);
		return count;
	}

	@Override
	public PageData<TableGoods> likeSelect(String paramName, String paramValue) throws Exception {
		PageData<TableGoods> pagInfo = new PageData<>();
		List<TableGoods> likeSelect = new ArrayList<>();

		//对传参进行解析
		paramValue = paramValue + '%';
		if("goodsName".equals(paramName)){
			likeSelect = tableGoodsMapper.likeSelect(null, paramValue);
		}else if("goodsId".equals(paramName)){
			likeSelect = tableGoodsMapper.likeSelect(paramValue, null);
		}
		pagInfo.setList(likeSelect);
		pagInfo.setPageNum(1);
		pagInfo.setPages(1);
		pagInfo.setTotal(likeSelect.size());
		
		return pagInfo;
	}

}
