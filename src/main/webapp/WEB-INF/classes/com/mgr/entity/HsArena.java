package com.mgr.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.GenericGenerator;

/**
 * HsArena entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "hs_arena")
@DataTransferObject
public class HsArena implements java.io.Serializable {

	// Fields

	private Integer id;
	private String occupation;
	private Integer wins;
	private Integer losses;
	private Integer gold;
	private Integer dust;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public HsArena() {
	}

	/** minimal constructor */
	public HsArena(Integer wins, Integer losses, Integer gold, Integer dust,
			Timestamp time) {
		this.wins = wins;
		this.losses = losses;
		this.gold = gold;
		this.dust = dust;
		this.time = time;
	}

	/** full constructor */
	public HsArena(String occupation, Integer wins, Integer losses,
			Integer gold, Integer dust, Timestamp time) {
		this.occupation = occupation;
		this.wins = wins;
		this.losses = losses;
		this.gold = gold;
		this.dust = dust;
		this.time = time;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "occupation", nullable = false, length = 64)
	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Column(name = "wins", nullable = false)
	public Integer getWins() {
		return this.wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	@Column(name = "losses", nullable = false)
	public Integer getLosses() {
		return this.losses;
	}

	@Column(name = "losses", nullable = false)
	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	@Column(name = "gold", nullable = false)
	public Integer getGold() {
		return this.gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	@Column(name = "dust", nullable = false)
	public Integer getDust() {
		return this.dust;
	}

	public void setDust(Integer dust) {
		this.dust = dust;
	}

	@Column(name = "time", nullable = false, length = 19)
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}