package com.base.filter;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.auth.dto.CustomUsernamePasswordToken;
import com.base.exception.IncorrectCaptchaException;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	private static final Logger log = Logger.getLogger(CustomFormAuthenticationFilter.class);
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	public String getCaptchaParam() { 
	      return captchaParam; 
	   } 
	   public void setCaptchaParam(String captchaParam) { 
	      this.captchaParam = captchaParam; 
	   } 
	   protected String getCaptcha(ServletRequest request) { 
	      return WebUtils.getCleanParam(request, getCaptchaParam()); 
	   } 
	   protected CustomUsernamePasswordToken  createToken(ServletRequest request,ServletResponse response) {
		    request.removeAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);//清空
			String username = getUsername(request);
			String password = getPassword(request);
			boolean rememberMe = isRememberMe(request);
			String captcha = getCaptcha(request);
			String host = getHost(request);
			return new CustomUsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
		}
	   // 验证码校验
	   protected void doCaptchaValidate( HttpServletRequest request ,CustomUsernamePasswordToken token ){ 
	      String captcha = (String)request.getSession().getAttribute("captcha"); 
	      if( captcha!=null && !captcha.equalsIgnoreCase(token.getCaptcha()) ){
	         throw new IncorrectCaptchaException("验证码错误！"); 
	      } 
	   } 
	   protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		   //log.info("executeLogin");
		   CustomUsernamePasswordToken token = createToken(request, response); 
		      try { 
		         doCaptchaValidate( (HttpServletRequest)request,token ); 
		         Subject subject = getSubject(request, response); 
		         subject.login(token); 
		         log.info("用户: "+token.getUsername()+" 登录");
		         return onLoginSuccess(token, subject, request, response); 
		      } catch (AuthenticationException e) { 
		         return onLoginFailure(token, e, request, response); 
		      } 
	   }
}
