package com.fh.service.ms.services;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.services.MsStatClassification;
import com.fh.util.PageData;

public interface MsStatClassificationService {
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsStatClassification> listAll(MsStatClassification pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByAll()throws Exception;
	
	
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
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception;
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer id)throws Exception;
	
	/**
	 * 通过名称来查找
	 * */
	public MsStatClassification findByName(String name)throws Exception;
}
