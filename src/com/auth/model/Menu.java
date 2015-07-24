package com.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="SYS_MENU")
public class Menu {
	
	
	private Integer id;
	private String text;
	private String iconCls;
	private String leaf;
	private Integer pid;
	private String expanded;
	private String visiable;
	private Integer delflag;
	private String leftid;
	private String rightid;
	private int level;
	private String url;
	private Integer orders;
	
//	private List<Authority> authList = Lists.newArrayList();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length=11)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getExpanded() {
		return expanded;
	}
	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	public String getVisiable() {
		return visiable;
	}
	public void setVisiable(String visiable) {
		this.visiable = visiable;
	}
	
	@Column(length=1)
	@JsonIgnore
	public Integer getDelflag() {
		return delflag;
	}
	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	public String getLeftid() {
		return leftid;
	}
	public void setLeftid(String leftid) {
		this.leftid = leftid;
	}
	public String getRightid() {
		return rightid;
	}
	public void setRightid(String rightid) {
		this.rightid = rightid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(length=11)
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "Menu [url=" + url + "]";
	}
	
	
	// 多对多定义
//	@ManyToMany(
//			targetEntity=com.auth.model.Authority.class,
//    	fetch=FetchType.EAGER,
//    	cascade={CascadeType.PERSIST, CascadeType.MERGE}
//	)
//	@JoinTable(name = "SYS_MENU_AUTHORITY", 
//		joinColumns = {@JoinColumn(name = "mid") }, 
//		inverseJoinColumns = { @JoinColumn(name = "aid") })
//	@Fetch(FetchMode.SUBSELECT)
//	//@OrderBy("id ASC")
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	@JsonIgnore
//	public List<Authority> getAuthList() {
//		return authList;
//	}
//	public void setAuthList(List<Authority> authList) {
//		this.authList = authList;
//	}
	
}
