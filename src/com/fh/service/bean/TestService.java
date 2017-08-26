package com.fh.service.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fh.bean.result.ResultObj;
import com.mapone.HttpRequestor;
import com.mapone.ServiceToken;

import net.sf.json.JSONObject;

public class TestService {
	
	public static void main(String[] args) {
		//System.out.println("requestService="+requestService());
		//ServicePublishService service = new ServicePublishService();
		//Folder folder = service.getServices();
		//Clusters clusters = service.getServiceClusters();
		//ResultObj result = requestService();
		MsArcgisService service = new MsArcgisService();
		List services = service.getServices("/",null);
		List folder = service.getFolders();
		System.out.println(services);
		System.out.println(folder);
	}
	
	public static ResultObj requestService(){
		try {
			
			Map dataMap = new HashMap();
	        dataMap.put("token", ServiceToken.getToken());
	    	MsArcgisService service = new MsArcgisService();
	        //dataMap.put("service", service.getTestServicePublishJson("test"));
	        dataMap.put("f", "pjson");//
	        dataMap.put("f", "json");
			HttpRequestor hr = new HttpRequestor();
			String s = hr.doPost("http://119.29.52.71:6080/arcgis/admin/services/startServices", dataMap);
			JSONObject obj = JSONObject.fromObject(s);
			ResultObj result = (ResultObj) JSONObject.toBean(obj, ResultObj.class);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
