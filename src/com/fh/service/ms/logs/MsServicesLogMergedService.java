/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: MsServicesLogMergedService.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月26日 下午6:39:05
 * @version: V1.0  
 */
package com.fh.service.ms.logs;

import java.util.List;

import com.fh.entity.ms.logs.MsServicesLogMerged;
import com.fh.entity.ms.logs.MsServicesLogMergedPK;

/**
 * @author Jes
 *		
 */
public interface MsServicesLogMergedService {
	
	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveObj(MsServicesLogMerged logMerged) throws Exception;
	
	/**
	 * 通过ID 查询
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void getById(MsServicesLogMergedPK pkid) throws Exception;
	
	/**
	 * 根据ID 更新
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateById(MsServicesLogMerged logMerged) throws Exception;
	
	/**
	 * 批量更新或者删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveOrUpdateBatch(List<MsServicesLogMerged> list) throws Exception;
	
}
