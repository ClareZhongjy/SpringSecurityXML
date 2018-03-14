package com.frame.common.domain;

import java.util.List;

public class TableDO {
	private String tableName;
	
	private String comments;
	
	private ColumnDO pk;
	
	private List<ColumnDO> columns;
	//类名(第一个字母大写)，如：sys_user => SysUser
	private String className;
	//类名(第一个字母小写)，如：sys_user => sysUser
	private String classname;
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the pk
	 */
	public ColumnDO getPk() {
		return pk;
	}
	/**
	 * @param pk the pk to set
	 */
	public void setPk(ColumnDO pk) {
		this.pk = pk;
	}
	/**
	 * @return the columns
	 */
	public List<ColumnDO> getColumns() {
		return columns;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(List<ColumnDO> columns) {
		this.columns = columns;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the classname
	 */
	public String getClassname() {
		return classname;
	}
	/**
	 * @param classname the classname to set
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	
}
