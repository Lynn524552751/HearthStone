package com.mgr.bean;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * HsArenaVo entity. @author MyEclipse Persistence Tools
 */
@DataTransferObject
public class HsArenaVo {
	private static final long serialVersionUID = 2730486647350808501L;
	// Fields

	private Integer count;
	private String name;
	private Integer winsSum;
	private Integer lossesSum;
	private Integer goldSum;
	private Integer dustSum;

	// Constructors
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWinsSum() {
		return winsSum;
	}

	public void setWinsSum(Integer winsSum) {
		this.winsSum = winsSum;
	}

	public Integer getLossesSum() {
		return lossesSum;
	}

	public void setLossesSum(Integer lossesSum) {
		this.lossesSum = lossesSum;
	}

	public Integer getGoldSum() {
		return goldSum;
	}

	public void setGoldSum(Integer goldSum) {
		this.goldSum = goldSum;
	}

	public Integer getDustSum() {
		return dustSum;
	}

	public void setDustSum(Integer dustSum) {
		this.dustSum = dustSum;
	}

}
