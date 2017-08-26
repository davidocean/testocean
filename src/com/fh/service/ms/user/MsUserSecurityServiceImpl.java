package com.fh.service.ms.user;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ms.user.MsUserSecurity;
import com.fh.util.PageData;

/**
 * 资源服务用户权限(实现)
 * @author fujl
 * @date 2016-11-13 ++
 */
@Service("msUserSecurityService")
public class MsUserSecurityServiceImpl implements MsUserSecurityService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MsUserSecurityMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MsUserSecurityMapper.delete", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MsUserSecurity> listAll(MsUserSecurity pd) throws Exception {
		return (List<MsUserSecurity>)dao.findForList("MsUserSecurityMapper.listAll", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MsUserSecurityMapper.datalistPage", page);
	}
	
	/**
	 * 根据id查询列表
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findById(String id)throws Exception{
		return (List<PageData>)dao.findForList("MsUserSecurityMapper.findById", id);
	}

	/**
	 * 根据userid查询列表
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByUserId(String userid) throws Exception {
		return (List<PageData>)dao.findForList("MsUserSecurityMapper.findByUserId", userid);
	}

	/**
	 * 根据userid删除当前用户的授权信息
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteByUserId(String userid) throws Exception {
		dao.delete("MsUserSecurityMapper.deleteByUserId", userid);
	}
}
