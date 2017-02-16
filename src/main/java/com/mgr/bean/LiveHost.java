package com.mgr.bean;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class LiveHost {
	private static final long serialVersionUID = 2730486647350808501L;
	// Fields
	private String name;
	private Integer number;
	private String url;
	private String title;
	private String img;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
