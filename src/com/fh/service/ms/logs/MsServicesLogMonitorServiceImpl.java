package com.fh.service.ms.logs;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.logs.MsServicesLogMonitor;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.util.PageData;

/**
 * 服务注册
 * 
 * @author Jes
 * @date 2016-11-04
 */
@Service("msServicesLogMonitorService")
public class MsServicesLogMonitorServiceImpl implements MsServicesLogMonitorService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("MsServicesLogMonitorMapper.save", pd);
	}
	
	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveObj(MsServicesLogMonitor monitor) throws Exception {
		dao.save("MsServicesLogMonitorMapper.save", monitor);
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteDateBefore(PageData pd) throws Exception {
		dao.delete("MsServicesLogMonitorMapper.deleteDateBefore", pd);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MsServicesReg> listAll(MsServicesReg pd) throws Exception {
		return (List<MsServicesReg>) dao.findForList("MsServicesLogMonitorMapper.listAll", pd);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> datalistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("MsServicesLogMonitorMapper.datalistPage", page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getMergeLogs(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MsServicesLogMonitorMapper.getMergeLogs", pd);
	}
	
}
