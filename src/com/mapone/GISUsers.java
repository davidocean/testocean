package com.mapone;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fh.util.PageData;

/** 
 * 类名称：GISUsers
 * 创建人：mwhdds 
 * 更新时间：2016年11月3日
 * @version
 */

public class GISUsers {
	
	public static String arcgis_url = MsConfigProperties.ARCGIS_URL;
	
	//新增用户
	public static void addUser(PageData pd) throws Exception{
        Map<String,String> dataMap = new HashMap<String,String>();
        String mm = ServiceToken.getToken();
        dataMap.put("token", mm);
        System.out.println("addUser:"+mm);
        dataMap.put("Username", pd.getString("USERNAME"));
        dataMap.put("Password", pd.getString("PASSWORD"));
        dataMap.put("fullname", pd.getString("NAME"));
        dataMap.put("Email", pd.getString("EMAIL"));
        dataMap.put("description", pd.getString("BZ"));
        		
		HttpRequestor hr = new HttpRequestor();
		try {
			hr.doPost(arcgis_url + "admin/security/users/add", dataMap);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//删除用户
	public static void delUser(PageData pd) throws Exception {				
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("token", ServiceToken.getToken());//
        dataMap.put("username", pd.getString("USER_NAME"));
        dataMap.put("f", "json");
        
        System.out.println(pd.getString("USER_NAME"));
        
		HttpRequestor hr = new HttpRequestor();
		try
		{
			hr.doPost(arcgis_url + "admin/security/users/remove", dataMap);
		}
		catch (IOException e)
		{
			
		}
	}
	
	//用户修改
	public static void updateUser(PageData pd) throws Exception {	
		
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("token", ServiceToken.getToken());
        dataMap.put("Username", pd.getString("USERNAME"));
        //dataMap.put("Password", pd.getString("PASSWORD"));
        dataMap.put("fullname", pd.getString("NAME"));
        dataMap.put("Email", pd.getString("EMAIL"));
        dataMap.put("description", pd.getString("BZ"));
        
		HttpRequestor hr = new HttpRequestor();
		try{
			hr.doPost(arcgis_url + "admin/security/users/update", dataMap);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	
}
