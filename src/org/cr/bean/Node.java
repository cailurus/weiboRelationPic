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
public class Node implements Serializable {
	private static final long serialVersionUID = 8779982152275429239L;

	private String name;
	private String group;
	private int count;

	public Node() {
		// TODO Auto-generated constructor stub
	}
	public Node(String name,String group) {
		this.name = name;
		this.group = group;
		this.count = 0;
	}
	public Node(String name,String group,int count) {
		this.name = name;
		this.group = group;
		this.count = count;
	}
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
