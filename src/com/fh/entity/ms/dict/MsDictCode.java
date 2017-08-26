package com.fh.entity.ms.dict;
/**
 * 字典代码
 * @author ZhaiZhengqiang
 * @date 2016-11-04
 */
public class MsDictCode {

	private int id;//主键id
	
	private int parentcode;//上级节点代码
	
	private String name;//节点描述
	
	private String value;//节点代码值
	
	private String type;//扩展字段
	
	private String memo;//预留

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentcode() {
		return parentcode;
	}

	public void setParentcode(int parentcode) {
		this.parentcode = parentcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
