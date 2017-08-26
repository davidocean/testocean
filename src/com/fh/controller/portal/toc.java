package com.fh.controller.portal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.fh.entity.ms.res.MsResServicesClass;
import com.fh.entity.ms.res.TOCItem;
import com.fh.entity.ms.services.MsServicesReg;
import com.fh.entity.system.User;
import com.fh.service.ms.res.MsResServicesClassService;
import com.fh.service.ms.services.MsServicesRegService;
import com.fh.util.PageData;

public class toc {
	
	String strTOC = "{" ;
	
	private List<TOCItem> lstToc = new ArrayList<TOCItem>();
	
	public List<TOCItem> getChildren2(String parentid)
	{
		List<TOCItem> children = new ArrayList<TOCItem>();
		for(TOCItem sc : lstToc)
		{
			if (sc.getParentID().equals(parentid))
			{
				children.add(sc);
			}
		}
		
		return children;
	}
	
	public void buildTOC2(String parentid)
	{
		String temp = "";
		String defSelected = "";
		List<TOCItem> children = getChildren2(parentid);  
		if (children.size() > 0)   //有目录儿子
		{
			strTOC += ",additionalParameters: {children:{";
			for(TOCItem item : children)
			{
				if (item.getType().equals("folder"))
				{
					strTOC += "'"+ item.getId() +"' : {text: '"+ item.getText() +"', type: 'folder'";//#1-"+sc.getId() +"#}";
					buildTOC2(item.getId());
				}
				else
				{
					defSelected = "";
					if (item.getServiceName().equals("HYMap"))
					{
						defSelected = "'additionalParameters': {'id': '9','item-selected': true},";
					}
					
					temp += "'" + item.getId() + "' : {text: '" + item.getText() + "', type: 'item',"+defSelected+"cache:'" + item.getServiceCache() + "'," 
												+ "nativeurl:'" + item.getServiceNativeURL() + "'," 
												+ "folder:'" + item.getServiceFolder() + "'," 
												+ "ServiceName:'" + item.getServiceName() + "'," 
												+ "hasPermission:'" + item.getHasPermission() + "'," 
												+ "fullPermission:'" + item.getFullPermisson() + "'," 
												+ "isBasemap:'" + item.getIsBasemap() + "'" 
												+ "},";
				}

			}
			
			if (!temp.isEmpty()){
				temp = temp.substring(0,temp.length()-1);
			}
			
			strTOC +=  temp + "}}},";
		}
		else
		{
			strTOC +=  "},";
		}
	}
	
	private void getBaseMaps(String id,Set<String> basemapSet){
		
		for(TOCItem ti : lstToc){
			
			if (ti.getParentID().equals(id)) {
				basemapSet.add(ti.getId());
				getBaseMaps(ti.getId(), basemapSet);
			}
		}
	}
	
	public String getTOC2(MsResServicesClassService msResServicesClassService,
			MsServicesRegService msServicesRegService,User user)
	{
		List<MsResServicesClass> lstServicesClass = null;
		List<MsServicesReg>	regServiceList = null;
		try
		{
			lstServicesClass = msResServicesClassService.listAll(null);
			for(MsResServicesClass sc : lstServicesClass)
			{
				TOCItem item = new TOCItem();
				item.setId(sc.getId());
				item.setParentID(sc.getParentid());
				item.setText(sc.getClassname());
				item.setType("folder");
				lstToc.add(item);
			}
			
			Set<String> basemapSet = new HashSet<String>();
			basemapSet.add("1");
			getBaseMaps("1", basemapSet);

			MsServicesReg reg = new MsServicesReg();
			
			if (null != user && !StringUtils.isEmpty(user.getUSERNAME())) {
				PageData _pd = new PageData();
				_pd.put("username", user.getUSERNAME());
				regServiceList = msServicesRegService.listAllAuthority(_pd);//所有注册服务列表 根据用户来过滤权限，借用 其 字段 memo
			}else {
				regServiceList = msServicesRegService.listAll(reg);//所有注册服务列表
			}
			
			for(MsServicesReg msr :regServiceList)
			{
				//if (msr.getId() < 100)
				{
					TOCItem item = new TOCItem();
					item.setId(String.valueOf(msr.getId()));
					item.setParentID(String.valueOf(msr.getClassid()));
					item.setText(msr.getServicefullname());
					item.setServiceFolder(msr.getFolder());
					item.setServiceNativeURL(msr.getNativeurl());
					item.setServiceCache(msr.getServicecache());
					item.setServiceName(msr.getServicename());
					// service permission.
					item.setFullPermisson(msr.getFullpermission());
					item.setHasPermission((null == msr.getMemo() || StringUtils.isEmpty(msr.getMemo()) ? "-1" : msr.getMemo()));// 0:没有权限；1:有权限;-1:没有登录信息
					item.setIsBasemap(basemapSet.contains(item.getParentID()) ? "1" : "0");// 是否是基础底图 0：否；1：是。
					
					item.setType("item");
					lstToc.add(item);
				}
			}
			 
			strTOC = "{" ;
			for(TOCItem item :lstToc)
			{
				if (item.getParentID().equals("0"))
				{
						strTOC += "'"+ item.getId()+"' : {text: '"+ item.getText() +"', type: 'folder'";
						buildTOC2(item.getId());
				}
			}
			
			strTOC = strTOC.substring(0,strTOC.length()-1);
			strTOC +="}";
			//System.out.println(strTOC);
			return strTOC;
			
		} catch (Exception e) {
			//logger.error(e.toString(), e);
		}
			
		return "";
		
	}
}
