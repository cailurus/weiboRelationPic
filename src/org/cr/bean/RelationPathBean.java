/**
 * 
 */
package org.cr.bean;

import java.io.Serializable;

/**
 * @author caorong
 * 
 */
public class RelationPathBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4310597827973561373L;
	private String id;
	// 用于给数据库表示
	private String Centeruid;
	private String sourceuid;
	private String targetuid;
	// 深度，根据深度决定颜色
	private String deep;

	public RelationPathBean() {
		// TODO Auto-generated constructor stub
	}

	public RelationPathBean(String centeruid, String sourceuid,
			String targetuid, String deep) {
		this.Centeruid = centeruid;
		this.sourceuid = sourceuid;
		this.targetuid = targetuid;
		this.deep = deep;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCenteruid() {
		return Centeruid;
	}

	public void setCenteruid(String centeruid) {
		Centeruid = centeruid;
	}

	public String getSourceuid() {
		return sourceuid;
	}

	public void setSourceuid(String sourceuid) {
		this.sourceuid = sourceuid;
	}

	public String getTargetuid() {
		return targetuid;
	}

	public void setTargetuid(String targetuid) {
		this.targetuid = targetuid;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	@Override
	public String toString() {
		return "RelationPathBean {\n\t[id=" + id + ", Centeruid=" + Centeruid
				+ ", sourceuid=" + sourceuid + ", targetuid=" + targetuid
				+ ", deep=" + deep + "]\n}";
	}


}
