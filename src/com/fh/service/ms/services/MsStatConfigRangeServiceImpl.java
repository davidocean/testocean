package com.fh.service.ms.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsStatConfigRange;
import com.fh.util.PageData;

@Service("msStatConfigRangeService")
public class MsStatConfigRangeServiceImpl  implements MsStatConfigRangeService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsStatConfigRange> listAll(MsStatConfigRange pd) throws Exception {
		return (List<MsStatConfigRange>)dao.findForList("MsStatConfigRangeMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsStatConfigRangeMapper.datalistPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("MsStatConfigRangeMapper.save", pd);
	}

	@Override
	public void delete(Integer id) throws Exception {
		dao.delete("MsStatConfigRangeMapper.delete", id);
	}
	
	@Override
	public void deleteByStaticsid(Integer id) throws Exception {
		dao.delete("MsStatConfigRangeMapper.deleteByStaticsid", id);
	}
	
	@Override
	public void update(PageData pd) throws Exception{
		dao.update("MsStatConfigRangeMapper.edit", pd);
	}

	@Override
	public MsStatConfigRange findById(PageData pd) throws Exception {
		return (MsStatConfigRange)dao.findForObject("MsStatConfigRangeMapper.findById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByConfigDetailId(Integer detailid)throws Exception {
		return (List<PageData>)dao.findForList("MsStatConfigRangeMapper.findByConfigDetailId", detailid);
	}
}
