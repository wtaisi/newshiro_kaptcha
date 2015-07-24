<%@page import="com.auth.model.Menu"%>
<%@page import="com.auth.model.Authority"%>
<%@page import="com.auth.model.Role"%>
<%@page import="com.auth.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%  
        String path = request.getContextPath();  
        String basePath = request.getScheme() + "://"  
                + request.getServerName() + ":" + request.getServerPort()  
                + path ;  
        User u=(User)session.getAttribute("user");
%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>shiro 成功界面</title>
</head>
<body>
	<a href="login/logout">登出</a>
	 <h3>用户：${user.loginName} 登录成功</h3>
	 <h3>角色为:</h3>
	 <%
	 for(int i=0;i<u.getRoleList().size();i++){
		 Role role=u.getRoleList().get(i);
	 	out.write(role.getRoleName()+"<br/>");
	 }
	 %>
	 <h3>权限为：</h3>
	 <%
	 for(int i=0;i<u.getRoleList().size();i++){
		 for(int k=0;k<u.getRoleList().get(i).getAuthList().size();k++){
			 Authority authority=u.getRoleList().get(i).getAuthList().get(k);
	 	 	 out.write(authority.getAuthName()+","+authority.getAuthCode()+"<br/>");
		 }
	 }
	 %>
	 <h3>url:</h3>
	<%
	 for(int i=0;i<u.getRoleList().size();i++){
		 for(int k=0;k<u.getRoleList().get(i).getAuthList().size();k++){
			 for(int j=0;j<u.getRoleList().get(i).getAuthList().get(k).getMenuList().size();j++){
				 Menu menu=u.getRoleList().get(i).getAuthList().get(k).getMenuList().get(j);
	 	 	 out.write("<a href='"+basePath+menu.getUrl()+"'>"+menu.getText()+"</a><br/>");
			 }
		 }
	 }
	 %>
	
</body>
</html>