/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: SecurityFilter.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月27日 下午6:25:18
 * @version: V1.0  
 */
package com.proxy.filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.esri.rf.util.RUtil;
import com.fh.util.StringUtil;
import com.proxy.manager.entity.RequestInfo;
import com.proxy.manager.exception.ServiceNotFoundException;
import com.proxy.manager.security.ParameterRequestWrapper;
import com.proxy.util.EncodingUtil;
import com.proxy.util.SecurityUtils;
import com.proxy.util.ServiceUtil;

/**
 * @author Jes
 * 
 */
public class SecurityFilter implements Filter {
	
	private String tokenrefer = null;
	private String tokenip = null;
	transient ServletContext servletContext;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) paramServletRequest;
		HttpServletRequest originalreq = this.copyRequest(request);
		HttpServletResponse response = (HttpServletResponse) paramServletResponse;
		
		try {
			RequestInfo info = null;
			try {
				info = new ServiceUtil().getRequetInfo(request);
			} catch (ServiceNotFoundException e) {
				chain.doFilter(originalreq, response);
				return;
			}
			
			if (info == null || info.getServicename() == null) {
				// 没有获取到serviceName直接跳过
				responseNoSecurity(request, response, 401, "Service not found.");
				return;
			}
			
			// 是否为安全服务
			boolean isSecurityService = !info.getServiceinfo().isFreeService();
			if (!isSecurityService) {
				chain.doFilter(originalreq, response);
				return;
			}
			
			String maptoken = request.getParameter("maptoken");
			// if maptoken from request parameter is null,try to get maptoken
			// from request header.
			if (StringUtils.isEmpty(maptoken)) {
				maptoken = SecurityUtils.getCookie(request, "maptoken");
			}
			Map<String, String> map = null;
			try {
				map = SecurityUtils.validate(maptoken, info.getServicename());
			} catch (Exception e) {
				e.printStackTrace();
				//responseNoSecurity(request, response, 403, "maptoken validation failed.");
				response.sendRedirect(request.getContextPath() + "/rest/login.html?redirect=" + EncodingUtil.encodeURIComponent(SecurityUtils.getFullURL(request)));
				return;
			}
			
			if (null == map) {
				//responseNoSecurity(request, response, 403, "maptoken validation failed.");
				response.sendRedirect(request.getContextPath() + "/rest/login.html?redirect=" + EncodingUtil.encodeURIComponent(SecurityUtils.getFullURL(request)));
				return;
			}
			
			if ("0".equals(map.get("isValidated"))) {// 验证失败
				//responseNoSecurity(request, response, 403, map.get("msg"));
				response.sendRedirect(request.getContextPath() + "/rest/login.html?errmsg=" + map.get("msg") + "&redirect=" + EncodingUtil.encodeURIComponent(SecurityUtils.getFullURL(request)));
				return;
			}
			// if maptoken validated,set cookie in browser.
			//response.addHeader("Set-Cookie", "maptoken=" + maptoken + "; Path=/;");
			// 通过权限验证
			request.setAttribute("validated_username", map.get("username"));
			chain.doFilter(originalreq, response);
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			chain.doFilter(originalreq, response);
			return;
		}
		
	}
	
	private void responseNoSecurity(HttpServletRequest req, HttpServletResponse response, int httpCode, String errorMsg) throws IOException {
		req.setAttribute("isSecurityValidated", false);
		response.setStatus(httpCode);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String f = RUtil.getParamIgnoreCase("f", req);
		if ("pjson".equalsIgnoreCase(f) || "json".equalsIgnoreCase(f)) {
			response.setStatus(200);
			String json = "{\"error\":{\"code\":" + httpCode + ",\"message\":\"" + errorMsg + "\",\"details\":[]}}";
			
			if (req.getParameter("callback") != null && !req.getParameter("callback").trim().equals("")) {
				json = req.getParameter("callback") + "(" + json + ");";
			}
			
			PrintWriter pw = response.getWriter();
			pw.println(json);
			pw.flush();
			pw.close();
		} else if ("image".equalsIgnoreCase(f) || req.getRequestURI().indexOf("/tile/") > 0) {
			try {
				this.writeErrorResponse(req, response, true);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else {
			PrintWriter pw = response.getWriter();
			pw.println("Error Code:\t" + httpCode);
			pw.println("<br/>");
			pw.println("Mseeage:\t" + errorMsg);
			pw.flush();
			pw.close();
			
		}
		
	}
	
	private void writeErrorResponse(HttpServletRequest request, HttpServletResponse response, boolean text) throws IOException, ServletException {
		String rqFormat = RUtil.getParamIgnoreCase("f", request);
		if ("image".equals(rqFormat) || request.getRequestURI().indexOf("/tile/") > 0) {
			response.setContentType("image/jpeg");
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 2000);
			try {
				String[] size;
				if (StringUtils.isEmpty(RUtil.getParamIgnoreCase("size", request))) {
					size = new String[] { "400", "400" };
				} else {
					size = RUtil.getParamIgnoreCase("size", request).split(",");
				}
				BufferedImage bi = new BufferedImage(Integer.parseInt(size[0]), Integer.parseInt(size[1]), BufferedImage.TYPE_INT_RGB);
				
				Graphics2D g2d = bi.createGraphics();
				
				// ---------- 增加下面的代码使得背景透明 -----------------
				bi = g2d.getDeviceConfiguration().createCompatibleImage(Integer.parseInt(size[0]), Integer.parseInt(size[1]), Transparency.TRANSLUCENT);
				g2d.dispose();
				g2d = bi.createGraphics();
				// ---------- 背景透明代码结束 -----------------
				if (text) {
					g2d.setFont(new Font("宋体", Font.PLAIN, 18));
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawString("禁止访问", Integer.parseInt(size[0]) / 2 - 60, Integer.parseInt(size[1]) / 2 - 10);
				}
				ServletOutputStream outStream = response.getOutputStream();
				ImageIO.write(bi, "png", outStream);
				
				outStream.flush();
				// 关闭输出流
				outStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			responseNoSecurity(request, response, 401, "没有权限访问该内容");
			// response.sendError(403, "Request denied! !");
		}
	}
	
	private HttpServletRequest copyRequest(HttpServletRequest request) {
		ParameterRequestWrapper newreq = new ParameterRequestWrapper(request, null, false);
		return newreq;
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
