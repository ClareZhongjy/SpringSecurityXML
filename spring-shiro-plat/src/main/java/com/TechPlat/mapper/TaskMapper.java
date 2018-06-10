package com.TechPlat.mapper;

import java.util.List;
import java.util.Map;

import com.TechPlat.model.Task;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
  * InnoDB free: 7168 kB Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2018-05-27
 */
public interface TaskMapper extends BaseMapper<Task> {
	
	List<Map<String, Object>> selectTaskPage(Pagination page, Map<String, Object> params);

}