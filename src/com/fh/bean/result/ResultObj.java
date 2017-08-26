package com.fh.bean.result;
/**
 * 返回结果
 * @author ZhaiZhengqiang
 * @date 2016-11-03
 */
public class ResultObj {
	
	public static final String SUCCESS = "success";
	
	public static final String ERROR = "error";
	
	private String status;
	
	private String[] messages;
	
	private String code;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
