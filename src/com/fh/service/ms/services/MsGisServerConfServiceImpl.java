package com.fh.service.ms.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsGisServerConfig;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.util.PageData;

/**
 * gis服务器配置服务
 * @author ZhaiZhengqiang
 * @date 2016-11-04
 */
@Service("msGisServerConfService")
public class MsGisServerConfServiceImpl implements MsGisServerConfService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsGisServerConfig> listAll(MsGisServerConfig pd) throws Exception {
		return (List<MsGisServerConfig>)dao.findForList("MsGisServerConfigMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsGisServerConfigMapper.datalistPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("MsGisServerConfigMapper.save", pd);
	}

	@Override
	public void delete(Integer id) throws Exception {
		dao.delete("MsGisServerConfigMapper.delete", id);
	}
	
	@Override
	public void update(PageData pd) throws Exception{
		dao.update("MsGisServerConfigMapper.edit", pd);
	}

}
