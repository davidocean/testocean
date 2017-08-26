package com.proxy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.fh.util.StringUtil;
import com.jes.utils.TokenUtils;
import com.proxy.util.SecurityUtils;

public class ServiceToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ServiceToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		
		String format = request.getParameter("f");
		if (!StringUtils.isEmpty(format) && "json".equalsIgnoreCase(format)) {
			
			JSONObject jo = new JSONObject();
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			try {
				
				if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {// json
					
					if (SecurityUtils.validateUser(username, password)) {
						String _expiration = request.getParameter("expiration");
						long expiration = 1000 * 60 * 60;
						if (!StringUtils.isEmpty(_expiration)) {
							expiration = Long.parseLong(_expiration);
						}
						
						String token = TokenUtils.generateToken(username, password, expiration);
						
						if (!StringUtils.isEmpty(token)) {
							jo.put("success", 1);
							jo.put("maptoken", token);
						} else {
							jo.put("success", 0);
							jo.put("msg", "获取maptoken失败！");
						}
					}else {
						jo.put("success", 0);
						jo.put("msg", "用户名或密码错误！");
					}
				} else {
					jo.put("success", 0);
					jo.put("msg", "用户名和密码是必须！");
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pw.write(jo.toString());
			pw.flush();
		} else {
			response.sendRedirect("generateToken.html");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
