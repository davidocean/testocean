/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: SecurityUtils.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月27日 下午6:54:57
 * @version: V1.0  
 */
package com.proxy.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

import com.fh.service.system.user.UserManager;
import com.fh.util.PageData;
import com.jes.utils.TokenUtils;

/**
 * @author Jes
 * 
 */
public class SecurityUtils {
	
	public static boolean validateUser(String username, String password) {
		boolean isValid = false;
		UserManager userService = SpringHelper.getBean(UserManager.class);
		PageData pd = new PageData();
		String passwd = new SimpleHash("SHA-1", username, password).toString(); // 密码加密
		pd.put("USERNAME", username);
		pd.put("PASSWORD", passwd);
		try {
			pd = userService.getUserByNameAndPwd(pd);
			// 根据用户名和密码去读取用户信息
			if (pd != null) {
				isValid = true;
			} else {
				isValid = false;
			}
		} catch (Exception e) {
			isValid = false;
			e.printStackTrace();
		}
		return isValid;
	}
	
	public static Map<String, String> validate(String token, String servicename) throws Exception {
		Map<String, String> re = new HashMap<>();
		re.put("isValidated", "0");// 0 没有通过验证
		if (null != token) {
			Map<String, String> tokenInfo = TokenUtils.parseToken(token);
			if (null != tokenInfo) {
				String username = tokenInfo.get("username");
				String password = tokenInfo.get("password");
				String expiration = tokenInfo.get("expiration");
				
				if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {// 用户名密码是否为空
					if (validateUser(username, password)) {// 用户名密码是否匹配
						
						if (ServiceUtil.serviceSecurityValidate(servicename, username)) {// 用户是否有该服务权限
							re.put("isValidated", "1");// 1 通过验证
							re.put("username", username);// 1 通过验证
						} else {
							re.put("msg", "No permissions for this service.");
						}
					} else {
						re.put("msg", "Illegal username or password.");
					}
				} else {
					re.put("msg", "Access forbidden.Username and password required.");
				}
			} else {
				re.put("msg", "Illegal token.");
			}
		} else {
			re.put("msg", "Token required.");
		}
		return re;
	}
	
	public static String getCookie(HttpServletRequest request, String name) {
		if (null == request || StringUtils.isEmpty(name)) {
			return null;
		}
		String cookie = null;
		Cookie[] cookies = request.getCookies();
		if(null == cookies)	return null;
		for (int i = 0; i < cookies.length; i++) {
			String _name = cookies[i].getName();
			if (name.equalsIgnoreCase(_name)) {
				cookie = cookies[i].getValue();
				break;
			}
		}
		return cookie;
	}
	
	public static String getFullURL(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();
		
		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append('?').append(queryString).toString();
		}
	}
}
