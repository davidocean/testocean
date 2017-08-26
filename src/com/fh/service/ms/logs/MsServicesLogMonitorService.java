package com.fh.service.ms.logs;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.logs.MsServicesLogMonitor;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.util.PageData;

/**
 * 服务访问日志（服务监控）
 * @author Jes
 * @date 2016-11-04
 */
public interface MsServicesLogMonitorService {

	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveObj(MsServicesLogMonitor monitor)throws Exception;
	
	
	/**批量删除
	 * @param regids
	 * @throws Exception
	 */
	public void deleteDateBefore(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsServicesReg> listAll(MsServicesReg pd)throws Exception;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> datalistPage(Page page)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> getMergeLogs(PageData pd)throws Exception;
	
	
}
