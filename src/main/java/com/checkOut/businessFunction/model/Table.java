package com.checkOut.businessFunction.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 表数据实体
 * 
 * @author lenovo
 *
 */
public class Table {
    /**
     * 表的名称
     */
    private String tableName;
    /**
     * 表的备注
     */
	private String comments;
    /**
     * 表的主键
     */
	private List<Column> pk = new ArrayList<>();
    /**
     * 表的列名(不包含主键)
     */
	private List<Column> columns;
    /**
     * 类名(第一个字母大写)，如：sys_user => SysUser
     */
	private String className;
    /**
     * 类名(第一个字母小写)，如：sys_user => sysUser
     */
	private String classname;
    /**
     * 表前缀(例:jrtz_app)
     */
	private String tableSchema;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<Column> getPk() {
		return pk;
	}

	public void setPk(List<Column> pk) {
		this.pk = pk;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

}
