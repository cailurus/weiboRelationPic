package org.cr.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Description
 * @author caorong
 * @date 2013-2-21
 * 
 */
public class Graphdata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6511802113390931782L;

	public List<Node> nodes;
	public List<Links> links;

	public Graphdata() {
		// TODO Auto-generated constructor stub
	}
	
	public Graphdata(List<Node> nodes, List<Links> links) {
		this.nodes = nodes;
		this.links = links;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Links> getLinks() {
		return links;
	}

	public void setLinks(List<Links> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
