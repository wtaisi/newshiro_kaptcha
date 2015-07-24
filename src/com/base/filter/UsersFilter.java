package com.base.filter;   
  
import java.io.IOException;   
  

import javax.servlet.Filter;   
import javax.servlet.FilterChain;   
import javax.servlet.FilterConfig;   
import javax.servlet.ServletException;   
import javax.servlet.ServletRequest;   
import javax.servlet.ServletResponse;   
import javax.servlet.http.HttpServletRequest;   
import javax.servlet.http.HttpServletResponse;   
 

import org.apache.shiro.SecurityUtils;

import com.auth.model.User;
/**  
 * 用户访问地址栏url权限的过滤器  
 */  
public class UsersFilter implements Filter {   
  
    public void destroy() {   
        // TODO Auto-generated method stub   
           
    }   
  
    public void doFilter(ServletRequest request, ServletResponse response,   
            FilterChain chain) throws IOException, ServletException {   
        request.setCharacterEncoding("UTF-8");   
        response.setCharacterEncoding("UTF-8");   
        HttpServletRequest req = (HttpServletRequest) request;   
        HttpServletResponse res = (HttpServletResponse) response;   
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        if (null != user) {
        	String conString = "";
        	//获取父url--如果不是直接输入的话就是先前的访问过来的页面，要是用户输入了，这个父url是不存在的
    		conString = req.getHeader("REFERER");
    		//判断如果上一个目录为空的话，说明是用户直接输入url访问的
    		if("".equals(conString) || null==conString){ 
    			//当前请求url，去掉几个可以直接访问的页面
    			String servletPath = req.getServletPath();
    			//跳过/login和/
    			if(servletPath.equals("/")||servletPath.equals("/login")||servletPath.equals("/login/")){ 
    				chain.doFilter(request, response);
    			} else {
    				//res.sendRedirect(req.getContextPath()+"/login");//跳回首页
    				req.getRequestDispatcher("/login").forward(req, res);
    			}
    		} else {
    			chain.doFilter(request, response);
    		}
        } else {
        	chain.doFilter(request, response);
        }
    }   
  
    public void init(FilterConfig filterConfig) throws ServletException {   
        // TODO Auto-generated method stub   
           
    }   
  
}  