package com.proxy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fh.entity.ms.logs.MsServicesLogMonitor;
import com.fh.service.ms.logs.MsServicesLogMonitorServiceImpl;
import com.proxy.entity.Buffer;
import com.proxy.filter.parser.AutoRequestParserImpl;
import com.proxy.filter.parser.RequestParser;
import com.proxy.filter.parser.RequestParserInterface;
import com.proxy.filter.parser.RestRequestParserImpl;
import com.proxy.filter.wrapper.AccessMonitorServletResponseWrapper;
import com.proxy.filter.wrapper.BufferedServletRequestWrapper;
import com.proxy.util.DateUtil;
import com.proxy.util.ServiceUtil;
import com.proxy.util.SpringHelper;
import com.proxy.util.TokenUtils;

/**
 * <p>
 * Title: 服务緩存分析过滤 <br>
 * Description: 系统入口
 * </p>
 * 
 * @copyright: Copyright (c) ESRI China (BeiJing) Co.Ltd All rights reserved
 * @company: ESRI China (BeiJing)
 * @author: even hao
 * @version: 1.0
 * @createTime: 2009-12-10
 * @lastUpdateTime: 2011-03-15
 */
public class ServicesMonitor extends HttpServlet implements Filter {
	private static final long serialVersionUID = 8341083378604969538L;
	private static Log log = LogFactory.getLog(ServicesMonitor.class);

	private static RequestParserInterface requestPraser = new RestRequestParserImpl();

	// Handle the passed-in FilterConfig
	public void init(FilterConfig filterConfig) throws ServletException {

		requestPraser = new AutoRequestParserImpl();
	}

	// Process the request/response pair
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if (null != request.getAttribute("isSecurityValidated") && false == (boolean) request.getAttribute("isSecurityValidated")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		java.util.Date start = new java.util.Date();
		HttpServletRequest req0 = (HttpServletRequest) request;
		HttpServletResponse resp0 = (HttpServletResponse) response;
		if(!StringUtils.isEmpty(req0.getHeader("FromRobot"))){
			filterChain.doFilter(req0, resp0);
		}
		// Wrapper request && response
		BufferedServletRequestWrapper req = new BufferedServletRequestWrapper(req0);
		AccessMonitorServletResponseWrapper resp = new AccessMonitorServletResponseWrapper(resp0);
		
		long beginTime = System.currentTimeMillis();

		// 客户端IP
		String remoteIP = RequestParser.getIpAddr(req);

		try {
			Buffer reqBuffer = requestPraser.parse(req);

			try {
				if (reqBuffer != null) {
					if (reqBuffer.getUserName() == null || "".equals(reqBuffer.getUserName())) {
						reqBuffer.setUserName(TokenUtils.getUserNameFromToken(req));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			java.util.Date end = new java.util.Date();
			log.debug("monitor run1	" +(end.getTime() - start.getTime()));
			// -----before
			filterChain.doFilter(req, resp);
			// -----after
			java.util.Date start1 = new java.util.Date();
			
			// --获取请求访问时间和返回状态
			if (reqBuffer != null && reqBuffer.getServiceName() != null && reqBuffer.getServiceName().trim().equals("") == false) {
				long delayTime = System.currentTimeMillis() - beginTime;
				reqBuffer.setAccessTime(delayTime);
				reqBuffer.setHttpStatus(resp.getStatus());
				
				if (0 == reqBuffer.getServiceID() && !StringUtils.isEmpty(reqBuffer.getServiceName())) {
					Long serviceId = ServiceUtil.getServices().get(reqBuffer.getServiceName()).getServiceid();
					reqBuffer.setServiceID(serviceId.intValue());
				}
				
				try {
					// --写入日志文件(log4j)
					addJsonLog(reqBuffer);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("存入日志错误:" + e.getMessage());
				}
				log.debug("----------------Reqeust Delay(ms): " + delayTime);
			}
			end = new java.util.Date();
			log.debug("monitor run2	" +(end.getTime() - start1.getTime()));
			log.debug("run total	" +(end.getTime() - start.getTime()));
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("监控异常：" + e.getMessage());
		}
		
		return;
	}

	/**
	 * 将接收到的请求信息以JSON格式写入日志文件
	 * 
	 * @param aRequest
	 *            - 请求信息对象
	 */
	private static void addJsonLog(Buffer buf) {
		// if (buf != null && buf.getLocalHostIP() != null &&
		// !buf.getLocalHostIP().equals("") && buf.getServiceName() != null
		// && !buf.getServiceName().trim().equals("")) {
		if (buf != null && buf.getLocalHostIP() != null && !buf.getLocalHostIP().equals("")) {
			//TODO INSERT TO DB.
			MsServicesLogMonitorServiceImpl monitorService = SpringHelper.getBean(MsServicesLogMonitorServiceImpl.class);
			try {
				monitorService.saveObj(convert(buf));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static MsServicesLogMonitor convert(Buffer buf) {
		MsServicesLogMonitor monitor = new MsServicesLogMonitor();
		monitor.setServiceid(buf.getServiceID());
		monitor.setServicename(buf.getServiceName());
		monitor.setServicetype(buf.getServiceType());
		monitor.setRequestid((int) buf.getRequestID());
		monitor.setRequesttime(DateUtil.getDate(buf.getRequestTime(), "yyyy-MM-dd HH:mm:ss"));
		monitor.setToken(buf.getToken());
		monitor.setRequesturi(buf.getRequestUri());
		monitor.setRemotehostip(buf.getRemoteHostIP());
		monitor.setRemotehostport(buf.getRemoteHostPort() + "");
		monitor.setLocalhostip(buf.getLocalHostIP());
		monitor.setLocalhostport(buf.getLocalHostPort() + "");
		monitor.setMethod(buf.getServiceMethod());
		monitor.setParam(buf.getServiceMethodParams());
		monitor.setMemo("");
		monitor.setRequser(buf.getUserName());
		monitor.setAccesstime(buf.getAccessTime().intValue());
		monitor.setHttpstatus(buf.getHttpStatus());
		monitor.setFromserver(buf.getRemoteHostIP());
		return monitor;
	}

	// Clean up resources
	public void destroy() {
	}
}
