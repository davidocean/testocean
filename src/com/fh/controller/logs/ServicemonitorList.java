/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: ServicemonitorList.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月26日 下午12:34:21
 * @version: V1.0  
 */

package com.fh.controller.logs;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.ms.logs.MsServicesLogMonitorService;
import com.fh.util.PageData;

/**
 * @author Jes
 * 展示服务列表
 */
@Controller
@RequestMapping(value="/logs")
public class ServicemonitorList extends BaseController {

	@Resource(name="msServicesLogMonitorService")
	private MsServicesLogMonitorService msServicesLogMonitorService;
	
	/**
	 * 展示服务列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="servicemonitorList")
	public ModelAndView servicemonitorList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String timestart = pd.getString("timestart");	//开始时间
		String timeend = pd.getString("timeend");		//结束时间
		if(timestart != null && !"".equals(timestart)){
			pd.put("timestart", timestart+" 00:00:00");
		}
		if(timeend != null && !"".equals(timeend)){
			pd.put("timeend", timeend+" 23:59:59");
		} 
		
		page.setPd(pd);
		
		List<PageData> varList = msServicesLogMonitorService.datalistPage(page);
		mv.addObject("pd", pd);
		mv.addObject("varList",varList);
		mv.setViewName("mapmanager/logs/servicemonitorList");
		return mv;
	}
}
