package com.mgr.bean;

import java.io.Serializable;
import java.util.Map;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * service返回值包装对象
 * 
 * @param <T>
 * 
 */
@DataTransferObject
public class ServiceResult<T> implements Serializable {

	private static final long serialVersionUID = -6652076509848001811L;

	private boolean isOk = false;

	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 数据
	 */
	private T data;

	/**
	 * 数据Map
	 */
	private Map<String, Object> dataMap;

	public ServiceResult() {
	}

	public ServiceResult(boolean ok) {
		this.isOk = ok;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
