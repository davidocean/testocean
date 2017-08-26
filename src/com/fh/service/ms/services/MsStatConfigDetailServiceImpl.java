package com.fh.service.ms.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsStatConfigDetail;
import com.fh.util.PageData;

@Service("msStatConfigDetailService")
public class MsStatConfigDetailServiceImpl implements MsStatConfigDetailService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsStatConfigDetail> listAll(MsStatConfigDetail pd) throws Exception {
		return (List<MsStatConfigDetail>)dao.findForList("MsStatConfigDetailMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsStatConfigDetailMapper.datalistPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("MsStatConfigDetailMapper.save", pd);
	}

	@Override
	public void delete(Integer id) throws Exception {
		dao.delete("MsStatConfigDetailMapper.delete", id);
	}
	
	@Override
	public void deleteByStaticsid(Integer id) throws Exception {
		dao.delete("MsStatConfigDetailMapper.deleteByStaticsid", id);
	}
	
	
	@Override
	public void update(PageData pd) throws Exception{
		dao.update("MsStatConfigDetailMapper.edit", pd);
	}

	@Override
	public void updateStaticsIdByZmemo(PageData pd) throws Exception {
		dao.update("MsStatConfigDetailMapper.updateStaticsIdByZmemo", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByStaticsId(Integer staticsid) throws Exception {
		return (List<PageData>)dao.findForList("MsStatConfigDetailMapper.findByStaticsId", staticsid);
	}

	@Override
	public MsStatConfigDetail findById(PageData pd) throws Exception {
		return (MsStatConfigDetail)dao.findForList("MsStatConfigDetailMapper.findById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByStaticidName(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("MsStatConfigDetailMapper.findByStaticidName", pd);
	}
}
