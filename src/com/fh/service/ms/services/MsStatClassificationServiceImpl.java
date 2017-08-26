package com.fh.service.ms.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsStatClassification;
import com.fh.util.PageData;

@Service("msStatClassificationService")
public class MsStatClassificationServiceImpl implements MsStatClassificationService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsStatClassification> listAll(MsStatClassification pd) throws Exception {
		return (List<MsStatClassification>)dao.findForList("MsStatClassificationMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsStatClassificationMapper.datalistPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("MsStatClassificationMapper.save", pd);
	}

	@Override
	public void delete(Integer id) throws Exception {
		dao.delete("MsStatClassificationMapper.delete", id);
	}
	
	@Override
	public void update(PageData pd) throws Exception{
		dao.update("MsStatClassificationMapper.edit", pd);
	}

	@Override
	public MsStatClassification findByName(String name) throws Exception {
		return (MsStatClassification)dao.findForObject("MsStatClassificationMapper.findByName", name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByAll() throws Exception {
		return (List<PageData>)dao.findForList("MsStatClassificationMapper.findByAll", null);
	}
}
