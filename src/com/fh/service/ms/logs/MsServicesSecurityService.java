/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: MsServicesSecurityService.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月27日 下午7:28:20
 * @version: V1.0  
 */
package com.fh.service.ms.logs;

import com.fh.entity.ms.services.MsServicesReg;
import com.fh.util.PageData;

/**
 * @author Jes
 *
 */
public interface MsServicesSecurityService {

	public PageData getByUserServiceName(PageData pd) throws Exception;
	
	public MsServicesReg getByServicename(PageData pd) throws Exception;
	
	public PageData getUserroleByName(PageData pd) throws Exception;
	
}
