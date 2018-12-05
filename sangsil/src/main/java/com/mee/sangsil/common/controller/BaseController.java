package com.mee.sangsil.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BaseController{
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	

	@ModelAttribute("menuUrl")
	public String getMenuId(HttpServletRequest request){
		String menuUrl="";
		//프로젝트경로부터 파일까지의 경로값을 얻어옴 (/test/index.jsp)
		request.getRequestURI();		
		//프로젝트의 경로값만 가져옴(/test)
//		request.getContextPath();
		//전체 경로를 가져옴 (http://localhost:8080/test/index.jsp)
//		request.getRequestURL();
		//파일명 (/index.jsp)
//		request.getServletPath();
		
//		logger.info(request.getRequestURI() + "\n" + request.getContextPath() + "\n" + request.getRequestURL() + "\n" + request.getServletPath());
		
		String menuArray[];
//		for(int i=0; i < menuArray.length; i++) {
//			logger.info(i + ":" + menuArray[i]);
//		}
		
		try {
			menuArray = request.getRequestURI().split("/");
			menuUrl = menuArray[1];
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return menuUrl;
	}
	
}
