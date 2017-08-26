/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: MsServicesLogJob.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月26日 下午4:05:13
 * @version: V1.0  
 */
package com.proxy.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.fh.entity.ms.logs.MsServicesLogMerged;
import com.fh.entity.ms.logs.MsServicesLogMergedPK;
import com.fh.service.ms.logs.MsServicesLogMergedService;
import com.fh.service.ms.logs.MsServicesLogMonitorService;
import com.fh.util.PageData;
import com.proxy.util.DateUtil;

/**
 * @author Jes
 * 		
 */
@Component("msServicesLogJob")
public class MsServicesLogJob {
	private static boolean run = false;
	
	@Resource(name = "msServicesLogMonitorService")
	private MsServicesLogMonitorService msServicesLogMonitorService;
	
	@Resource(name = "msServicesLogMergedService")
	private MsServicesLogMergedService msServicesLogMergedService;
	
	public void mergeMonitorLogs() {
		System.out.println("MergeLog Job running...\t" + DateUtil.Date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		if (!run) {
			try {
				run = true;
				// TODO merge logs.
				mergeLogs();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				run = false;
			}
		}
	}
	
	public void cleanMergeMonitorLogs() {
		System.out.println("\tClean Clean..." + new Date());
	}
	
	public void mergeLogs() throws Exception {
		
		String endtime = DateUtil.Date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
		String starttime = DateUtil.Date2Str(DateUtil.getDayModify(new Date(), -7), "yyyy-MM-dd HH:mm:ss");
		
		PageData pd = new PageData();
		pd.put("endtime", endtime);
		pd.put("starttime", starttime);
		List<PageData> pdlist = (List<PageData>) msServicesLogMonitorService.getMergeLogs(pd);
		
		List<MsServicesLogMerged> list = new ArrayList<MsServicesLogMerged>();
		MsServicesLogMerged logMerged = null;
		MsServicesLogMergedPK logMergedPK = null;
		
		for (PageData _pd : pdlist) {
			Iterator<String> it = _pd.entrySet().iterator();
			
			logMerged = new MsServicesLogMerged();
			logMergedPK = new MsServicesLogMergedPK();
			
			while (it.hasNext()) {
				Object key = it.next();
				String[] pairs = (key + "").split("=");
				
				if (!StringUtils.isEmpty(pairs[0]) && "serviceid".equals(pairs[0])) {
					logMergedPK.setServiceid(Math.round(Double.parseDouble(pairs[1])));
				} else if (!StringUtils.isEmpty(pairs[0]) && "requser".equals(pairs[0])) {
					logMergedPK.setUsername(pairs[1]);
				} else if (!StringUtils.isEmpty(pairs[0]) && "year_".equals(pairs[0])) {
					logMergedPK.setYear(pairs[1]);
				} else if (!StringUtils.isEmpty(pairs[0]) && "month_".equals(pairs[0])) {
					logMergedPK.setMonth(pairs[1]);
				} else if (!StringUtils.isEmpty(pairs[0]) && "day_".equals(pairs[0])) {
					logMergedPK.setDay(pairs[1]);
				} else if (!StringUtils.isEmpty(pairs[0]) && "servicetype".equals(pairs[0])) {
					logMergedPK.setServicetype(pairs[1]);
				} else if (!StringUtils.isEmpty(pairs[0]) && "remotehostip".equals(pairs[0])) {
					logMergedPK.setRemotehostip(pairs[1]);
				} else if (!StringUtils.isEmpty(pairs[0]) && "fullname".equals(pairs[0])) {
					logMerged.setUserfullname(pairs[1]);
				} else if (!StringUtils.isEmpty(pairs[0]) && "avg_access_time".equals(pairs[0])) {
					logMerged.setAccesstimeconsuming(Math.round(Double.parseDouble(pairs[1])));
				} else if (!StringUtils.isEmpty(pairs[0]) && "accsee_count".equals(pairs[0])) {
					logMerged.setAccesscount(Math.round(Double.parseDouble(pairs[1])));
				} else if (!StringUtils.isEmpty(pairs[0]) && "access_success".equals(pairs[0])) {
					logMerged.setAccesssuccess(Math.round(Double.parseDouble(pairs[1])));
				} else if (!StringUtils.isEmpty(pairs[0]) && "access_failure".equals(pairs[0])) {
					logMerged.setAccessfailure(Math.round(Double.parseDouble(pairs[1])));
				} else if (!StringUtils.isEmpty(pairs[0]) && "requesttime".equals(pairs[0])) {
					logMerged.setAccessdate(DateUtil.getDate(pairs[1], "yyyy-MM-dd"));
				} else if (!StringUtils.isEmpty(pairs[0]) && "servicename".equals(pairs[0])) {
					logMerged.setServicename(pairs[1]);
				} else if (!StringUtils.isEmpty(pairs[0]) && "servicefullname".equals(pairs[0])) {
					logMerged.setServicefullname(pairs[1]);
				}
			}
			logMerged.setPkid(logMergedPK);
			list.add(logMerged);
		}
		msServicesLogMergedService.saveOrUpdateBatch(list);
	}
	
	public static void main(String args[]) {
		
	}
}
