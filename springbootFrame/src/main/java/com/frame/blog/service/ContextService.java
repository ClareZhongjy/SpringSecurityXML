package com.frame.blog.service;

import java.util.List;
import java.util.Map;

import com.frame.blog.domain.ContentDO;
import com.frame.utils.Query;

public interface ContextService {
	ContentDO get(Long cid);
	
	List<ContentDO> list(Map<String,Object> map);

	int count(Map<String, Object> map);
}
