package com.checkOut.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.checkOut.common.model.Column;
import com.checkOut.common.model.Table;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 * 
 */
public class GenUtils {

    public enum VelocityTemplateSuffixNameType {
        MAPPER_JAVA("Mapper.java.vm"),
        ENTITY_JAVA("Entity.java.vm"),
        SERVICE_JAVA("Service.java.vm"),
        SERVICE_IMPL_JAVA("ServiceImpl.java.vm"),
        MAPPER_XML("Mapper.xml.vm");

        private String value;

        VelocityTemplateSuffixNameType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("templates/vm/Mapper.java.vm");
		templates.add("templates/vm/Entity.java.vm");
		templates.add("templates/vm/Service.java.vm");
		templates.add("templates/vm/ServiceImpl.java.vm");
		templates.add("templates/vm/Mapper.xml.vm");
		return templates;
	}
	
	/**
	 * 生成代码
	 * @throws Exception 
	 */
	public static void generatorCode(Map<String, String> table,
			List<Map<String, String>> columns, ZipOutputStream zip) throws Exception{
		//配置信息
		Configuration config = getConfig();
		
		//表信息
		Table info = new Table();
		info.setTableName(table.get("TABLE_NAME"));
		info.setComments(table.get("TABLE_COMMENT"));
		//表名转换成Java类名
		String className = tableToJava(info.getTableName(), config.getString("TABLE_PREFIX"));
		info.setClassName(className);
		info.setClassname(StringUtils.uncapitalize(className));
		
		//列信息
		List<Column> columsList = new ArrayList<>();
		Set<String> imports = new HashSet<>();
		for(Map<String, String> column : columns){
			Column newColumn = new Column();
			newColumn.setColumnName(column.get("COLUMN_NAME"));
			newColumn.setDataType(convertDataType(column.get("DATA_TYPE")));
			newColumn.setComments(column.get("COLUMN_COMMENT"));
			newColumn.setExtra(column.get("EXTRA"));
			
			//列名转换成Java属性名
			String attrName = columnToJava(newColumn.getColumnName());
			newColumn.setAttrName(attrName);
			newColumn.setAttrname(StringUtils.uncapitalize(attrName));
			
			//列的数据类型，转换成Java类型
			String absAttrType = config.getString(newColumn.getDataType(), "UNKNOW_TYPE");
			String attrType = "";
			if(absAttrType.lastIndexOf(".") != -1){
				attrType = absAttrType.substring(absAttrType.lastIndexOf(".") + 1);
			}
			
			//如果字段类型为clob 或者 blob
            String clob = "clob", blob = "blob";
            if (clob.equals(column.get("DATA_TYPE")) || blob.equals(column.get("DATA_TYPE"))) {
                attrType = "Byte[]";
			}
			
			//非java.lang包需要引包
			newColumn.setAttrType(attrType);
			if(!absAttrType.contains("java.lang")){
				imports.add(absAttrType);
			}
			
			//是否主键
			if("PRI".equalsIgnoreCase(column.get("COLUMN_KEY"))){
				info.getPk().add(newColumn);
			}
			
			columsList.add(newColumn);
		}
		info.setColumns(columsList);
		
		
		//设置velocity资源加载器
		Properties prop = new Properties();  
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");  
		Velocity.init(prop);
		
		//封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", info.getTableName());
		map.put("comments", info.getComments());
		map.put("pk", info.getPk());
		map.put("className", info.getClassName());
		map.put("classname", info.getClassname());
		map.put("pathName", info.getClassname().toLowerCase());
		map.put("columns", info.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("imports", imports);
        VelocityContext context = new VelocityContext(map);
        
        //获取模板列表
		List<String> templates = getTemplates();
		for(String template : templates){
			//渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);
			
			try {
				//添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, info.getClassName(), config.getString("package"))));  
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new Exception("渲染模板失败，表名：" + info.getTableName(), e);
			}
		}
	}
	
	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}
	
	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}
	
	/**
	 * 获取配置信息
	 * @throws Exception 
	 */
	public static Configuration getConfig() throws Exception{
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new Exception("获取配置文件失败，", e);
		}
	}
	
	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName){
		String packagePath = "main" + File.separator + "java" + File.separator;
		if(StringUtils.isNotBlank(packageName)){
			packagePath += packageName.replace(".", File.separator) + File.separator;
		}

        if (template.contains(VelocityTemplateSuffixNameType.MAPPER_JAVA.getValue())) {
            return packagePath + "mapper" + File.separator + File.separator + className + "Mapper.java";
		}

        if (template.contains(VelocityTemplateSuffixNameType.ENTITY_JAVA.getValue())) {
            return packagePath + "model" + File.separator + File.separator + className + ".java";
		}

        if (template.contains(VelocityTemplateSuffixNameType.SERVICE_JAVA.getValue())) {
            return packagePath + "service" + File.separator + File.separator + className + "Service.java";
		}

        if (template.contains(VelocityTemplateSuffixNameType.SERVICE_IMPL_JAVA.getValue())) {
            return packagePath + "service" + File.separator + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

        if (template.contains(VelocityTemplateSuffixNameType.MAPPER_XML.getValue())) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + File.separator + className + "Mapper.xml";
		}
		
		/*
		
		if(template.contains("Controller.java.vm")){
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}

		if(template.contains("list.html.vm")){
			return "main" + File.separator + "resources" + File.separator + "static" + File.separator
					+ "generator" + File.separator + className.toLowerCase() + ".html";
		}
		
		if(template.contains("list.js.vm")){
			return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "public" + File.separator
					+ "js" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
		}

		if(template.contains("menu.sql.vm")){
			return className.toLowerCase() + "_menu.sql";
		}*/
		
		return null;
	}

	/**
	 * 将数据库查询出的列dataType转成高可用的的jdbcType
	 * @param dataType
	 * @return
	 */
	public static String convertDataType(String dataType){
		switch (dataType) {
		case "double":
			dataType = "decimal";
			break;
		case "float":
			dataType = "decimal";
			break;
		case "number":
			dataType = "decimal";
			break;
		case "datetime":
			dataType = "timestamp";
			break;
		case "date":
			dataType = "timestamp";
			break;
		default:
			break;
		}
		return dataType;
	}
}
