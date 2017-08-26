package com.fh.service.ms.stat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.ms.stat.MsServicesStat;
import com.fh.util.PageData;

@Service("msStatServices")
public class MsStatServicesimpl implements MsStatServices {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listStatServices(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("MsStatServicesMapper.listServicesStat", pd);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listStatUsers(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("MsStatServicesMapper.listUserStat", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listStatServiceSuccess(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("MsStatServicesMapper.listServiceSuccessStat", pd);
	}


}
