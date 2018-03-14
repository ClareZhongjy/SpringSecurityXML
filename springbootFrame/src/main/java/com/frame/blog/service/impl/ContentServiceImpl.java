package com.frame.blog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.blog.dao.ContentDao;
import com.frame.blog.domain.ContentDO;
import com.frame.blog.service.ContextService;
import com.frame.utils.Query;

@Service
public class ContentServiceImpl implements ContextService {

	@Autowired
	private ContentDao bContentMapper;
	
	
	@Override
	public ContentDO get(Long cid) {
		
		return bContentMapper.get(cid);
	}

	@Override
	public List<ContentDO> list(Map<String, Object> map) {
		
		return bContentMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		
		return bContentMapper.count(map);
	}

}
