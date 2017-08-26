package com.mapone;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件
 * @author ZhaiZhengqiang
 * @date 2016-11-07
 *
 */
public class MsConfigProperties {

	/**
	 * 返回目录服务器请求路径
	 */
	public static String ARCGIS_ADMIN_SERVICES_URL;

	/**
	 * 返回集群服务器请求路径
	 */
	public static String ARCGIS_ADMIN_CLUSTERS_URL;
	
	/**
	 * token服务请求地址
	 */
	public static String ARCGIS_SERVICES_URL;
	
	/**
	 * 服务器IP
	 */
	public static String SERVICES_IP;
	
	/**
	 * 系统名称
	 */
	public static String App_Name;	
	
	//
	/**
	 * 系统名称
	 */
	public static String CopyRight;	
	
	/**
	 * 服务器请求路径
	 */
	public static String ARCGIS_URL;
	
	/**
	 * 资源物理地址目录
	 */
	public static String RESOURCE_DIR;
	
	/**
	 * 输出目录
	 */
	public static String OUTPUT_DIR;
	
	/**
	 * 转发地址
	 */
	public static String TRANSFER_URL;
	
	/**
	 * arcgis server所在服务器IP
	 */
	public static String ARCGIS_SERVER_IP;
	

	/**
	 * 登录账户和密码
	 */	
	public static String ARCGIS_SERVER_ACCOUNT;

	/**
	 * ARCGIS_FOR_JS_API
	 */	
	public static String ARCGIS_JS_API;
	
	/**
	 * 瓦片服务访问量比率
	 */
	public static String TILED_SERVICE_RATE;
	
	static {
		Properties prop = new Properties();
		InputStream in = MsConfigProperties.class.getClassLoader().getResourceAsStream("msconfig.properties");
		try {
			prop.load(in);
			ARCGIS_ADMIN_SERVICES_URL = prop.getProperty("arcgis_admin_services_url").trim();
			ARCGIS_ADMIN_CLUSTERS_URL = prop.getProperty("arcgis_admin_clusters_url").trim();
			ARCGIS_SERVICES_URL = prop.getProperty("arcgis_services_url").trim();
			SERVICES_IP = prop.getProperty("services_ip").trim();
			ARCGIS_URL = prop.getProperty("arcgis_url").trim(); 
			App_Name  = prop.getProperty("app_name").trim();
			CopyRight = prop.getProperty("copyright").trim();
			RESOURCE_DIR = prop.getProperty("resource_dir").trim();
			OUTPUT_DIR = prop.getProperty("output_dir").trim();
			TRANSFER_URL = prop.getProperty("transfer_url").trim();
			ARCGIS_SERVER_IP = prop.getProperty("arcgis_server_ip").trim();
			ARCGIS_SERVER_ACCOUNT = prop.getProperty("arcgis_server_account").trim();
			ARCGIS_JS_API =  prop.getProperty("arcgis_js_api").trim();
			TILED_SERVICE_RATE =  prop.getProperty("tiled_service_rate").trim();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
	}

}
