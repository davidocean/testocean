package com.proxy.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fh.entity.ms.services.MsServicesReg;
import com.fh.service.ms.logs.MsServicesSecurityService;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.mapone.MsConfigProperties;
import com.proxy.dao.ServiceDao;
import com.proxy.manager.entity.CommonCode;
import com.proxy.manager.entity.RequestInfo;
import com.proxy.manager.entity.ServiceInfo;
import com.proxy.manager.entity.ServiceParserInfo;
import com.proxy.manager.exception.ServiceNotFoundException;

public class ServiceUtil {
	private static Log log = LogFactory.getLog(ServiceUtil.class);
	public static String charset = "utf-8";
	private static Map<String, ServiceInfo> services = new HashMap<String, ServiceInfo>();
	
	public enum ResponseTypeEnum {
		json, xml
	}
	
	public enum ArcGISEnum {
		ags10, ags101, others
	}
	
	public static String getAppURL(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String cp = request.getContextPath();
		return url.substring(0, url.indexOf(cp) + cp.length());
	}
	
	public RequestInfo getRequetInfo(HttpServletRequest request) throws ServiceNotFoundException {
		String url = request.getRequestURL().toString();
		String cp = request.getContextPath();
		
		RequestInfo info = new RequestInfo();
		info.setRequestURL(url);
		info.setRequestContextPath(cp);
		info.setRequestHttp(url.substring(0, url.indexOf(cp)));
		info.setHttpprotocol(request.isSecure() ? "https" : "http");
		
		String uri = request.getRequestURI();
		String[] ps = uri.split("/");
		info.setProtocol(ps[2]);
		
		if (info.getProtocol() == RequestInfo.ProtocolEnum.dispatch) {
			String realhttp = "/" + ps[3];
			String other = uri.substring(uri.indexOf(realhttp) + (realhttp).length());
			info.setRealhttp(new StringBuffer().append(info.getHttpprotocol()).append(":/").append(realhttp).toString());
			info.setRealURL(new StringBuffer().append(info.getRealhttp()).append(other).toString());
			return info;
		} else {
			ServiceParserInfo parserinfo = RequestURIParserFactory.getRequestURIParser().parse(request);
			if (parserinfo == null || StringUtils.isEmpty(parserinfo.getServicename()))
				return info;
			info.setServicename(parserinfo.getOnemapServicename());
			info.setServicetype(parserinfo.getServicetype());
			
			ServiceInfo service = null;
			try {
				// service =
				// CacheDataManager.getServiceInfo(info.getServicename());
				// TODO get service.
				
				service = queryService(info.getServicename());
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			if (service == null) {
				log.error("没有找到对应服务" + info.getServicename() + "，请确定服务是否存在！");
				throw new ServiceNotFoundException("没有找到对应服务，请确定服务是否存在！");
			} else {
				
				// 封装服务
				String serUri = info.getRequestHttp() + info.getRequestContextPath() + "/";
				//by David.Ocean 将争取的URL地址附上，取消Transferrul的地址
				service.setRealurl(MsConfigProperties.ARCGIS_URL  + service.getInresturl().replace("/rest", "rest"));
				//service.setRealurl(MsConfigProperties.ARCGIS_URL + "rest/services" + service.getTransferurl());
				service.setSoapurl(service.getRealurl().replace("rest/", ""));
				service.setClusterInResturl(MsConfigProperties.ARCGIS_URL + "/rest/services");
				service.setClusterInSoapurl(MsConfigProperties.ARCGIS_URL + "/services");
				service.setOnemaprealurl(url);
				service.setOnemapsoapurl(url.replace("rest/", "soap/") + "?WSDL");
				service.setFunctiontype(CommonCode.AGS_FUNCTION_TYPE.get(service.getServicetype())); // For
																										// resource
																										// dispatch.
				service.setServicetype(CommonCode.AGS_SERVICE_TYPE); // For
																		// resource
																		// dispatch.
				if ("".equals(service.getClustercode()) || "default".equalsIgnoreCase(service.getClustercode())) {
					service.setClustercode(CommonCode.DEFAULT_CLUSTER_CODE);
				}
			}
			
			info.setServiceinfo(service);
			String serviceurl = null, clusterurl = null;
			if (info.getProtocol() == RequestInfo.ProtocolEnum.rest) {
				serviceurl = service.getRealurl();
				clusterurl = service.getClusterInResturl();
			} else if (info.getProtocol() == RequestInfo.ProtocolEnum.soap) {
				serviceurl = service.getSoapurl();
				clusterurl = service.getClusterInSoapurl();
			}
			if (StringUtils.isEmpty(serviceurl) || StringUtils.isEmpty(clusterurl)) {
				log.error("服务访问地址错误，请与系统管理员联系！");
				throw new ServiceNotFoundException("服务访问地址错误，请与系统管理员联系！");
			}
			String[] realservice = serviceurl.replace(clusterurl.replace("//rest","/rest") + "/", "").split("/");
			String realservicetype = null, realservicename = null, realservicefolder = "";
			if (realservice.length == 2) {
				realservicename = realservice[0];
				realservicetype = info.getServicetype();
			} else if (realservice.length == 3) {
				realservicefolder = realservice[0] + "/";
				realservicename = realservice[1];
				realservicetype = info.getServicetype();
			} else if (realservice.length == 1) {
				realservicename = realservice[0];
				realservicetype = "";
			}else {
				realservicename = realservice[6];
			}
			info.setRealFolder(realservicefolder);
			info.setRealServicename(realservicename);
			// info.setRealServicetype(realservicetype);
			info.setRealServicetype(realservicetype);
			String other = null;
			if (!StringUtils.isEmpty(realservicetype)) {
				serviceurl = serviceurl.substring(0, serviceurl.lastIndexOf("/"));
				other = uri.substring(uri.indexOf("/" + info.getServicename()) + info.getServicename().length() + 1, uri.length());
			} else {
				other = uri.substring(uri.indexOf("/" + info.getServicename()) + info.getServicename().length() + 2 + info.getServicetype().length(), uri.length());
			}
			StringBuffer sb = new StringBuffer();
			sb.append(serviceurl).append(other);
			// Deprecated for old token. 2016.12.18 
			// Different from ags token. 
			// remove token param.
			
			if (request.getQueryString() != null) {
				String queryStr = request.getQueryString();
				String token = request.getParameter("maptoken");
				/*if (!StringUtils.isEmpty(token)) {
					String substr = "maptoken=" + token;
					queryStr = queryStr.replace(substr, "").replace("&&", "&");
					if (!StringUtils.isEmpty(queryStr) && queryStr.lastIndexOf("&") == queryStr.length() - 1) {
						queryStr = queryStr.substring(0, queryStr.length() - 1);
					}
					if (!StringUtils.isEmpty(queryStr) && queryStr.indexOf("&") == 0) {
						queryStr = queryStr.substring(1, queryStr.length());
					}
					System.out.println(queryStr);
				}*/
				sb.append("?").append(queryStr);
			}
			info.setRealURL(sb.toString());
			
			String realhttp = serviceurl.substring(0, serviceurl.indexOf("/", serviceurl.indexOf("//") + 2));
			info.setRealhttp(realhttp);
			String tmp = serviceurl.substring(realhttp.length(), serviceurl.length());
			info.setRealContextPath("/" + tmp.split("/")[1]);
			if (!StringUtils.isEmpty(info.getRealServicetype())) {
				info.setRealServiceURL(tmp.substring(info.getRealContextPath().length()) + "/" + info.getRealServicetype());
			} else {
				info.setRealServiceURL(tmp.substring(info.getRealContextPath().length()));
			}
			info.setClusterurl(clusterurl);
			info.setRequestServiceURL(uri.substring(cp.length(), uri.indexOf("/" + info.getServicename()) + info.getServicename().length() + 1) + "/" + info.getServicetype());
			String methodname = url.substring(url.lastIndexOf("/") + 1);
			if (methodname.indexOf("?") > 0) {
				methodname = methodname.substring(0, methodname.indexOf("?"));
			}
			info.setMethodname(methodname);
		}
		return info;
	}
	
	public static Map<String, ServiceInfo> getServices(){
		if (services.size() == 0) {
			queryAllService();
		}
		return services;
	}
	
	public static ArcGISEnum isArcGISService(String servicetype) {
		if (CommonCode.AGS10_SERVICE_TYPE.equals(servicetype)) {
			return ArcGISEnum.ags10;
		} else if (CommonCode.AGS101_SERVICE_TYPE.equals(servicetype)) {
			return ArcGISEnum.ags101;
		} else {
			return ArcGISEnum.others;
		}
	}
	
	public static void responseWrite(HttpServletResponse response, String con, ResponseTypeEnum type) throws IOException {
		response.setCharacterEncoding("utf-8");
		if (type == ResponseTypeEnum.json) {
			response.setContentType("text/html;charset=utf-8");
		} else if (type == ResponseTypeEnum.xml) {
			response.setContentType("text/xml;charset=utf-8");
		} else {
			response.setContentType("text/html;charset=utf-8");
		}
		
		PrintWriter pw = response.getWriter();
		pw.println(con);
		pw.flush();
		pw.close();
	}
	
	public ServiceInfo queryService(String servicename) throws Exception {
		if (!services.containsKey(servicename)) {
			queryAllService();
		}
		// GET FRESH SERVICE
		ServiceDao dao = SpringHelper.getBean(ServiceDao.class);
		MsServicesReg msr = dao.queryByName(servicename);
		ServiceInfo si = new ServiceInfo();
		si.setServiceid((long) msr.getId());
		si.setServicename(msr.getServicename());
		si.setServicefullname(msr.getServicefullname());
		si.setServicetype(msr.getServicetype());
		si.setFullpermission(msr.getFullpermission());
		si.setClustercode(msr.getClustercode());
		si.setTransferurl(msr.getTransferurl());
		//by David.Ocean 将native(ArcGIS Server正确地址的后半段)和地址获取到。
		si.setInresturl(msr.getNativeurl());
		return si;
		
		/*
		if (!services.containsKey(servicename)) {
			queryAllService();
		}
		ServiceInfo si = services.get(servicename);
		return si;*/
	}
	
	public synchronized static void queryAllService(){
		// TODO
		// query all services.
		ServiceDao dao = SpringHelper.getBean(ServiceDao.class);
		List<MsServicesReg> list;
		try {
			list = dao.queryAllService();
			services.clear();
			for (int i = 0; i < list.size(); i++) {
				MsServicesReg msr = list.get(i);
				ServiceInfo si = new ServiceInfo();
				si.setServiceid((long) msr.getId());
				si.setServicename(msr.getServicename());
				si.setServicefullname(msr.getServicefullname());
				si.setServicetype(msr.getServicetype());
				si.setFullpermission(msr.getFullpermission());
				si.setClustercode(msr.getClustercode());
				si.setTransferurl(msr.getTransferurl());
				services.put(si.getServicename(), si);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean serviceSecurityValidate(String servicename, String username) {
		boolean isValid = false;
		MsServicesSecurityService msServicesSecurityService = SpringHelper.getBean(MsServicesSecurityService.class);
		PageData pd = new PageData();
		pd.put("servicename", servicename);
		pd.put("username", username);
		try {
			PageData pdResult = msServicesSecurityService.getByUserServiceName(pd);
			
			PageData pdRole = msServicesSecurityService.getUserroleByName(pd);
			// role_id in '3264c8e83d0248bb9e3ea6195b4c0216' or '1' which means 'manager role' and have access to all services.
			if (("3264c8e83d0248bb9e3ea6195b4c0216".equals(pdRole.get("role_id"))
					|| "1".equals(pdRole.get("role_id")))
					|| null != pdResult 
					) {				
				isValid = true;
			}else {
				isValid = false;
			}
			
		} catch (Exception e) {
			isValid = false;
			e.printStackTrace();
		}
		return isValid;
	}
	
	public static void main(String[] args) {
		
		String queryStr = "";
		String token = "eyJhbGciOiJIUzI1NiJ9";
		if (!StringUtils.isEmpty(token)) {
			String substr = "token=" + token;
			queryStr = "token=eyJhbGciOiJIUzI1NiJ9&aaa=bbb&ccc=ddd&eee=fff".replace(substr, "").replace("?&", "?").replace("&&", "&");
			if (queryStr.lastIndexOf("&") == queryStr.length() - 1) {
				queryStr = queryStr.substring(0, queryStr.length() - 1);
			}
			if (queryStr.indexOf("&") == 0) {
				queryStr = queryStr.substring(1, queryStr.length());
			}
			System.out.println(queryStr);
		}
		
	}
}
