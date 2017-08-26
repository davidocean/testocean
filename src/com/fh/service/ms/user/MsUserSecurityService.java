package com.fh.service.ms.user;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.user.MsUserSecurity;
import com.fh.util.PageData;

/**
 * 资源服务用户权限(接口)
 * @author fujl
 * @date 2016-11-13 ++
 */
public interface MsUserSecurityService {

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsUserSecurity> listAll(MsUserSecurity pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 根据id查询列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findById(String id)throws Exception;
	
	
	/**
	 * 根据userid查询列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByUserId(String userid)throws Exception;
	
	/**
	 * 根据userid删除授权信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteByUserId(String userid)throws Exception;
}
