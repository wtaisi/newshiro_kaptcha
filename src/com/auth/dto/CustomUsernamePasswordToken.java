package com.auth.dto;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomUsernamePasswordToken extends UsernamePasswordToken {
	
	private static final long serialVersionUID = 18209042484318145L;
	private String captcha;
	private String error;
	public CustomUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha){
		super(username,password,rememberMe,host);
		this.captcha = captcha;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
