package com.checkOut.businessFunction.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.checkOut.businessFunction.model.Table;

import java.util.List;
import java.util.Map;

@Repository
public interface GeneratorMapper {
	/**
	 * MYSQL多条件可分页查询表信息
	 * @param record - 表信息条件
	 * @return List<Map<String, Object>> - 表信息集合
	 */
	List<Map<String, Object>> queryListMysql(Table record);

	/**
	 * 根据schema与表名查询表信息
	 * @param tableSchema
	 * @param tableName - 表名称
	 * @return Map<String, String> - 表信息
	 */
	Map<String, String> queryTable(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

	/**
	 * 根据schema与表名查询该表的字段信息
	 * @param tableSchema
	 * @param tableName - 表名称
	 * @return List<Map<String, String>> - 字段信息集合
	 */
	List<Map<String, String>> queryColumns(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);
}