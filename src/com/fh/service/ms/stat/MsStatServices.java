package com.fh.service.ms.stat;

import java.util.List;

import com.fh.entity.ms.services.MsStatConfig;
import com.fh.entity.ms.stat.MsServicesStat;
import com.fh.util.PageData;

public interface MsStatServices {
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listStatServices(PageData pd)throws Exception;
	public List<PageData> listStatUsers(PageData pd)throws Exception;
	public List<PageData> listStatServiceSuccess(PageData pd)throws Exception;
}
