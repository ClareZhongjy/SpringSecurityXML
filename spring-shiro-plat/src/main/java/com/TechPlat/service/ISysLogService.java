package com.TechPlat.service;

import com.baomidou.mybatisplus.service.IService;
import com.TechPlat.commons.result.PageInfo;
import com.TechPlat.model.SysLog;

/**
 *
 * SysLog 表数据服务层接口
 *
 */
public interface ISysLogService extends IService<SysLog> {

    void selectDataGrid(PageInfo pageInfo);

}