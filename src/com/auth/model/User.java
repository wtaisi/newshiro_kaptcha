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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

@Entity
@Table(name="SYS_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {
	
	private Integer id;
	private String loginName;
	private String password;
	private String name;
	private String address;
	private String telphone;
	private String postcode;
	private String email;
	private String status;
	
	private List<Role> roleList = Lists.newArrayList();
	private Integer delflag;
	private Date createDate;
	private String createBy;
	private Date updateDate;
	private String updateBy;
	
	private String newPwd;//新密码
	private String confirmPwd;//确认密码
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/*两者都是设定加载策略，前者是JPA标准设定加载策略的注解属性，后者是hibernate自有加载策略注解。可选值如下：
	@Fetch(FetchMode.JOIN)：始终立刻加载，使用外连（outer join）立刻加载关联对象，且忽略FetchType.LAZY。
	@Fetch(FetchMode.SELECT)   默认懒加载（除非设定lazy=false)，在访问每一个关联对象时用Select查询加载。有n+1次查询。
	@Fetch(FetchMode.SUBSELECT)  默认懒加载（除非设定lazy=false)，在第一次访问关联对象时才一次性加载所有关联对象。共产生两条sql语句，且FetchType设定有效。
	*/
	// 多对多定义
	@ManyToMany(
			targetEntity=com.auth.model.Role.class,
			//设定加载策略，是JPA标准设定加载策略的注解属性
    	fetch=FetchType.EAGER,//急加载，加载一个实体时，定义急加载的属性会立即从数据库中加载。（FetchType.LAZY：懒加载）从类在主类加载的时候同时加载
    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "SYS_ROLE_USER", 
		joinColumns = {@JoinColumn(name = "uid") }, 
		inverseJoinColumns = { @JoinColumn(name = "rid") })
	//是hibernate加载策略
	@Fetch(FetchMode.SUBSELECT)
	//@OrderBy("id ASC")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//缓存方式：只读模式，在此模式下，如果对数据进行更新操作，会有异常；
	@JsonIgnore
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonIgnore//忽略json
	@Column(nullable=false)
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
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Transient//数据库中将不会出现newPwd对应的字段,使用的时候,该字段可以直接存储到实体类中
	//@JsonIgnore
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	@Transient
	//@JsonIgnore
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	@Override
	public String toString() {
		return "User [loginName=" + loginName + ", password=" + password
				+ ", name=" + name + ", status=" + status + "]";
	}
	
}
