package org.cr.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Description
 * @author caorong
 * @date 2013-2-21
 * 
 */
public class Links implements Serializable {

	private static final long serialVersionUID = 1411040167519856685L;

	private String source;
	private String target;
	private String value;

	public Links() {
		// TODO Auto-generated constructor stub
	}

	public Links(String source, String target, String value) {
		this.source = source;
		this.target = target;
		this.value = value;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
