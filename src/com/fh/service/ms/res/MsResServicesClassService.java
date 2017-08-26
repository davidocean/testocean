package com.fh.service.ms.res;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.res.MsResServicesClass;
import com.fh.util.PageData;
/**
 * 服务分类
 * @author ZhaiZhengqiang
 * @date 2016-11-06
 */
public interface MsResServicesClassService {
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsResServicesClass> listAll(MsResServicesClass pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	
	/**根据parentid查找列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> parentid(Page page)throws Exception;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<MsResServicesClass> listAllClass(String parentId) throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<MsResServicesClass> listSubClassByParentId(String parentId) throws Exception;
	
	
	/**
	 * 获取大于指定parentI在所有数据
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<MsResServicesClass> listSubClassGTParentId(Integer parentId) throws Exception;
	
	/**
	 * 通过ID获取其列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<MsResServicesClass> allListClass(String parentId) throws Exception;
	
	
	/**
	 * 根据id查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	
	/**修改
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception;
	
	
	/**批量删除
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	
	/**
	 * 根据classid查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByClassid(PageData pdclassid)throws Exception;
	

	/**
	 * 根据classname查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<MsResServicesClass> findByClassname(String classname)throws Exception;
	
	/**
	 * 根据parentId和classname查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByIn(PageData pdclassid)throws Exception;
	
	public List<MsResServicesClass>  find(PageData pd)throws Exception;
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<MsResServicesClass> listRoot(MsResServicesClass pd) throws Exception;

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<MsResServicesClass> getIdByParentId(String id) throws Exception;
}
