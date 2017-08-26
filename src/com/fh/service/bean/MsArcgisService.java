package com.fh.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fh.bean.cluster.Cluster;
import com.fh.bean.cluster.ClusterProtocol;
import com.fh.bean.cluster.Clusters;
import com.fh.bean.folder.Folder;
import com.fh.bean.folder.FoldersDetail;
import com.fh.bean.folder.Service;
import com.fh.bean.result.ResultObj;
import com.fh.bean.service.Properties;
import com.fh.bean.service.ServicePublishBean;
import com.fh.bean.service.type.Type;
import com.fh.entity.ms.services.MsServicesReg;
import com.mapone.HttpRequestor;
import com.mapone.MsConfigProperties;
import com.mapone.ServiceToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 服务发布
 * @author ZhaiZhengqiang
 * @date 2016-11-03
 */
public class MsArcgisService {
	public static String ARCGIS_URL = MsConfigProperties.ARCGIS_URL;
	/**
	 * 获取测试服务发布json数据
	 * @return
	 */
	public String getServiceJson(String serviceName,String serviceType,String description,String clusterName,String filePath){
		ServicePublishBean bean = new ServicePublishBean();
		bean.setServiceName(serviceName);
		bean.setType(serviceType);
		bean.setDescription(description);
		bean.setCapabilities("map,query,data");
		bean.setClusterName(clusterName);
		Properties pro = new Properties();
		pro.setVirtualCacheDir(ARCGIS_URL+"arcgiscache");
		pro.setFilePath(filePath);
		pro.setVirtualOutputDir(ARCGIS_URL+"server/arcgisoutput");
		pro.setOutputDir(MsConfigProperties.OUTPUT_DIR);
		pro.setIsCached("false");
		pro.setIgnoreCache("false");
		bean.setProperties(pro);
		JSONObject json = JSONObject.fromObject(bean); 
		return json.toString();
	}
	
	/**
	 * 返回服务目录System, Utilities
	 */
	public List<String> getFolders(){
		try {
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("token", ServiceToken.getToken());
			dataMap.put("f", "json");
			HttpRequestor hr = new HttpRequestor();
			String s = hr.doPost(ARCGIS_URL+"admin/services/", dataMap);
			JSONObject obj = JSONObject.fromObject(s);
			@SuppressWarnings("rawtypes")
			Map<String, Class> classMap = new HashMap<String, Class>();  
			classMap.put("foldersDetail", FoldersDetail.class); 
			classMap.put("folders", String.class); 
			classMap.put("services", Service.class);
			Folder folder = (Folder) JSONObject.toBean(obj, Folder.class,classMap);
			List<String> folderList = new ArrayList<String>();
			if(folder != null){
				String folderName = folder.getFolderName();
				folderList.add(folderName);
				List<String> folders = folder.getFolders();
				for(String f :folders){
					folderList.add(folderName+f);
				}
			}
			return folderList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询所有GisServer中Service
	 */
	public List<Service> getServices(String folderName,List<Service> services){
		try {
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("token", ServiceToken.getToken());
			dataMap.put("f", "json");
			HttpRequestor hr = new HttpRequestor();
			String s = hr.doPost(ARCGIS_URL+"admin/services/"+folderName, dataMap);
			JSONObject obj = JSONObject.fromObject(s);
			@SuppressWarnings("rawtypes")
			Map<String, Class> classMap = new HashMap<String, Class>();  
			classMap.put("foldersDetail", FoldersDetail.class); 
			classMap.put("folders", String.class); 
			classMap.put("services", Service.class);
			Folder folder = (Folder) JSONObject.toBean(obj, Folder.class,classMap);
			if(folder != null){
				List<Service> list = folder.getServices();
				if(services == null){
					services = new ArrayList<Service>();
				}
				for(Service ser : list){
					ser.setFolderName(folderName);
					services.add(ser);
				}
				List<String> folders = folder.getFolders();
				if(folders != null){
					for(String f : folders){
						this.getServices(folderName+f, services);
					}
				}
			}
			return services;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回集群
	 */
	public Clusters getServiceClusters(){
		try {
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("token", ServiceToken.getToken());
			dataMap.put("f", "json");
			HttpRequestor hr = new HttpRequestor();
			String s = hr.doPost(ARCGIS_URL+"admin/clusters", dataMap);
			JSONObject obj = JSONObject.fromObject(s);
			@SuppressWarnings("rawtypes")
			Map<String, Class> classMap = new HashMap<String, Class>();  
			classMap.put("clusters", Cluster.class); 
			classMap.put("clusterProtocol", ClusterProtocol.class);  
			Clusters clusters = (Clusters) JSONObject.toBean(obj, Clusters.class,classMap);
			return clusters;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回集群
	 */
	public List<Type> getServiceTypes(){
		try {
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("token", ServiceToken.getToken());
			dataMap.put("f", "json");
			HttpRequestor hr = new HttpRequestor();
			String s = hr.doPost(MsConfigProperties.ARCGIS_ADMIN_SERVICES_URL+"/types ", dataMap);
			JSONObject obj = JSONObject.fromObject(s);
			Map map = obj;
			List<Type> types = new ArrayList<Type>();
			if(map != null){
				List<Map> listMap = (List<Map>) map.get("types");
				for(Map m : listMap){
					Type type = new Type();
					type.setName(m.get("Name").toString());
					type.setDisplayName(m.get("DisplayName").toString());
					types.add(type);
				}
			}
			return types;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 创建服务
	 * @param serviceName
	 * @param serviceType
	 * @param description
	 * @param clusterName
	 * @param filePath
	 * @param folderName
	 * @return
	 */
	public ResultObj createService(String serviceName,String serviceType,String description,String clusterName,String filePath,String folderName){
		try {
			Map<String,String> dataMap = new HashMap<String,String>();
	        dataMap.put("token", ServiceToken.getToken());
	        dataMap.put("service", this.getServiceJson(serviceName,serviceType,description,clusterName,filePath));
	        dataMap.put("f", "pjson");
	        dataMap.put("f", "json");
			HttpRequestor hr = new HttpRequestor();
			String[] folderArray = folderName.split("/");
			String folderStr = "/";
			if(folderArray != null && folderArray.length > 1){
				for(String f : folderArray){
					if(folderStr.equals("/"))
						folderStr = "/"+ f;
					else {
						folderStr = folderStr+"/"+f;
					}
				}
			}
			String url = ARCGIS_URL+"admin/services"+folderStr+"/createService";
			String s = hr.doPost(url, dataMap);
			JSONObject obj = JSONObject.fromObject(s);
			ResultObj result = (ResultObj) JSONObject.toBean(obj, ResultObj.class);
			if(result.getStatus().equals(ResultObj.SUCCESS)){
				Service service = new Service();
				service.setServiceName(serviceName);
				service.setType(serviceType);
				folderName = folderName.equals("/") ? folderName : folderName.substring(folderName.lastIndexOf("/")+1,folderName.length());
				service.setFolderName(folderName);
				result = this.startService(service);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取服务信息
	 * @param folder
	 * @param serviceName
	 * @param serviceType
	 * @return dynamic 或 tiled
	 */
	public String getServiceCache(String folder,String serviceName,String serviceType){
		try {
			folder = StringUtils.isBlank(folder) ? "/" : folder;
			String url = MsConfigProperties.ARCGIS_URL+"rest/services"+folder + "/" + serviceName +"/" +serviceType;
			//url = "http://119.29.52.71:6080/arcgis/rest/services/HYImage/MapServer";
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("token", ServiceToken.getToken());
			dataMap.put("f", "pjson");
			HttpRequestor hr = new HttpRequestor();
			String s = hr.doPost(url, dataMap);
			JSONObject obj = JSONObject.fromObject(s);
			JSONObject tileInfo = (JSONObject) obj.get("tileInfo");
			JSONArray lods = (JSONArray) tileInfo.get("lods");
			if(lods != null && lods.size() > 0){
				return "tiled";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dynamic";
	}
	
	/**
	 * 多服务启动
	 * @param services
	 * @return
	 */
	public ResultObj startServices(List<MsServicesReg> services) throws Exception{
		String url = MsConfigProperties.ARCGIS_ADMIN_SERVICES_URL+"/startServices";
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("token", ServiceToken.getToken());
		dataMap.put("f", "json");
		List mapList = new ArrayList();
		for(MsServicesReg reg : services){
			Map map = new HashMap();
			String folder = reg.getFolder().equals("/") ? reg.getFolder() : reg.getFolder().substring(reg.getFolder().lastIndexOf("/")+1,reg.getFolder().length());
			map.put("folderName", folder);
			map.put("serviceName", reg.getServicename());
			map.put("type", reg.getServicetype());
			mapList.add(map);
		}
		Map servicesMap = new HashMap();
		servicesMap.put("services", mapList);
		String servicesStr = JSONObject.fromObject(servicesMap).toString();
		dataMap.put("services",servicesStr);
		HttpRequestor hr = new HttpRequestor();
		String s = hr.doPost(url, dataMap);
		JSONObject obj = JSONObject.fromObject(s);
		ResultObj result = (ResultObj) JSONObject.toBean(obj, ResultObj.class);
		return result;
	}
	
	/**
	 * 单服务启动
	 * @param service
	 * @return
	 */
	public ResultObj startService(Service service) throws Exception{
		String url = MsConfigProperties.ARCGIS_ADMIN_SERVICES_URL+"/startServices";
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("token", ServiceToken.getToken());
		dataMap.put("f", "json");
		List mapList = new ArrayList();
		Map map = new HashMap();
		String folderName = service.getFolderName().equals("/") ? service.getFolderName() : service.getFolderName().substring(service.getFolderName().lastIndexOf("/")+1,service.getFolderName().length());
		map.put("folderName", folderName);
		map.put("serviceName", service.getServiceName());
		map.put("type", service.getType());
		mapList.add(map);
		Map servicesMap = new HashMap();
		servicesMap.put("services", mapList);
		String servicesStr = JSONObject.fromObject(servicesMap).toString();
		dataMap.put("services",servicesStr);
		HttpRequestor hr = new HttpRequestor();
		String s = hr.doPost(url, dataMap);
		JSONObject obj = JSONObject.fromObject(s);
		ResultObj result = (ResultObj) JSONObject.toBean(obj, ResultObj.class);
		return result;
	}
	
	/**
	//单服务启动
	public ResultObj startService(Service service) throws Exception{
		String url = MsConfigProperties.ARCGIS_ADMIN_SERVICES_URL+"/"+service.getFolderName()+"/"+service.getServiceName()+"."+service.getType()+"/start";
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("token", ServiceToken.getToken());
		dataMap.put("f", "json");
		HttpRequestor hr = new HttpRequestor();
		String s = hr.doPost(url, dataMap);
		JSONObject obj = JSONObject.fromObject(s);
		Map map = obj;
		ResultObj resultObj = new ResultObj();
		if(map != null){
			resultObj.setStatus(map.get("status").toString());
			JSONArray array = JSONArray.fromObject(map.get("messages"));
			List list = JSONArray.toList(array);
			String[] messages=new String[list.size()]; 
			for(int i=0;i<list.size();i++){  
				messages[i]=(String)list.get(i);  
	        } 
			resultObj.setCode("无错误代码");
			resultObj.setMessages(messages);
		}
		return resultObj; 
	}*/
	
	/**
	 * 多服务停止
	 * @param services
	 * @return
	 */
	public ResultObj stopServices(List<MsServicesReg> services) throws Exception{
		String url = MsConfigProperties.ARCGIS_ADMIN_SERVICES_URL+"/stopServices";
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("token", ServiceToken.getToken());
		dataMap.put("f", "json");
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		for(MsServicesReg reg : services){
			Map<String,String> map = new HashMap<String,String>();
			String folder = reg.getFolder().equals("/") ? reg.getFolder() : reg.getFolder().substring(reg.getFolder().lastIndexOf("/")+1,reg.getFolder().length());
			map.put("folderName", folder);
			map.put("serviceName", reg.getServicename());
			map.put("type", reg.getServicetype());
			mapList.add(map);
		}
		Map<String,Object> servicesMap = new HashMap<String,Object>();
		servicesMap.put("services", mapList);
		String servicesStr = JSONObject.fromObject(servicesMap).toString();
		dataMap.put("services",servicesStr);
		HttpRequestor hr = new HttpRequestor();
		String s = hr.doPost(url, dataMap);
		JSONObject obj = JSONObject.fromObject(s);
		ResultObj result = (ResultObj) JSONObject.toBean(obj, ResultObj.class);
		return result;
	}
	
	/**
	 * 单服务停止
	 * @param services
	 * @return
	 */
	public ResultObj stopService(MsServicesReg service){
		String url = MsConfigProperties.ARCGIS_ADMIN_SERVICES_URL+"/"+service.getFolder()+"/"+service.getServicename()+"."+service.getServicetype()+"/stop";
		return null;
	}
	
	/**
	 * 服务删除
	 * @param services
	 * @return
	 */
	public ResultObj deleteService(MsServicesReg service) throws Exception{
		String url = MsConfigProperties.ARCGIS_ADMIN_SERVICES_URL+"/"+service.getFolder()+"/"+service.getServicename()+"."+service.getServicetype()+"/delete";
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("token", ServiceToken.getToken());
		dataMap.put("f", "json");
		HttpRequestor hr = new HttpRequestor();
		String s = hr.doPost(url, dataMap);
		JSONObject obj = JSONObject.fromObject(s);
		ResultObj result = (ResultObj) JSONObject.toBean(obj, ResultObj.class);
		return result;
	}
	
	/**
	 * 验证服务是否存在
	 * @param service
	 * @return
	 * @throws Exception
	 */
	public Boolean existsService(String serviceName,String serviceType,String folderName) throws Exception{
		String url = MsConfigProperties.ARCGIS_ADMIN_SERVICES_URL+"/exists";
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("token", ServiceToken.getToken());
		dataMap.put("folderName", folderName);
		dataMap.put("serviceName", serviceName);
		dataMap.put("type", serviceType);
		dataMap.put("f", "json");
		HttpRequestor hr = new HttpRequestor();
		String s = hr.doPost(url, dataMap);
		JSONObject obj = JSONObject.fromObject(s);
		Map map = obj;
		String exists = map.get("exists").toString();
		if(exists.equals("false"))
			return false;
		else
			return true;
	}
	
	
    
	/**
     * 获取图层列表（根据服务的转发访问地址)
     * @author fujl 2016-11-19
     * @time 2016-11-19 22:11
     * @param
     * serviceTransferUrl:服务转发访问地址
     * */
	public String getLayerListByMapService(String serviceTransferUrl){
		try{	
			HttpRequestor hr = new HttpRequestor();

			String s = hr.doGet(ARCGIS_URL+"rest/services"+serviceTransferUrl+"/?f=pjson");
			JSONObject obj = JSONObject.fromObject(s);
			return obj.get("layers").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * 获取图层字段（根据图层ID)
     * @author fujl 2016-11-19
     * @time 2016-11-19 22:11
     * @param
     * serviceTransferUrl:服务转发访问地址
     * layerId:图层ID
     * */
	public String getLayerFieldsByLayerName(String serviceTransferUrl,String layerId){
		try{	
			HttpRequestor hr = new HttpRequestor();
			
			//System.out.println(ARCGIS_URL+"rest/services/"+serviceTransferUrl+"/"+layerId+"?f=pjson");
			
			String s = hr.doGet(ARCGIS_URL+"rest/services"+serviceTransferUrl+"/"+layerId+"?f=pjson");
			//System.out.println(s);
			//int i = s.indexOf("fields");
			//String s1 =  s.substring(0,s.indexOf("currentVersion")) + s.substring(s.indexOf("fields"));
			String s1 =  "{"+ s.substring(s.indexOf("fields")-1,s.indexOf("relationships")-3) + "}";
			//System.out.println(s1);
			JSONObject obj = JSONObject.fromObject(s1);
			
			//System.out.println(ARCGIS_URL+"rest/services/"+serviceTransferUrl+"/"+layerId+"?f=pjson");
			return obj.get("fields").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
