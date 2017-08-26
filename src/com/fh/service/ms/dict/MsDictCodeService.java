package com.fh.service.ms.dict;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.ms.dict.MsDictCode;
import com.fh.util.PageData;

public interface MsDictCodeService {

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<MsDictCode> listAll(MsDictCode pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
}
