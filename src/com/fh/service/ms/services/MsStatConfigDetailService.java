package com.fh.service.ms.services;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.services.MsStatConfig;
import com.fh.entity.ms.services.MsStatConfigDetail;
import com.fh.util.PageData;

public interface MsStatConfigDetailService {
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsStatConfigDetail> listAll(MsStatConfigDetail pd)throws Exception;
	
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
	 * 根据备注来更新父节点ID
	 * @param zmemo
	 * @throws Exception
	 */
	public void updateStaticsIdByZmemo(PageData pd)throws Exception;
	
	 /**
	 * 根据父类ID查找明细
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findByStaticsId(Integer staticsid)throws Exception;
	
	/**
	 * 通过staticsid、staticobjname获取数据
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findByStaticidName(PageData pd)throws Exception;
	
	/**
	 * 根据id查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public MsStatConfigDetail findById(PageData pd)throws Exception;
}
