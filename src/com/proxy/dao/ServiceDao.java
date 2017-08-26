/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: ServiceDao.java
 * @Prject: mapone
 * @author: Jes  
 * @date: 2016年11月12日 下午5:45:50
 * @version: V1.0  
 */
package com.proxy.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.entity.ms.services.MsServicesReg;
import com.fh.service.ms.logs.MsServicesSecurityService;
import com.fh.service.ms.services.MsServicesRegService;
import com.fh.util.PageData;

/**
 * @author Jes
 *
 */
@Service("serviceDao")
public class ServiceDao{
	
	@Resource(name="msServicesRegService")
	private MsServicesRegService msServicesRegService;
	
	@Resource(name="msServicesSecurityService")
	private MsServicesSecurityService msServicesSecurityService;
	
	public List<MsServicesReg> queryAllService() throws Exception{
//		msServicesRegService = new MsServicesRegServiceImpl(); 
		return msServicesRegService.listAll(new MsServicesReg());
		
	}
	
	public MsServicesReg queryByName(String servicename) {
		PageData pd = new PageData();
		pd.put("servicename", servicename);
		MsServicesReg servicesReg = null;
		try {
			servicesReg = msServicesSecurityService.getByServicename(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return servicesReg;
	}

}
