/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: MsServicesSecurityServiceImpl.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月27日 下午7:30:36
 * @version: V1.0  
 */
package com.fh.service.ms.logs;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.util.PageData;

/**
 * @author Jes
 *
 */
@Service("msServicesSecurityService")
public class MsServicesSecurityServiceImpl implements MsServicesSecurityService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public PageData getByUserServiceName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MsServicesSecurityMapper.getByUserServiceName", pd);
	}

	@Override
	public MsServicesReg getByServicename(PageData pd) throws Exception {
		return (MsServicesReg) dao.findForObject("MsServicesRegMapper.findByName", pd);
	}

	@Override
	public PageData getUserroleByName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("MsServicesSecurityMapper.getUserroleByName", pd);
	}
	
}
