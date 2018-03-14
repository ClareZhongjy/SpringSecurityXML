package com.frame.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.frame.blog.domain.ContentDO;

@Mapper
public interface ContentDao {
	ContentDO get(Long cid);
	
	List<ContentDO> list(Map<String,Object> map);

	int count(Map<String, Object> map);
}
