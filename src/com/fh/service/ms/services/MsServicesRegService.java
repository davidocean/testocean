package com.fh.service.ms.services;

import java.util.List;

import com.fh.bean.folder.Service;
import com.fh.bean.result.ResultObj;
import com.fh.entity.Page;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.util.PageData;

/**
 * 服务注册
 * @author ZhaiZhengqiang
 * @date 2016-11-04
 */
public interface MsServicesRegService {

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsServicesReg> listAll(MsServicesReg pd)throws Exception;
	
	/**列表(全部) 根据某个用户，关联权限
	 * @param pd.username
	 * @throws Exception
	 */
	public List<MsServicesReg> listAllAuthority(PageData pd)throws Exception;
	
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
	
	/**修改
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception;
	
	/**查询
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public List<MsServicesReg> find(PageData pd)throws Exception;
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer id)throws Exception;
	
	/**
	 * 根据classid查询列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listPageByClassId(Page page)throws Exception;
	
	
	/**
	 * 根据多id查询
	 * @param regids
	 * @return
	 * 
	 * @throws Exception
	 */
	public List<MsServicesReg> findByIds(String[] regids)throws Exception;
	
	/**
	 * 根据id查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public MsServicesReg findById(PageData pd)throws Exception;
	
	/**
	 * 启动服务
	 * @param regids
	 * @throws Exception
	 */
	public ResultObj startServices(String[] regids) throws Exception;
	
	/**批量删除
	 * @param regids
	 * @throws Exception
	 */
	public void deleteAll(String[] regids)throws Exception;
	
	/**
	 * 更新服务状态
	 * @param id
	 * @param status
	 * @throws Exception
	 */
	public void updateStatus(Integer id,int status)throws Exception;
	
	
	/**
	 * 修改服务
	 * @param id
	 * @param status
	 * @throws Exception
	 */
	public void updateReg(PageData pd)throws Exception;
	
	
	/**
	 * 停止服务
	 * @param regids
	 * @throws Exception
	 */
	public ResultObj stopServices(String[] regids) throws Exception;
	
	/**
	 * 删除服务
	 * @param regids
	 * @param deleteArcFlag
	 * @throws Exception
	 */
	public Boolean deleteServices(String[] regids,Boolean deleteArcFlag) throws Exception;
	
	/**
	 * 获取可用 的服务
	 * @return
	 * @throws Exception
	 */
	public List<com.fh.bean.folder.Service> getAvailableServices() throws Exception;
	
	/**
	 * 更新服务访问量
	 * @return
	 * @throws Exception
	 */
	//public void updatecount(PageData pd)throws Exception;
	
	/**
	 * 更新服务访问量
	 * @param servicename 服务名称
	 * @param servicetype 服务类型
	 * @throws Exception
	 */
	public void updatecount(String servicename,String servicetype)throws Exception;
}
