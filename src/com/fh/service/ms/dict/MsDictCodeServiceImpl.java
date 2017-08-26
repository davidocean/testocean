package com.fh.service.ms.dict;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.dict.MsDictCode;
import com.fh.util.PageData;
/**
 * 字典代码
 * @author ZhaiZhengqiang
 * @date 2016-11-04
 */
@Service("msDictCodeService")
public class MsDictCodeServiceImpl implements MsDictCodeService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MsDictCodeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MsDictCode> listAll(MsDictCode pd)throws Exception{
		return (List<MsDictCode>)dao.findForList("MsDictCodeMapper.listAll", pd);
	}
}
