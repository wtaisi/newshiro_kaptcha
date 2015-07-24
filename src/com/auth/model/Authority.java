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

/**
 * 权限
 * 注解：要么变量，要么方法
 * */
@Entity
@Table(name="SYS_AUTHORITY")
public class Authority {
	
	private Integer id;//
	private String authName;
	//规则
	private String authCode;
	private String descr;
	private List<Role> roleList = Lists.newArrayList();
	//关联Menu
	private List<Menu> menuList = Lists.newArrayList();
	
	private Integer delflag;
	private Date createDate;
	private String createBy;
	private Date updateDate;
	private String updateBy;
	
	private String menus;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	// 多对多定义
	//persist 级联保存，级联更新（merge)
	@ManyToMany(
			targetEntity=com.auth.model.Role.class,
    	fetch=FetchType.EAGER,//立刻加载，在查询主对象的时候同时加载关联对象。
    	/**
    	 *  CascadeType.PERSIST只有A类新增时，会级联B对象新增。若B对象在数据库存（跟新）在则抛异常（让B变为持久态） 
			CascadeType.MERGE指A类新增或者变化，会级联B对象（新增或者变化） 
			CascadeType.REMOVE只有A类删除时，会级联删除B类； 
			CascadeType.ALL包含所有； 
			CascadeType.REFRESH没用过。 
			综上：大多数情况用CascadeType.MERGE就能达到级联跟新又不报错，用CascadeType.ALL时要斟酌下CascadeType.REMOVE 
    	 */
    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	//joinColumns写的都是本表在中间表的外键名称
	@JoinTable(name = "SYS_ROLE_AUTHORITY", //关联表名
		joinColumns = {@JoinColumn(name = "aid") }, //name="主表外键"
	//inverseJoinColumns写的是另一个表在中间表的外键名称。
		inverseJoinColumns = { @JoinColumn(name = "rid") })//name="从表外键"
	@Fetch(FetchMode.SUBSELECT)//默认懒加载(除非设定关联属性lazy=false),在访问第一个关联对象时加载所有的关联对象。会累计产生两条sql语句。且FetchType设定有效。
	//@OrderBy("id ASC")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//基于时间戳判定机制，，对于数据同步要求严格的情况，使用频繁 (二级缓存方案)
	@JsonIgnore
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	
	// 多对多定义
	@ManyToMany(
			targetEntity=com.auth.model.Menu.class,
    	fetch=FetchType.EAGER,
    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(name = "SYS_MENU_AUTHORITY", 
		joinColumns = {@JoinColumn(name = "aid") }, 
		inverseJoinColumns = { @JoinColumn(name = "mid") })
	@Fetch(FetchMode.SUBSELECT)
	//@OrderBy("id ASC")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	/**
	 * 获取所有menu的id
	 * */
	@JsonIgnore
	@Transient
	public List<Integer> getMenuIds(){
		List<Integer> ids = Lists.newArrayList();
		if(menuList!=null && menuList.size()>0){
			for(Menu menu: menuList){
				ids.add(menu.getId());
			}
		}
		return ids;
	}
	
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	@Column(length=1)
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
	public String getMenus() {
		return menus;
	}
	public void setMenus(String menus) {
		this.menus = menus;
	}
	@Override
	public String toString() {
		return "Authority [authName=" + authName + ", authCode=" + authCode
				+ "]";
	}
	
}
