package com.fh.entity.ms.res;

public class TOCItem {

	private String id;
	private String ParentID;
    private String text ;  
    private String type ;  
    private String ServiceFolder;
    private String ServiceNativeURL;
    private String ServiceCache;
    private String ServiceName;
    private String fullPermisson; // 是否自由服务 0：自由服务；1：安全服务
    private String hasPermission; // 当前用户是否拥有服务权限  0：没有权限；1：拥有权限；-1：没有登录信息
    private String isBasemap; // 是否是 基础底图 0：否；1：是。
    
    //private AdditionalParameters additionalParameters ;  
 
    public String getText()  
   {  
         return text ;  
   }  
 
    public void setText(String text )  
   {  
         this.text = text;  
   }  
 
    public String getType()  
   {  
         return type ;  
   }  
 
    public void setType(String type )  
   {  
         this.type = type;  
   }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentID() {
		return ParentID;
	}

	public void setParentID(String parentID) {
		ParentID = parentID;
	}

	public String getServiceNativeURL() {
		return ServiceNativeURL;
	}

	public void setServiceNativeURL(String serviceNativeURL) {
		ServiceNativeURL = serviceNativeURL;
	}

	public String getServiceFolder() {
		return ServiceFolder;
	}

	public void setServiceFolder(String serviceFolder) {
		ServiceFolder = serviceFolder;
	}

	public String getServiceCache() {
		return ServiceCache;
	}

	public void setServiceCache(String serviceCache) {
		ServiceCache = serviceCache;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	public String getFullPermisson() {
		return fullPermisson;
	}

	public void setFullPermisson(String fullPermisson) {
		this.fullPermisson = fullPermisson;
	}

	public String getHasPermission() {
		return hasPermission;
	}

	public void setHasPermission(String hasPermission) {
		this.hasPermission = hasPermission;
	}

	public String getIsBasemap() {
		return isBasemap;
	}

	public void setIsBasemap(String isBasemap) {
		this.isBasemap = isBasemap;
	}  
	
	
 
//    public AdditionalParameters getAdditionalParameters()  
//   {  
//         return additionalParameters ;  
//   }  
// 
//    public void setAdditionalParameters(AdditionalParameters additionalParameters )  
//   {  
//         this.additionalParameters = additionalParameters ;  
//   }  
}
