package com.proxy.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.esri.rf.util.RUtil;
import com.proxy.manager.entity.RequestInfo;
import com.proxy.manager.exception.ServiceNotFoundException;
import com.proxy.manager.security.ParameterRequestWrapper;
import com.proxy.util.ServiceUtil;
import com.proxy.util.Util;
/**
 * This class should preserve.
 * 
 * @preserve public
 */
public class ServicesAdaptorProxy extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(ServicesAdaptorProxy.class.getName());
	public String charset = "utf-8";
	
	public void init(ServletConfig config) throws ServletException {

	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (null != request.getAttribute("isSecurityValidated") && false == (boolean) request.getAttribute("isSecurityValidated")) {
			return;
		}
		
		java.util.Date start = new java.util.Date();
		InputStream is = null;
		OutputStream os = null;
		URL newURL = null;
		HttpURLConnection con = null;
		try {
			if (request.getRequestURL() == null) {
				throw new Exception("请求地址为空");
			}
			
			
			log.debug("-----------------服务请求地址：" + request.getRequestURL());
			String xForURLBase = getClientIPFromHeader(request);
			String requestContext = Util.getAppURL(request);
			RequestInfo info = null;
			try {
				info = new ServiceUtil().getRequetInfo(request);
			} catch (ServiceNotFoundException e) {
				responseNoSecurity(request, response, 401, e.getMessage());
				return;
			}
			
			int statusCode = 0;
			
			log.debug("-----------------服务请求实际地址：" + info.getRealURL());
			newURL = new URL(info.getRealURL().replace("wsdl=", "wsdl").replace("WSDL=", "WSDL"));
			
			log.debug("-----------------增加属性后服务实际地址：" + newURL);
			con = (HttpURLConnection) newURL.openConnection();
			con.setRequestProperty("Accept-Charset", "utf-8");
			con.setRequestProperty("contentType", "utf-8");
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod(request.getMethod());
			con.setConnectTimeout(60000);
			con.setReadTimeout(120000);
			Enumeration reqhs = request.getHeaderNames();
			boolean isrefer = false;
			boolean hasContentType = false;
			boolean hasAccept = false;
			while (reqhs.hasMoreElements()) {
				String h = (String) reqhs.nextElement();
				if (h != null) {
					con.setRequestProperty(h, request.getHeader(h));
				}
				if (StringUtils.equalsIgnoreCase(h, "referer")) {
					if (!StringUtils.isEmpty(request.getHeader(h))) {
						isrefer = true;
					}
				}
				
				if (StringUtils.equalsIgnoreCase("Content-Type", h)) {
					hasContentType = true;
				}
				if (StringUtils.equalsIgnoreCase("Accept", h)) {
					hasAccept = true;
				}
				
			}
			String username = request.getRemoteUser();
			if (!StringUtils.isEmpty(username)) {
				con.setRequestProperty("REMOTE_USER", username);
			}
			if (!StringUtils.isEmpty(xForURLBase)) {
				con.setRequestProperty("x-forwarded-for", xForURLBase);
			}
			if (!StringUtils.isEmpty(requestContext)) {
				con.setRequestProperty("X-Forwarded-Request-Context", requestContext);
			}
			if (!isrefer) {
				if (request.getAttribute("tokenrefer") != null)
					con.setRequestProperty("referer", request.getAttribute("tokenrefer").toString());
				else
					con.setRequestProperty("referer", requestContext);
			}
			
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				con.setUseCaches(false);
				con.setDoOutput(true);
				is = request.getInputStream();
				os = con.getOutputStream();
				if (is.available() > 0) {
					IOUtils.closeQuietly(is);
					IOUtils.copy(is, os);
					IOUtils.closeQuietly(is);
				} else {
					HashMap map = new HashMap(request.getParameterMap());
					ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(request, map, true);
					is = wrapRequest.getInputStream();
					IOUtils.copy(is, os);
					IOUtils.closeQuietly(is);
				}
				
				IOUtils.closeQuietly(os);
			}
			con.connect();
			statusCode = con.getResponseCode();
			log.debug("-----------------服务实际地址返回状态：" + statusCode);
			try {
				is = con.getInputStream();
			} catch (Exception ignore) {
				is = con.getErrorStream();
			}
			if (is == null) {
				is = con.getErrorStream();
			}
			os = response.getOutputStream();
			
			String contenttype = null, contentencoding = null;
			if (con.getContentType() != null) {
				contenttype = con.getContentType();
				response.setContentType(con.getContentType());
			} else {
				response.setContentType(charset);
			}
			if (con.getResponseCode() > 0) {
				response.setStatus(con.getResponseCode());
			}
			if (con.getContentEncoding() != null) {
				contentencoding = con.getContentEncoding();
			}
			Map resultHeaders = con.getHeaderFields();
			String resultHeader;
			String contentLengthValue = "0";
			boolean haschunked = false;
			for (Iterator<String> i$ = resultHeaders.keySet().iterator(); i$.hasNext();) {
				resultHeader = i$.next();
				if (resultHeader != null) {
					List<String> headerValues = (List<String>) resultHeaders.get(resultHeader);
					if (headerValues != null){
						for (String headerValue : headerValues) {
							log.debug("-----------------服务返回头信息：" + headerValue);
							if ((((statusCode == 302) || (statusCode == 303))) && ("Location".equalsIgnoreCase(resultHeader)) && (!StringUtils.isEmpty(headerValue))) {
								log.debug("-----------------服务转发原始地址：" + headerValue);
								headerValue = Util.replaceServiceUrl(headerValue, info);
								URL serverURL = new URL(headerValue);
								if ("https".equalsIgnoreCase(newURL.getProtocol())) {
									headerValue = headerValue.replaceFirst(serverURL.getProtocol(), newURL.getProtocol());
								}
								log.debug("-----------------服务转发修正地址：" + headerValue);
								response.setHeader(resultHeader, headerValue);
							} else if (!"chunked".equalsIgnoreCase(headerValue)) {
								if ("Content-Length".equalsIgnoreCase(resultHeader)) {
									contentLengthValue = headerValue;
								} else {
									response.setHeader(resultHeader, headerValue);
								}
							}
							
						}
					}
				}
			}
		
			if (contenttype != null && contenttype.indexOf("text/") > -1) {
				Util.writeResults(is, os, info, contentencoding, response, contentLengthValue);
			} else {
				if (!"0".equals(contentLengthValue))
					response.setHeader("Content-Length", contentLengthValue);
				IOUtils.copy(is, os);
			}
			
		} catch (Exception e) {
			response.sendError(500, "访问失败!" + e.getMessage());
			return;
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
			if (con != null)
				con.disconnect();
		}
		java.util.Date end = new java.util.Date();
		log.debug("proxy run	" + (end.getTime() - start.getTime()));
	}
	
	public void destroy() {
		
	}
	
	private boolean oneMapUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI().replace(request.getContextPath(), "");
		if (path.startsWith("/onemap/")) {
			if (path.equals("/onemap/admin") || path.equals("/onemap/clearCache") || path.equals("/onemap/setting")) {
				RequestDispatcher rd = request.getRequestDispatcher(path + ".jsp");
				rd.forward(request, response);
				return false;
			} else {
				return false;
			}
		}
		if (path.indexOf("/rest/services") == 0) {
			String[] tmp = path.split("/");
			if (tmp.length ==4 || (tmp.length == 5 && StringUtils.isEmpty(tmp[4]))) {
				responseNoSecurity(request, response, 404, "请求地址错误");
				return false;
			}else if (tmp.length == 3){
				path = "/rest/services";
			}
		}
		if ("/rest/services".equalsIgnoreCase(path) || "/rest/logout".equalsIgnoreCase(path) || "/rest/login".equalsIgnoreCase(path)
				|| "/rest/info".equalsIgnoreCase(path)) {
			RequestDispatcher rd = request.getRequestDispatcher(path.replace("rest", "onemap") + ".jsp");
			rd.forward(request, response);
			return false;
		} else {
			return true;
		}
		
	}
	
	private String getClientIPFromHeader(HttpServletRequest request) throws ServletException, IOException {
		String clientId = "";
		String ip = request.getRemoteAddr();
		if (ip != null)
			clientId = ip;
		else {
			log.warn("没有找到ip地址");
		}
		/*
		String xFwdForHost = request.getHeader("x-forwarded-for");
		if (xFwdForHost != null) {
			if (xFwdForHost.indexOf(clientId) < 0) {
				clientId = xFwdForHost + "," + clientId;
			} else {
				clientId = xFwdForHost;
			}
		}*/
		return clientId;
	}
	private void responseNoSecurity(HttpServletRequest req, HttpServletResponse response, int httpCode, String errorMsg) throws IOException {
		response.setStatus(httpCode);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String f = RUtil.getParamIgnoreCase("f", req);
		if ("pjson".equalsIgnoreCase(f) || "json".equalsIgnoreCase(f)) {
			response.setStatus(200);
			String json = "{\"error\":{\"code\":" + httpCode + ",\"message\":\"" + errorMsg + "\",\"details\":[]}}";
			
			if( req.getParameter("callback")!=null && !req.getParameter("callback").trim().equals("")){
				json = req.getParameter("callback") + "(" + json + ");";
			}
			
			PrintWriter pw = response.getWriter();
			pw.println(json);
			pw.flush();
			pw.close();
		}  else {
			if (httpCode == 403 || httpCode == 499)
				response.sendRedirect(req.getContextPath() + "/rest/login?redirect=" + req.getRequestURL().toString()+"&errmsg="+errorMsg);
			else {
				PrintWriter pw = response.getWriter();
				pw.println("Error Code:\t" + httpCode);
				pw.println("<br/>");
				pw.println("Mseeage:\t" + errorMsg);
				pw.flush();
				pw.close();
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 500; i++) {
			Thread t = new Thread(new ServicesAdaptorProxy().new ThreadUseRunnable(i), "SecondThread");
			t.start();
		}
	}
	
	class ThreadUseRunnable implements Runnable {
		private int i = 0;
		
		public ThreadUseRunnable(int i) {
			this.i = i;
		}
		
		public void run() {
			java.util.Date start = new java.util.Date();
			InputStream is = null;
			OutputStream os = null;
			URL newURL = null;
			HttpURLConnection con = null;
			try {
				int statusCode = 0;
				
				newURL = new URL("http://192.168.90.83:6080/arcgis/rest/services/SampleWorldCities/MapServer");
				
				// log.debug("-----------------增加属性后服务实际地址：" + newURL);
				con = (HttpURLConnection) newURL.openConnection();
				con.setRequestProperty("Accept-Charset", "utf-8");
				con.setRequestProperty("contentType", "utf-8");
				con.setInstanceFollowRedirects(false);
				con.setRequestMethod("GET");
				con.setConnectTimeout(60000);
				con.setReadTimeout(120000);
				con.connect();
				statusCode = con.getResponseCode();
				// log.debug("-----------------服务实际地址返回状态：" + statusCode);
				try {
					is = con.getInputStream();
				} catch (Exception ignore) {
					is = con.getErrorStream();
				}
				if (is == null) {
					is = con.getErrorStream();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return;
			} finally {
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(os);
				if (con != null)
					con.disconnect();
			}
			java.util.Date end = new java.util.Date();
			log.debug("proxy run " + this.i + " ------------------------ " + (end.getTime() - start.getTime()));
		}
	}
}