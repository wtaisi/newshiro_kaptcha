package com.auth.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**
 * 角色
 * 
 * */
@Entity
@Table(name="SYS_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role {
	
	private Integer id;//
	private String roleName;
	private List<Authority> authList = Lists.newArrayList();
	private List<User> userList = Lists.newArrayList();
	
	private String descr;
	private Integer delflag;
	
	private Date createDate;
	private String createBy;
	private Date updateDate;
	private String updateBy;
	
	//存放角色对应的人员和权限
	private String member;
	private String permission;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToMany(
			targetEntity=com.auth.model.Authority.class,
	    	fetch=FetchType.EAGER,
	    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
		)
	@JoinTable(name = "SYS_ROLE_AUTHORITY", 
		joinColumns = {@JoinColumn(name = "rid") }, 
		inverseJoinColumns = { @JoinColumn(name = "aid") })
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@OrderBy("id ASC")
	@JsonIgnore
	public List<Authority> getAuthList() {
		return authList;
	}
	public void setAuthList(List<Authority> authList) {
		this.authList = authList;
	}
	
	// 多对多定义
	@ManyToMany(
			targetEntity=com.auth.model.User.class,
    	fetch=FetchType.EAGER,
    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "SYS_ROLE_USER", 
		joinColumns = {@JoinColumn(name = "rid") }, 
		inverseJoinColumns = { @JoinColumn(name = "uid") })
	@Fetch(FetchMode.SUBSELECT)
	//@OrderBy("id ASC")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	@Transient
	public List<String> getPermissions(){
		List<String> permissions = Lists.newArrayList();
		if(authList!=null && authList.size()>0){
			for(Authority auth: authList){
				permissions.add(auth.getAuthCode());
			}
		}
		return permissions;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	@JsonIgnore
	public Integer getDelflag() {
		return delflag;
	}
	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@JsonIgnore
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@JsonIgnore
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@JsonIgnore
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	@Transient
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	@Transient
	public String getPermission() {
		
		return this.permission;
	}
	@Override
	public String toString() {
		return "Role [roleName=" + roleName + "]";
	}
	
	
}
