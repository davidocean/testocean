package com.fh.service.ms.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsQueryConfigCombination;
import com.fh.util.PageData;


/**
 * 查询配置组合服务层
 * @author fujl 
 * @date 2016-12-31
 */
@Service("msQueryConfigCombinationService")
public class MsQueryConfigCombinationServiceImpl implements MsQueryConfigCombinationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsQueryConfigCombination> listAll(MsQueryConfigCombination pd) throws Exception {
		return (List<MsQueryConfigCombination>)dao.findForList("MsQueryConfigCombinationMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsQueryConfigCombinationMapper.datalistPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		
		dao.save("MsQueryConfigCombinationMapper.save", pd);
	}

	@Override
	public void delete(Integer id) throws Exception {
		dao.delete("MsQueryConfigCombinationMapper.delete", id);
	}
	
	@Override
	public void update(PageData pd) throws Exception{
		dao.update("MsQueryConfigCombinationMapper.edit", pd);
	}

	@Override
	public void updateSortCodeById(PageData pd) throws Exception{
		dao.update("MsQueryConfigCombinationMapper.updateSortCodeById", pd);
	}
	
	
	@Override
	public MsQueryConfigCombination findByName(String name) throws Exception {
		return (MsQueryConfigCombination)dao.findForObject("MsQueryConfigCombinationMapper.findByName", name);
	}
}
