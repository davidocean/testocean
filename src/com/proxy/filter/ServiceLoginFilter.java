/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: ServiceLogin.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年12月25日 下午4:24:25
 * @version: V1.0  
 */
package com.proxy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jes
 *
 */
public class ServiceLoginFilter implements Filter {
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) paramServletRequest;
		HttpServletResponse response = (HttpServletResponse) paramServletResponse;
		String redirect = request.getParameter("redirect");
		if (StringUtils.isEmpty(redirect)) {
			response.sendRedirect(request.getContextPath());
			return ;
		}
		
		chain.doFilter(paramServletRequest, paramServletResponse);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
