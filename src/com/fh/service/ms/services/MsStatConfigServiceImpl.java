package com.fh.service.ms.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsQueryConfig;
import com.fh.entity.ms.services.MsStatConfig;
import com.fh.util.PageData;

/**
 * 查询统计配置服务层
 * @author fujl 
 * @date 2016-11-20
 */
@Service("msStatConfigService")
public class MsStatConfigServiceImpl implements MsStatConfigService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsStatConfig> listAll(MsStatConfig pd) throws Exception {
		return (List<MsStatConfig>)dao.findForList("MsStatConfigMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsStatConfigMapper.datalistPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("MsStatConfigMapper.save", pd);
	}

	@Override
	public void delete(Integer id) throws Exception {
		dao.delete("MsStatConfigMapper.delete", id);
	}
	
	@Override
	public void update(PageData pd) throws Exception{
		dao.update("MsStatConfigMapper.edit", pd);
	}

	@Override
	public void setEnble(PageData pd) throws Exception{
		dao.update("MsStatConfigMapper.setEnble", pd);
	}
	
	@Override
	public MsStatConfig findByTypeAndName(PageData pd) throws Exception {
		return (MsStatConfig)dao.findForObject("MsStatConfigMapper.findByTypeAndName", pd);
	}

	@Override
	public MsStatConfig findById(PageData pd) throws Exception {
		return (MsStatConfig)dao.findForObject("MsStatConfigMapper.findById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByStaticsName(String staticsname)
			throws Exception {
		return (List<PageData>)dao.findForList("MsStatConfigMapper.findByStaticsName",staticsname);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> configlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsStatConfigMapper.configlistPage", page);
	}
}
