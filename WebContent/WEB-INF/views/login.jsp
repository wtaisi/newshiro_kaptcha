<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
	<%  
        String path = request.getContextPath();  
        String basePath = request.getScheme() + "://"  
                + request.getServerName() + ":" + request.getServerPort()  
                + path + "/";  
    %> 
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>shiro 登入界面</title>
</head>
<body>
<script type="text/javascript">
<%
		String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(error != null){
			if(error.contains("DisabledAccountException")){
		%>
					alert('提示用户被屏蔽!');
		<%
				}else if(error.contains("IncorrectCaptchaException")){
		%>
					alert('提示验证码错误!');
		<%
				}else if(error.contains("IncorrectPasswordException")){
		%>
					alert('提示密码错误!');
		<%
				}else if(error.contains("UserNotExistException")){
		%>
					alert('提示用户不存在!');
		<%
				}
			}
		%>
		</script>
	异常：<%=error==null?" ":error%>
	<h3>shiro 登入界面</h3>
	<%=basePath%>
	<form action="login" method="post">
		<table>
			<tr>
				<td>账号</td>
				<td><input type="text" name="username" required="required"/></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" name="password" required="required"/></td>
			</tr>
			<tr>
				<td>验证码</td>
				<td><input type="text" name="captcha" required="required"/></td>
				<td colspan="2"><img  src='login/rand?rid=<%=Math.random()*10000 %>' /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="提交" /></td>
			</tr>
		</table>
	</form>
	
		 <a href="login/logout">登出</a>
	
</body>
</html>