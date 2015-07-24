package com.auth.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.service.UserServiceImpl;
import com.base.security.SecurityHelper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@RequestMapping()
	public String index() {
		if(SecurityUtils.getSubject().getSession()!=null){
			if(SecurityUtils.getSubject().getSession().getAttribute("user")!=null){
				return "redirect:/";
			}
		}
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}
	
	@RequestMapping("/rand")
	public void generateAuthcode(@RequestParam("rid") String rid, HttpServletRequest req, HttpServletResponse res){
		try {
		      int width = 55, height = 20;
		      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		      // 获取图形上下文
		      Graphics g = image.getGraphics();
		      // 设定背景色
		      g.setColor(Color.white);
		      g.fillRect(0, 0, width, height);
		      //画边框
		      g.setColor(Color.black);
		      g.drawRect(0, 0, width - 1, height - 1);
		      // 取随机产生的认证码(4位数字)
		     String  rnd = rid.substring(0, rid.indexOf("."));
		      switch (rnd.length()) {
		        case 1:
		        	rnd = "000" + rnd;
		          break;
		        case 2:
		        	rnd = "00" + rnd;
		          break;
		        case 3:
		        	rnd = "0" + rnd;
		          break;
		        default:
		        	rnd = rnd.substring(0, 4);
		          break;
		      }
		      // 将认证码存入SESSION
		      req.getSession().setAttribute("captcha", rnd);
		      // 将认证码显示到图象中
		      g.setColor(Color.black);
		      g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		      g.drawString(rnd, 10, 15);
		      // 随机产生88个干扰点，使图象中的认证码不易被其它程序探测到
		      Random random = new Random();
		      for (int i = 0; i < 88; i++) {
		        int x = random.nextInt(width);
		        int y = random.nextInt(height);
		        g.drawLine(x, y, x, y);
		      }
		      // 图象生效
		      g.dispose();
		      res.setHeader("Cache-Control", "no-store");
		      res.setDateHeader("Expires", 0);
		      res.setContentType("image/jpeg");
		      ServletOutputStream sos = res.getOutputStream();
		      BufferedOutputStream gif = new BufferedOutputStream(sos);
		      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(gif);
		      encoder.encode(image);
		      sos.flush();
		      gif.close();
		      sos.close();
		    }
		    catch (Exception e) {

		    }
	}
}