package com.fh.service.ms.services;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.services.MsQueryConfigCombination;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.entity.ms.services.MsStatConfig;
import com.fh.util.PageData;

public interface MsStatConfigService {

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsStatConfig> listAll(MsStatConfig pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 列表(分页使用)
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> configlistPage(Page page)throws Exception;
	
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception;
	
	
	/**
	 * 修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void setEnble(PageData pd)throws Exception;
	
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer id)throws Exception;
	
	/**
	 * 通过名称查找
	 * @param Name
	 * @throws Exception
	 */
	public MsStatConfig findByTypeAndName(PageData pd)throws Exception;
	
	
	/**
	 * 通过StaticsName查找
	 * @param Name
	 * @throws Exception
	 */
	public List<PageData> findByStaticsName(String staticsname)throws Exception;
	
	/**
	 * 根据id查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public MsStatConfig findById(PageData pd)throws Exception;
	
	
}
