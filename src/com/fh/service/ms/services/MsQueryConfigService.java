package com.fh.service.ms.services;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.services.MsQueryConfig;
import com.fh.util.PageData;

public interface MsQueryConfigService {

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsQueryConfig> listAll(MsQueryConfig pd)throws Exception;
	
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
	
	 /**
	 * 根据备注来更新父节点ID
	 * @param zmemo
	 * @throws Exception
	 */
	public void updateCombIdByZmemo(PageData pd)throws Exception;
	
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	
	/**删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer id)throws Exception;
	
	
	 /**
	 * 删除
	 * @param combid
	 * @throws Exception
	 */
	public void deleteByCombId(Integer combid)throws Exception;
	
	
}
