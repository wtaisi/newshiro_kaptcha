package com.auth.service;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.auth.dto.CustomUsernamePasswordToken;
import com.auth.model.Role;
import com.auth.model.User;
import com.base.exception.IncorrectPasswordException;
import com.base.exception.UserNotExistException;
import com.google.common.base.Objects;
/**
 * shiro-权限验证
 * 
 * */
public class ShiroRealm extends AuthorizingRealm{
	
	HttpServletRequest request;
	
	@Autowired
	private UserServiceImpl userService;
	
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	/**
	 * 授权-未授权的情况下调用
	 * */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = userService.findByUserName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Role role : user.getRoleList()) {
			// 基于Role的权限信息
			info.addRole(role.getRoleName());
			// 基于Permission的权限信息
			info.addStringPermissions(role.getPermissions());
		}
		//log.info(info.getRoles().toString());
		return info;
	}
	
	/**
	 * 认证
	 * 
	 * */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//log.info("shiro authentication");
		CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authcToken;
		User user = userService.findByUserName(token.getUsername());//User [loginName=a, password=a, name=管理员1, status=1]
		Session session = SecurityUtils.getSubject().getSession();
		if (user != null) {
			if ("0".equals(user.getStatus())) {
				throw new DisabledAccountException();
			}
			String psw = String.valueOf(token.getPassword());
			if(!psw.equals(user.getPassword())){
				throw new IncorrectPasswordException("密码错误！"); 
			}
			session.setAttribute("user", user);
			return new SimpleAuthenticationInfo(new ShiroUser(user.getLoginName(), user.getName()), user.getPassword(), getName());
		} else {
			throw new UserNotExistException("用户不存在！"); 
		}
	}
	
	
	
	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 * 
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public String loginName;
		public String name;

		public ShiroUser(String loginName, String name) {
			this.loginName = loginName;
			this.name = name;
		}

		public String getName() {
			return name;
		}
		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}
		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}
		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}
}
