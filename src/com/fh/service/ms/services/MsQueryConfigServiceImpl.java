package com.fh.service.ms.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsQueryConfig;
import com.fh.util.PageData;

/**
 * 查询配置服务层
 * @author fujl 
 * @date 2016-11-20
 */
@Service("msQueryConfigService")
public class MsQueryConfigServiceImpl implements MsQueryConfigService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsQueryConfig> listAll(MsQueryConfig pd) throws Exception {
		return (List<MsQueryConfig>)dao.findForList("MsQueryConfigMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsQueryConfigMapper.datalistPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		
		dao.save("MsQueryConfigMapper.save", pd);
	}

	@Override
	public void delete(Integer id) throws Exception {
		dao.delete("MsQueryConfigMapper.delete", id);
	}

	@Override
	public void deleteByCombId(Integer id) throws Exception {
		dao.delete("MsQueryConfigMapper.deleteByCombId", id);
	}
	
	@Override
	public void update(PageData pd) throws Exception {
		dao.update("MsQueryConfigMapper.edit", pd);
	}

	@Override
	public void updateCombIdByZmemo(PageData pd) throws Exception {
		dao.update("MsQueryConfigMapper.updateCombIdByZmemo", pd);
	}
	
	
}
