package com.checkOut.common.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkOut.common.mapper.GeneratorMapper;
import com.checkOut.common.model.Table;
import com.checkOut.common.model.commonModel.PageData;
import com.checkOut.utils.GenUtils;
import com.checkOut.utils.H;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author investody E-mail:admin@investdoday.com.cn
 * @date 创建时间：2017年9月7日 下午5:03:27
 * @version 1.0
 * @since 
 */
@Service
public class SysGeneratorService {
	@Autowired
	private GeneratorMapper generatorMapper;
	
	/**
	 * 分页查询表信息
	 * 
	 * @param record
	 *            - 表实体条件
	 * @return PageInfo<Map<String, Object>> - 分页信息
	 */
	public PageData<Map<String, Object>> queryList(Table record, Integer page, Integer limit) {
		List<Map<String, Object>> datas = null;
		PageData<Map<String, Object>> pageData = new PageData<>();
		
		int total = 0;
		if (H.isNotBlank(record)) {
			datas = generatorMapper.queryListMysql(record);
			total = datas.size();
			if (H.isNotBlank(datas)) {
				//分页参数处理
				Integer start = (page - 1) * limit;
				Integer end = page * limit;
				if (end >= total) {
					datas = datas.subList(start, total);
				} else if (start >= total) {
					datas = new ArrayList<Map<String, Object>>();
				} else {
					datas = datas.subList(start, end);
				}
			}
		}
		if (0 == total % limit) {
			pageData.setPages(total / limit);
		} else {
			pageData.setPages(total / limit == 0 ? 1 : total / limit + 1);
		}
		pageData.setPageNum(page);
		pageData.setList(datas);
		pageData.setTotal(total);
		return pageData;
	}

	/**
	 * 根据Schema与表名生成该表的代码并打包成zip文件
	 * @param tableName - 表名称
	 * @return byte[] - 字节
	 * @throws Exception
	 */
	public byte[] generateCode(String tableName) throws Exception{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		//查询表信息
		Map<String, String> table;
		//查询列信息
		List<Map<String, String>> columns;
		table = generatorMapper.queryTable(tableName);
		columns = generatorMapper.queryColumns(tableName);

		//生成代码
		GenUtils.generatorCode(table, columns, zip);
		
		IOUtils.closeQuietly(zip);
		
		return outputStream.toByteArray();
	}
}
