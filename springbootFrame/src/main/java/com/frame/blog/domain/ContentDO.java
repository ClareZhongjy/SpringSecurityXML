package com.frame.blog.domain;

import java.io.Serializable;
import java.util.Date;

public class ContentDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//
	private Long cid;
	//标题
	private String title;
	//
	private String slug;
	//创建人id
	private Long created;
	//最近修改人id
	private Long modified;
	//内容
	private String content;
	//类型
	private String type;
	//标签
	private String tags;
	//分类
	private String categories;
	//
	private Integer hits;
	//评论数量
	private Integer commentsNum;
	//开启评论
	private Integer allowComment;
	//允许ping
	private Integer allowPing;
	//允许反馈
	private Integer allowFeed;
	//状态
	private Integer status;
	//作者
	private String author;
	//创建时间
	private Date gtmCreate;
	//修改时间
	private Date gtmModified;
	/**
	 * @return the cid
	 */
	public Long getCid() {
		return cid;
	}
	/**
	 * @param cid the cid to set
	 */
	public void setCid(Long cid) {
		this.cid = cid;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}
	/**
	 * @param slug the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	/**
	 * @return the created
	 */
	public Long getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Long created) {
		this.created = created;
	}
	/**
	 * @return the modified
	 */
	public Long getModified() {
		return modified;
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(Long modified) {
		this.modified = modified;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the categories
	 */
	public String getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(String categories) {
		this.categories = categories;
	}
	/**
	 * @return the hits
	 */
	public Integer getHits() {
		return hits;
	}
	/**
	 * @param hits the hits to set
	 */
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	/**
	 * @return the commentsNum
	 */
	public Integer getCommentsNum() {
		return commentsNum;
	}
	/**
	 * @param commentsNum the commentsNum to set
	 */
	public void setCommentsNum(Integer commentsNum) {
		this.commentsNum = commentsNum;
	}
	/**
	 * @return the allowComment
	 */
	public Integer getAllowComment() {
		return allowComment;
	}
	/**
	 * @param allowComment the allowComment to set
	 */
	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}
	/**
	 * @return the allowPing
	 */
	public Integer getAllowPing() {
		return allowPing;
	}
	/**
	 * @param allowPing the allowPing to set
	 */
	public void setAllowPing(Integer allowPing) {
		this.allowPing = allowPing;
	}
	/**
	 * @return the allowFeed
	 */
	public Integer getAllowFeed() {
		return allowFeed;
	}
	/**
	 * @param allowFeed the allowFeed to set
	 */
	public void setAllowFeed(Integer allowFeed) {
		this.allowFeed = allowFeed;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the gtmCreate
	 */
	public Date getGtmCreate() {
		return gtmCreate;
	}
	/**
	 * @param gtmCreate the gtmCreate to set
	 */
	public void setGtmCreate(Date gtmCreate) {
		this.gtmCreate = gtmCreate;
	}
	/**
	 * @return the gtmModified
	 */
	public Date getGtmModified() {
		return gtmModified;
	}
	/**
	 * @param gtmModified the gtmModified to set
	 */
	public void setGtmModified(Date gtmModified) {
		this.gtmModified = gtmModified;
	}
	
	

}
