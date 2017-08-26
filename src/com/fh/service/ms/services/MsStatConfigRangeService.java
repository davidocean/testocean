package com.fh.service.ms.services;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.services.MsStatConfigDetail;
import com.fh.entity.ms.services.MsStatConfigRange;
import com.fh.util.PageData;

public interface MsStatConfigRangeService {
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsStatConfigRange> listAll(MsStatConfigRange pd)throws Exception;
	
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
	 * 根据父类删除对应的列表
	 * @param id
	 * @throws Exception
	 */
	public void deleteByStaticsid(Integer id)throws Exception;
	
	/**
	 * 根据id查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public MsStatConfigRange findById(PageData pd)throws Exception;
	
	
	/**
	 * 根据id查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByConfigDetailId(Integer detailid)throws Exception;
}
