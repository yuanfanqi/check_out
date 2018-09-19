package com.checkOut.common.service.businessFunction.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.checkOut.common.mapper.businessFunction.GoodsStoreMapper;
import com.checkOut.common.mapper.businessFunction.TableGoodsMapper;
import com.checkOut.common.model.businessFunction.GoodsStore;
import com.checkOut.common.model.businessFunction.TableGoods;
import com.checkOut.common.model.commonModel.GoodsModel;
import com.checkOut.common.model.commonModel.PageData;
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
	@Autowired
	private GoodsStoreMapper goodsStoreMapper;

	@Override
	public PageData<GoodsModel> selectPage(TableGoods record, Integer page, Integer limit, String sidx,
			String order) throws Exception {
		PageData<GoodsModel> pageInfo = new PageData<>();
		int total = tableGoodsMapper.selectCount(record);
		List<GoodsModel> list = new ArrayList<>();
		//分页参数处理
		Map<String, Integer> pageOperation = H.pageOperation(page, limit, total);
		
		List<TableGoods> selectPage = tableGoodsMapper.selectPage(record, pageOperation.get("start"), pageOperation.get("end"), sidx, order);
		//遍历selectPage，将其加上库存、最小保质期等数据
		for (TableGoods tableGoods : selectPage) {
			GoodsStore goodsStore = new GoodsStore();
			goodsStore.setGoodsId(tableGoods.getGoodsId());
			List<GoodsStore> storeList = goodsStoreMapper.select(goodsStore);
			Integer goodsNum = 0;
			Date exp = null;
			for (GoodsStore goodsStore2 : storeList) {
				goodsNum += goodsStore2.getGoodsNum();
				if(null == exp || exp.after(goodsStore2.getExp())){
					exp = goodsStore2.getExp();
				}
			}
			GoodsModel goodsModel = new GoodsModel();
			goodsModel.setGoodsBid(tableGoods.getGoodsBid());
			goodsModel.setExp(exp);
			goodsModel.setGoodsId(tableGoods.getGoodsId());
			goodsModel.setGoodsName(tableGoods.getGoodsName());
			goodsModel.setGoodsNum(goodsNum);
			goodsModel.setGoodsPrice(tableGoods.getGoodsPrice());
			goodsModel.setGoodsType(tableGoods.getGoodsType());
			goodsModel.setUpdateTime(tableGoods.getUpdateTime());
			list.add(goodsModel);
		}
		pageInfo.setList(list);
		pageInfo.setPageNum(page);
		pageInfo.setPages(pageOperation.get("pages"));
		pageInfo.setTotal(total);
		
		return pageInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer add(TableGoods record, GoodsStore goodsStore) throws Exception {
		int insert = tableGoodsMapper.insertSelective(record);
		//库存保质期等数据入库
		int insertSelective = goodsStoreMapper.insertSelective(goodsStore);
		return insert + insertSelective;
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
