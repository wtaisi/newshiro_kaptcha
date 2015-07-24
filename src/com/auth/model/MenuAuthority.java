package com.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 * 菜单-权限 关联表
 * 
 * 交集
 * */
@Entity
@Table(name="SYS_MENU_AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MenuAuthority {
	private Integer id;
	private Integer aid;//权限
	private Integer mid;//菜单
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	
}
