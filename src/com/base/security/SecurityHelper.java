package com.base.security;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.auth.model.Authority;
import com.auth.model.Role;
import com.auth.model.User;
import com.google.common.collect.Lists;

/**
 * 获取当前用户的权限信息
 * 
 * */
public class SecurityHelper {
	
	/**
	 * 获取当前用户
	 * */
	public static User getCurrentUser(){
		return (User) SecurityUtils.getSubject().getSession().getAttribute("user");
	}
	/**
	 * 获取当前用户id
	 * */
	public static Integer getCurrentUserId(){
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		if(u!=null){
			return u.getId();
		}
		return 0;
	}
	
	public static boolean isTimeOut(){
		Session s = SecurityUtils.getSubject().getSession(false);
		if(s == null){
			return true;//过期
		}
		return false;
	}
	
	/**
	 * 获取当前用户具有的 menu id 集合
	 * */
	public static List<Integer> getMenuIds(){
		List<Integer> ids = Lists.newArrayList();
		User u = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		if(u!=null){
			List<Role> roles = u.getRoleList();
			if(roles!=null && roles.size()>0){
				for(Role role : roles){
					List<Authority> auths = role.getAuthList();
					if(auths!=null && auths.size()>0){
						for(Authority auth: auths){
							List<Integer> menus = auth.getMenuIds();
							ids.addAll(menus);
						}
					}
				}
			}
		}
		return ids;
	}
	
	/**
	 * 是否有角色
	 * */
	public static boolean hasRole(String role){
		Subject sub = SecurityUtils.getSubject();
		return sub.hasRole(role);
	}
	/**
	 * 是否有所有角色
	 * */
	public static boolean hasRoles(Collection<String> roles){
		Subject sub = SecurityUtils.getSubject();
		return sub.hasAllRoles(roles);
	}
	/**
	 * 是否有任一角色
	 * */
	public static boolean hasAnyRole(List<String> roles){
		Subject sub = SecurityUtils.getSubject();
		boolean[] bs = sub.hasRoles(roles);
		if(bs!=null && bs.length>0){
			for(boolean b: bs){
				if(b){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 是否有权限
	 * */
	public static boolean hasPermission(String authCode){
		return SecurityUtils.getSubject().isPermitted(authCode);
	}
	/**
	 * 是否有所有权限
	 * */
	public static boolean hasPermissions(String[] permissions){
		return SecurityUtils.getSubject().isPermittedAll(permissions);
	}
	/**
	 * 是否有任一权限
	 * */
	public static boolean hasAnyPermission(String[] permissions){
		boolean[] bs = SecurityUtils.getSubject().isPermitted(permissions);
		if(bs!=null && bs.length>0){
			for(boolean b: bs){
				if(b){
					return true;
				}
			}
		}
		return false;
	}
}
