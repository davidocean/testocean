package com.fh.bean.folder;
/**
 * 发布目录
 * @author ZhaiZhengqiang
 * @date 2016-11-03
 */
import java.util.List;

public class Folder {

	private String folderName;
	
	private String description;
	
	private String webEncrypted;
	
	private String isDefault;
	
	private List<String> folders;
	
	private List<FoldersDetail> foldersDetail;
	
	private List<Service> services;

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebEncrypted() {
		return webEncrypted;
	}

	public void setWebEncrypted(String webEncrypted) {
		this.webEncrypted = webEncrypted;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}


	public List<String> getFolders() {
		return folders;
	}

	public void setFolders(List<String> folders) {
		this.folders = folders;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<FoldersDetail> getFoldersDetail() {
		return foldersDetail;
	}

	public void setFoldersDetail(List<FoldersDetail> foldersDetail) {
		this.foldersDetail = foldersDetail;
	}
	
	
}
