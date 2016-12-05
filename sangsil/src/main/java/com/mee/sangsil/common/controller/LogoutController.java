package com.mee.sangsil.common.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mee.sangsil.common.CommonUtil;
import com.mee.sangsil.common.HttpsClientWithoutValidation;
import com.mee.sangsil.common.service.LoginService;
import com.mee.sangsil.dto.UserDto;

@Controller
@RequestMapping("/common/logout")
public class LogoutController {

	private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);
	
	@Autowired
	private LoginService loginService;
	
	//로그인기관구분 
	@Value("#{config['LOGIN_GBN_01']}") String login_gbn_01;
	@Value("#{config['LOGIN_GBN_02']}") String login_gbn_02;
	//네로아 설정
	@Value("#{config['NAVER_LOGIN_URL']}") String naver_login_url;
	@Value("#{config['NAVER_LOGIN_CLIENT_ID']}") String naver_login_client_id;
	@Value("#{config['NAVER_LOGIN_CLIENT_SECRET']}") String naver_login_client_secret;
	
	/**
	 * 로그인
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		JSONObject json = new JSONObject();
		
		// ===================================================================================================
		// 네로아-접근 토큰 삭제 요청
		//https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=jyvqXeaVOVmV&client_
		//secret=527300A0_COq1_XV33cf&access_token=c8ceMEJisO4Se7uGCEYKK1p52L93bHXLnaoETis9Yzjfn
		//orlQwEisqemfpKHUq2gY&service_provider=NAVER
		//https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=jyvqXeaVOVmV&access_token=c8ceMEJisO4Se7uGCEYKK1p52L93bHXLnaoETis9YzjfnorlQwEisqemfpKHUq2gY	&client_secret=527300A0_COq1_XV33cf&service_provider=NAVER
		//https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=aDbtAQP5ImTbn5MWqFfT&secret=qxBqZHqD8B&access_token=AAAAOK55G+reNt8oay7pOGkufLonQLGIr15lqC4rKe6cT7e7Ui7T8JtCdxJotyTwzQI9tNJjhQ1G4oma6eiKTPF3gHA=&service_provider=NAVER
		try {
			//네이버의경우 토큰삭제요청 
			if(session.getAttribute("LOGIN_GBN").toString().equals(login_gbn_01)){
				StringBuffer naver_req_url = new StringBuffer();
				naver_req_url.append(naver_login_url);
				naver_req_url.append("/token");
				naver_req_url.append("?grant_type=delete");
				naver_req_url.append("&client_id=");
				naver_req_url.append(naver_login_client_id);
				naver_req_url.append("&access_token=");
				naver_req_url.append(session.getAttribute("ACCESS_TOKEN").toString());
				naver_req_url.append("&client_secret=");
				naver_req_url.append(naver_login_client_secret);
				naver_req_url.append("&service_provider=NAVER");
				
				logger.info("RESULT_naver_req_url:"+naver_req_url.toString());
				
				StringBuffer result = new StringBuffer();
				HttpsClientWithoutValidation httpsClientWithoutValidation = new HttpsClientWithoutValidation();
				result = httpsClientWithoutValidation.getHttps(naver_req_url.toString());
				logger.info("RESULT:"+result.toString());
				CommonUtil.getReturnCodeSuc(json);
			}
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		
		//세션 모두삭제 
		session.invalidate();
	
		return (ModelAndView)new ModelAndView("redirect:/");
	}
	
}
