package com.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="SYS_ROLE_AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleAuthority implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer aid;
	private Integer rid;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(length=32)
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	@Column(length=32)
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	@Override
	public String toString() {
		return "RoleAuthority [id=" + id + ", aid=" + aid + ", rid=" + rid
				+ "]";
	}
	
}
