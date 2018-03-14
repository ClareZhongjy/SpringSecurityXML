package com.frame.common.domain;

public class ColumnDO {
	// 列名
	private String columnName;
	// 列名类型
	private String dataType;
	// 列名备注
	private String comments;

	// 属性名称(第一个字母大写)，如：user_name => UserName
	private String attrName;
	// 属性名称(第一个字母小写)，如：user_name => userName
	private String attrname;
	// 属性类型
	private String attrType;
	// auto_increment
	private String extra;
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
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
	 * @return the attrName
	 */
	public String getAttrName() {
		return attrName;
	}
	/**
	 * @param attrName the attrName to set
	 */
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	/**
	 * @return the attrname
	 */
	public String getAttrname() {
		return attrname;
	}
	/**
	 * @param attrname the attrname to set
	 */
	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}
	/**
	 * @return the attrType
	 */
	public String getAttrType() {
		return attrType;
	}
	/**
	 * @param attrType the attrType to set
	 */
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	/**
	 * @return the extra
	 */
	public String getExtra() {
		return extra;
	}
	/**
	 * @param extra the extra to set
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	
}
