package com.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 过滤中文
 * 
 * */
public class CharacterEncodingFilter implements Filter {
	
	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	
	@Override
	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain fc) throws IOException, ServletException {
		HttpServletRequest httpreq = (HttpServletRequest) req;
		if (httpreq.getMethod().equals("POST")) {
			req.setCharacterEncoding(encoding);
		} else {
			req = new Request(httpreq);
		}

		fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
		this.filterConfig = fc;
		this.encoding = fc.getInitParameter("encoding");
	}
	
	
	class Request extends HttpServletRequestWrapper {
		public Request(HttpServletRequest request) {
			super(request);
		}
		public String toChi(String input) {
			try {
				byte[] bytes = input.getBytes("ISO-8859-1");
				return new String(bytes, encoding);
			} catch (Exception ex) {
			}
			return null;
		}
		private HttpServletRequest getHttpServletRequest() {
			return (HttpServletRequest) super.getRequest();
		}
		@Override
		public String getParameter(String name) {
			return toChi(getHttpServletRequest().getParameter(name));
		}
		@Override
		public String[] getParameterValues(String name) {
			String values[] = getHttpServletRequest().getParameterValues(name);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					values[i] = toChi(values[i]);
				}
			}
			return values;
		}
	}

}
