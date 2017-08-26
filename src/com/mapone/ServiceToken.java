package com.mapone;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.esri.arcgisws.ServiceCatalogBindingStub;
import com.fh.bean.token.Token;

import net.sf.json.JSONObject;

public class ServiceToken {
	//security====================================================================
	//private static String token = null;
	
	public static String getToken2() throws Exception{
		//if(token == null){
		   String endpointURL = MsConfigProperties.ARCGIS_SERVICES_URL;
		   ServiceCatalogBindingStub catalog = new ServiceCatalogBindingStub(endpointURL);
		   String tokenurl = catalog.getTokenServiceURL();
		   String ip = MsConfigProperties.ARCGIS_SERVER_IP;
		   String account = MsConfigProperties.ARCGIS_SERVER_ACCOUNT;
		   String[] arrStr = account.split("/");
		   //String tokenrequesturl = tokenurl + "?request=getToken&Username=siteadmin&Password=admin123&clientid=ip."+ip+"&expiration=10";
		   //String tokenrequesturl = tokenurl + "?request=getToken&Username=siteadmin&Password=admin123&clientid=ip.58.63.173.127&expiration=10";
		   String tokenrequesturl = tokenurl + "?request=getToken&Username="+ arrStr[0] +"&Password="+ arrStr[1] +"&clientid=ip.58.63.173.127&expiration=10";
		   System.out.print("mwhdds:" +tokenrequesturl);
		   URL url=new URL(tokenrequesturl);
		   URLConnection conn=url.openConnection(); 
		   InputStream inStream=conn.getInputStream();
		   String token = MapUtil.InputStreamToString(inStream,"UTF-8");
		//}
		return token;
	}
	/**
	 * 获取token
	 * @return
	 * @throws Exception
	 */
	public static String getToken() throws Exception {
	    String account = MsConfigProperties.ARCGIS_SERVER_ACCOUNT;
	    String[] arrStr = account.split("/");
		System.out.print("mwhdds:" +arrStr[0] + arrStr[1]);   
		
		Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("Username", arrStr[0]);
        dataMap.put("Password", arrStr[1]);
        dataMap.put("clientid", "ip."+MsConfigProperties.SERVICES_IP);        
        dataMap.put("expiration", "10");          
        dataMap.put("f", "json");
		HttpRequestor hr = new HttpRequestor();
		try{
			String s =  hr.doPost(MsConfigProperties.ARCGIS_URL+"tokens/", dataMap);
			
			System.out.println(s);
			JSONObject obj = JSONObject.fromObject(s);
			Token token = (Token) JSONObject.toBean(obj, Token.class);
			return token.getToken();
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
