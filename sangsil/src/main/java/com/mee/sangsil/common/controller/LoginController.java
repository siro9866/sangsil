package com.mee.sangsil.common.controller;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import com.mee.sangsil.common.CommonFunction;
import com.mee.sangsil.common.CommonUtil;
import com.mee.sangsil.common.LoginUtil;
import com.mee.sangsil.common.service.LoginService;
import com.mee.sangsil.dto.CdDto;
import com.mee.sangsil.dto.UserDto;

@Controller
@RequestMapping("/common/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;
	
	
	//로그인기관구분 
	@Value("#{config['LOGIN_GBN_00']}") String login_gbn_00;
	@Value("#{config['LOGIN_GBN_01']}") String login_gbn_01;
	@Value("#{config['LOGIN_GBN_02']}") String login_gbn_02;
	@Value("#{config['LOGIN_GBN_03']}") String login_gbn_03;
	@Value("#{config['LOGIN_GBN_04']}") String login_gbn_04;
	@Value("#{config['LOGIN_GBN_05']}") String login_gbn_05;
	//네로아 설정
	@Value("#{config['NAVER_LOGIN_URL']}") String naver_login_url;
	@Value("#{config['NAVER_LOGIN_CLIENT_ID']}") String naver_login_client_id;
	@Value("#{config['NAVER_LOGIN_REDIRECT_URI']}") String naver_login_redirect_uri;
	@Value("#{config['NAVER_LOGIN_CLIENT_SECRET']}") String naver_login_client_secret;
	@Value("#{config['NAVER_LOGIN_XML_RESULT']}") String naver_login_xml_result;
	@Value("#{config['NAVER_LOGIN_XML_RESPONSE']}") String naver_login_xml_response;
	//트위터 설정
	@Value("#{config['TWITTER_LOGIN_API_KEY']}") String twitter_login_api_key;
	@Value("#{config['TWITTER_LOGIN_API_SECRET']}") String twitter_login_api_secret;
	@Value("#{config['TWITTER_LOGIN_API_OWNER']}") String twitter_login_api_owner;
	@Value("#{config['TWITTER_LOGIN_API_ID']}") String twitter_login_api_id;
	
	//회원권한 전체관리자, 일반관리자, 일반회원 
	@Value("#{config['CD_ID_AAA01']}") String cd_id_aaa01;
	@Value("#{config['CD_ID_AAA02']}") String cd_id_aaa02;
	@Value("#{config['CD_ID_AAA10']}") String cd_id_aaa10;
	
	
	/**
	 * 로그인
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/form")
	public ModelAndView form(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		//세션 초기화 
		session.invalidate();
		mav.setViewName("/common/login/login_form");
		return mav;
	}
	
	/**
	 * 권한 없을 경우 페이지
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/assessDenied")
	public ModelAndView assessDenied(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/common/login/assessDenied");
		logger.info("pageView:"+mav.getViewName());
		return mav;
	}
	
	/**
	 * Mee 로그인 처리
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/form_mee")
	@ResponseBody
	public JSONObject form_mee(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		UserDto user = new UserDto();
		LoginUtil loginUtil = new LoginUtil();
		try {
			session.setAttribute("LOGIN_GBN", login_gbn_00);
			userDto.setLogin_gbn(login_gbn_00);
			String sql = "common.login.login";
			user = loginService.login(sql, userDto);
			
			// 입력한 비밀번호와 비교
			if(user == null){
				CommonUtil.getReturnCodeFail(json, "로그인 정보가 틀립니다.");
			}else{
				if(user.getP_pw().equalsIgnoreCase(userDto.getP_pw())){
					loginUtil.meeCallback(user, session);
					json.put("mee_req_url", "/common/login/loginCallback.mee");
					CommonUtil.getReturnCodeSuc(json);
				}else{
					CommonUtil.getReturnCodeFail(json, "로그인 정보가 틀립니다.");
				}
			}
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		return json;
	}
	
	/**
	 * 네이버 인증페이지 호출(인증코드요청)
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/form_naver")
	@ResponseBody
	public JSONObject form_naver(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			session.setAttribute("LOGIN_GBN", login_gbn_01);
			//네로아 요청URL
			//"https://nid.naver.com/oauth2.0/authorize?client_id={클라이언트 아이디}&response_type=code&redirect_uri={개발자 센터에 등록한 콜백 URL(URL 인코딩)}&state={상태 토큰}";
			// 상태 토큰으로 사용할 랜덤 문자열 생성
			String state = CommonUtil.generateState();
			session.setAttribute("NAVER_STATE", state);
			// 세션 또는 별도의 저장 공간에 상태 토큰을 저장
			String naver_login_redirect_uri_enc = URLEncoder.encode(naver_login_redirect_uri, "UTF-8");
			StringBuffer naver_req_url = new StringBuffer();
			naver_req_url.append(naver_login_url);
			naver_req_url.append("/authorize");
			naver_req_url.append("?client_id=");
			naver_req_url.append(naver_login_client_id);
			naver_req_url.append("&response_type=code&redirect_uri=");
			naver_req_url.append(naver_login_redirect_uri_enc);
			naver_req_url.append("&state=");
			naver_req_url.append(state);
			
			json.put("naver_req_url", naver_req_url.toString());
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		logger.info(json.toJSONString());
		return json;
	}
	
	/**
	 * 구글 인증페이지 호출(인증코드요청)
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/form_google")
	@ResponseBody
	public JSONObject form_google(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			session.setAttribute("LOGIN_GBN", login_gbn_03);
			
			OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
			String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
			
			json.put("google_req_url", url.toString());
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		logger.info(json.toJSONString());
		return json;
	}
	/**
	 * 트위터 인증페이지 호출(인증코드요청)
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/form_twitter")
	@ResponseBody
	public JSONObject form_twitter(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			session.setAttribute("LOGIN_GBN", login_gbn_05);
			StringBuffer twitter_req_url = new StringBuffer();
			String twitter_url, twitter_token, twitter_tokenScret;
			Twitter twitter = new TwitterFactory().getInstance();
			//twitter로 접근한다.
			twitter.setOAuthConsumer(twitter_login_api_key, twitter_login_api_secret);
			//성공시 requestToken에 해당정보를 담겨져온다.
			RequestToken requestToken =  twitter.getOAuthRequestToken();
			//requestToken 을 반드시 세션에 담아주어야 한다.
			request.getSession().setAttribute("REQUEST_TOKEN", requestToken);
			twitter_url = requestToken.getAuthorizationURL();  //접속할 url값이 넘어온다.
			twitter_token = requestToken.getToken(); //token값을 가져온다.
			twitter_tokenScret = requestToken.getTokenSecret(); //token Secret값을 가져온다.
			
			twitter_req_url.append(twitter_url);
			twitter_req_url.append("?oauth_token");
			twitter_req_url.append(twitter_token);
			
			json.put("twitter_req_url", twitter_req_url.toString());
			
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		logger.info(json.toJSONString());
		return json;
	}
	
	
	
	/**
	 * 로그인연동 인증후 콜백 (AccessToken 요청 )
	 * 파람{개발자 센터에 등록한 콜백 URL}?state={상태 토큰}&code={인증 코드}
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/loginCallback")
	public ModelAndView loginCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		JSONObject json = new JSONObject();
		LoginUtil loginUtil = new LoginUtil();
		UserDto userDto = new UserDto();
		HashMap<String, String> prop = new HashMap<String, String>();
		
		logger.info("===================================================================================================");
		logger.info("로그인 콜백 들어왔음");
		logger.info("===================================================================================================");
		
		try {
			//네이버로그인
			if(session.getAttribute("LOGIN_GBN") != null){
				
				if(session.getAttribute("LOGIN_GBN").toString().equalsIgnoreCase(login_gbn_00)){
					logger.info("MEE 로그인");
				}else if(session.getAttribute("LOGIN_GBN").toString().equalsIgnoreCase(login_gbn_01)){
					//네이버 인증최종받기와 회원정보 세팅
					prop.put("naver_login_url", naver_login_url);
					prop.put("naver_login_client_id", naver_login_client_id);
					prop.put("naver_login_redirect_uri", naver_login_redirect_uri);
					prop.put("naver_login_client_secret", naver_login_client_secret);
					prop.put("naver_login_xml_result", naver_login_xml_result);
					prop.put("naver_login_xml_response", naver_login_xml_response);
					loginUtil.naverCallback(prop, request, response, session);
				}else if(session.getAttribute("LOGIN_GBN").toString().equalsIgnoreCase(login_gbn_03)){
					//구글
					//http://localhost:8080/common/login/loginCallback.mee?code=4/NkNpmu4qqm4THThXh9ltHi2bh1SFFLX_d4QBMgodjbo&authuser=0&session_state=c57c8c8cc4ed5eebb8e507a4dbece06ac5cc655b..2c44&prompt=consent#
					String code = request.getParameter("code");
					logger.info("GOOGLE_LOGIN START!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					logger.info("code:"+code);
					OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
					logger.info("1111111111");
					AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, googleOAuth2Parameters.getRedirectUri(), null);
					logger.info("22222222");
					String accessToken = accessGrant.getAccessToken();
					logger.info("33333333");
					Long expireTime =  accessGrant.getExpireTime();
					logger.info("4444444444");
					if (expireTime != null && expireTime < System.currentTimeMillis()) {
						logger.info("5555555");
						accessToken = accessGrant.getRefreshToken();
						logger.info("accessToken is expired. refresh token = {}" , accessToken);
					}
					logger.info("6666666");
					
					Connection<Google>connection = googleConnectionFactory.createConnection(accessGrant);
					Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi();
							
					PlusOperations plusOperations = google.plusOperations();
					Person person = plusOperations.getGoogleProfile();
					
					logger.info("person:"+person.getAboutMe());
				}else if(session.getAttribute("LOGIN_GBN").toString().equalsIgnoreCase(login_gbn_05)){
					//트위
					//http://localhost:8080/twitter-response.jsp?oauth_token=qwd1AAAWeDHAAABUEXVgyE&oauth_verifier=Ae12e12Xsu5VIWhs0wq2
					loginUtil.twitterCallback(request, response, session);
					
				}else{throw new Exception("LOGIN_GBN 세션 값이 없음");}
				
			}else{
				
				String param_login_gbn = request.getParameter("param_login_gbn");
				
				//카카오톡로그인 
				if(!param_login_gbn.isEmpty() && param_login_gbn != null){
					if(param_login_gbn.equalsIgnoreCase(login_gbn_02)){
						logger.info("통하였느냐!!!!!!:"+login_gbn_02);
						session.setAttribute("LOGIN_GBN", login_gbn_02);
						loginUtil.kakaoCallback(request, response, session);
					}else if(param_login_gbn.equalsIgnoreCase(login_gbn_04)){
						logger.info("통하였느냐!!!!!!:"+login_gbn_04);
						session.setAttribute("LOGIN_GBN", login_gbn_04);
						loginUtil.facebookCallback(request, response, session);
					}else if(param_login_gbn.equalsIgnoreCase(login_gbn_00)){
						logger.info("통하였느냐!!!!!!:"+login_gbn_00);
						
					}
				}else{
					throw new Exception("param_login_gbn 이 없음");
				}
			}
			
			
			//로그인 히스토리 저장할 빈 데이타생성 
			userDto.setLogin_gbn(session.getAttribute("LOGIN_GBN").toString());
			System.out.println("11111111111");
			userDto.setP_id(session.getAttribute("P_ID").toString());
			System.out.println("2222222");
			userDto.setP_email(session.getAttribute("P_EMAIL").toString());
			System.out.println("3333333");
			userDto.setP_nickname(session.getAttribute("P_NICKNAME").toString());
			System.out.println("44444444");
			userDto.setP_age(session.getAttribute("P_AGE").toString());
			System.out.println("5555555");
			userDto.setP_gender(session.getAttribute("P_GENDER").toString());
			System.out.println("666666666");
			userDto.setP_name(session.getAttribute("P_NAME").toString());
			System.out.println("7777777777");
			userDto.setP_birthday(session.getAttribute("P_BIRTHDAY").toString());
			System.out.println("8888888888");
			CommonUtil.setInUserInfo(request, userDto);

			//로그인 회원 체크
			UserDto user = new UserDto();
			String sql2 = "common.login.login";
			user = loginService.login(sql2, userDto);
			
			// 등록된 회원이 아니라면 회원정보 저장
			if(user == null){
				try {
					loginService.insert_user("common.login.insert_user", userDto);
					user = loginService.login(sql2, userDto);
				} catch (Exception e) {
					throw new Exception("회원 정보 저장 실패",e);
				}
				
			}
			
			if(CommonFunction.isNotNull(user.getUser_id())){
				
				// 회원정보 세션 저장
				session.setAttribute("USER_ID", user.getUser_id());
				// 회원권한
				session.setAttribute("USER_AUTH", user.getUser_auth());
				// 본 사이트에서 활동할 유저 이름(고유한 이름이어야함)
				session.setAttribute("USER_NM", user.getUser_nm());
				
				// 로그인 한 로그인 정보 업데이트
				loginService.update("common.login.update_login", user);
			}else{
				throw new Exception("회원 정보 저장 실패!");
			}
			
			try {
				//로그인 히스토리 저장 
				CommonUtil.setInUserInfo(request, user);
				loginService.insert_history("common.login.insert_history", user, request);
			} catch (Exception e) {
				throw new Exception("로그인 히스토리 저장 정보 저장 실패",e);
			}
			
		} catch (Exception e) {
			logger.info("로그인 전체적인 실패");
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		mav.setViewName("redirect:/");
		
		return mav;
	}
	
	/**
	 * 회원정보 수정 페이지(최초 로그인시 이름 없음)
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/infoForm")
	public ModelAndView infoForm(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/common/login/login_infoForm");
		logger.info("pageView:"+mav.getViewName());
	
		return mav;
	}
	
	
	/**
	 * 회원 이름 등록
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/update_user_nm")
	@ResponseBody
	public JSONObject update_user_nm(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		JSONObject json = new JSONObject();
		UserDto user = new UserDto();
		
		try {
			// 이름 중복사용 있는지 확인
			user = loginService.login("common.login.loginInfo", userDto);
			
			if(user == null){
				// 이름이 중복이 아니라면 저장
				userDto.setUser_id(session.getAttribute("USER_ID").toString());
				CommonUtil.setUpUserInfo(request, userDto);
				loginService.update("common.login.update_user_nm", userDto);
				session.setAttribute("USER_NM", userDto.getUser_nm());
				CommonUtil.getReturnCodeSuc(json);
			}else{
				// 전달 코드 1111
				CommonUtil.getReturnCodeSuc(json, "이미 사용중입니다");
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
		} finally {
			logger.info(json.toJSONString());
			return json;
		}
		
	}
}
