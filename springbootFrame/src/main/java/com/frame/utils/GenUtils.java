package com.frame.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.frame.common.config.Constant;
import com.frame.common.domain.ColumnDO;
import com.frame.common.domain.TableDO;

public class GenUtils {

	public static List<String> getTemplates() {

		List<String> templates = new ArrayList<String>();
		templates.add("templates/common/generator/Mapper.xml.vm");

		return templates;

	}

	public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns, ZipOutputStream zip)
			throws Exception {
		// 配置信息
		Configuration config = getConfig();
		TableDO tableDO = new TableDO();
		tableDO.setTableName(table.get("tableName"));
		tableDO.setComments(table.get("tableComment"));
		String className = tableToJava(tableDO.getTableName(), config.getString("tablePrefix"),
				config.getString("autoRemovePre"));
		tableDO.setClassName(className);
		tableDO.setClassname(StringUtils.uncapitalize(className));
		
		List<ColumnDO> colList = new ArrayList<ColumnDO>();
		for(Map<String,String> col:columns){
			ColumnDO colDO = new ColumnDO();
			colDO.setColumnName(col.get("columnName"));
			colDO.setDataType(col.get("dataType"));
			colDO.setComments(col.get("columnComment"));
			colDO.setExtra(col.get("extra"));
			
			String attrName = columnToJava(colDO.getColumnName());
			colDO.setAttrName(attrName);
			colDO.setAttrname(StringUtils.uncapitalize(attrName));
		
			String attrType = config.getString(colDO.getDataType());
			colDO.setAttrType(attrType);
			
            //是否主键
            if ("PRI".equalsIgnoreCase(col.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(colDO);
            }

            colList.add(colDO);
        }
        tableDO.setColumns(colList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        map.put("pathName", config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1));
        map.put("columns", tableDO.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableDO.getClassname(), tableDO.getClassName(), config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new Exception("渲染模板失败，表名：" + tableDO.getTableName(), e);
            }
        }
	}

	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {
		if (Constant.AUTO_REOMVE_PRE.equals(autoRemovePre)) {
			tableName = tableName.substring(tableName.indexOf("_") + 1);
		}
		if (StringUtils.isNotBlank(tablePrefix)) {
			tableName = tableName.replace(tablePrefix, "");
		}

		return columnToJava(tableName);
	}

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

	private static Configuration getConfig() throws Exception {

		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			throw new Exception("获取配置文件失败，", e);
		}
	}

	public static String getFileName(String template, String classname, String className, String packageName) {
		String packagePath = "main" + File.separator + "java" + File.separator;
		if (StringUtils.isNotBlank(packageName)) {
			packagePath += packageName.replace(".", File.separator) + File.pathSeparator;
		}
		if (template.contains("domain.java.vm")) {
			return packagePath + "domain" + File.separator + className + "DO.java";
		}
		if (template.contains("Dao.java.vm")) {
			return packagePath + "dao" + File.separator + className + "Dao.java";
		}
		if (template.contains("Service.java.vm")) {
			return packagePath + "service" + File.separator + className + "Service.java";
		}

		if (template.contains("ServiceImpl.java.vm")) {
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

		if (template.contains("Controller.java.vm")) {
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}

		if (template.contains("Mapper.xml.vm")) {
			return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + packageName
					+ File.separator + className + "Mapper.xml";
		}

		if (template.contains("list.html.vm")) {
			return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
					+ File.separator + classname + File.separator + classname + ".html";
			// + "modules" + File.separator + "generator" + File.separator +
			// className.toLowerCase() + ".html";
		}
		if (template.contains("add.html.vm")) {
			return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
					+ File.separator + classname + File.separator + "add.html";
		}
		if (template.contains("edit.html.vm")) {
			return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
					+ File.separator + classname + File.separator + "edit.html";
		}

		if (template.contains("list.js.vm")) {
			return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
					+ File.separator + "appjs" + File.separator + packageName + File.separator + classname
					+ File.separator + classname + ".js";
			// + "modules" + File.separator + "generator" + File.separator +
			// className.toLowerCase() + ".js";
		}
		if (template.contains("add.js.vm")) {
			return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
					+ File.separator + "appjs" + File.separator + packageName + File.separator + classname
					+ File.separator + "add.js";
		}
		if (template.contains("edit.js.vm")) {
			return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
					+ File.separator + "appjs" + File.separator + packageName + File.separator + classname
					+ File.separator + "edit.js";
		}

		// if(template.contains("menu.sql.vm")){
		// return className.toLowerCase() + "_menu.sql";
		// }

		return null;
	}

}
