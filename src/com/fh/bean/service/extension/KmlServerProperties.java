package com.fh.bean.service.extension;

public class KmlServerProperties {

	private String minRefreshPeriod;
	private String compatibilityMode;
	private String imageSize;
	private String dpi;
	private String endPointURL;
	private String featureLimit;
	private String useDefaultSnippets;
	
	public String getMinRefreshPeriod() {
		return minRefreshPeriod;
	}
	
	public void setMinRefreshPeriod(String minRefreshPeriod) {
		this.minRefreshPeriod = minRefreshPeriod;
	}
	
	public String getCompatibilityMode() {
		return compatibilityMode;
	}
	
	public void setCompatibilityMode(String compatibilityMode) {
		this.compatibilityMode = compatibilityMode;
	}
	
	public String getImageSize() {
		return imageSize;
	}
	
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	
	public String getDpi() {
		return dpi;
	}
	
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}
	
	public String getEndPointURL() {
		return endPointURL;
	}
	
	public void setEndPointURL(String endPointURL) {
		this.endPointURL = endPointURL;
	}
	
	public String getFeatureLimit() {
		return featureLimit;
	}
	
	public void setFeatureLimit(String featureLimit) {
		this.featureLimit = featureLimit;
	}
	
	public String getUseDefaultSnippets() {
		return useDefaultSnippets;
	}
	
	public void setUseDefaultSnippets(String useDefaultSnippets) {
		this.useDefaultSnippets = useDefaultSnippets;
	}
	
	
}
