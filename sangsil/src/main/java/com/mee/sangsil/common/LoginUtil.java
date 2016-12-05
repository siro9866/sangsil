package com.mee.sangsil.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.mee.sangsil.dto.UserDto;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class LoginUtil {

	private static final Logger logger = LoggerFactory.getLogger(LoginUtil.class);
	
	//트위터 설정
	@Value("#{config['TWITTER_LOGIN_API_KEY']}") String twitter_login_api_key;
	@Value("#{config['TWITTER_LOGIN_API_SECRET']}") String twitter_login_api_secret;
	
	/**
	 * 네이버아이디로그 인증절차 프로세스 
	 * */
	public JSONObject naverCallback(HashMap<String, String> prop, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//====================================================================================
			// CSRF 방지를 위한 상태 토큰 검증 검증
			// 세션 또는 별도의 저장 공간에 저장된 상태 토큰과 콜백으로 전달받은 state 파라미터의 값이 일치해야 함

			// 콜백 응답에서 state 파라미터의 값을 가져옴
			String state = request.getParameter("state");
			//콜백 응답에서 code 파라미터 값을 가져옴 
			String code = request.getParameter("code");
			//콜백 응답에서 error 파라미터 값을 가져옴 
			String error = request.getParameter("error");
			//콜백 응답에서 error 파라미터 값을 가져옴 
			String error_description = request.getParameter("error_description");
			
			logger.info("naverCallback(콜백응답-state:"+state+",code:"+code+",error:"+error+",error_description:"+error_description);
			
			//에러가 오면 익셉션 떨어뜨
			if(error != null) throw new Exception("인증에러 error:"+error+",error_description:"+error_description);
			
			// 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
			String storedState = session.getAttribute("NAVER_STATE").toString();
			if(!state.equals( storedState ) ) {
				throw new Exception("401 unauthorized");
				//return RESPONSE_UNAUTHORIZED; //401 unauthorized
			} else {
				//return RESPONSE_SUCCESS; //200 success
				logger.info("네이버 state 응답: 200 success");
			}
			//====================================================================================
			
			//====================================================================================
			// 접근 토큰 발급 요청
			// https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id={클라이언트 아이디}&client_secret={클라이언트 시크릿}&code={인증 코드}&state={상태 토큰}
			//client_id: 애플리케이션 등록 후 발급받은 클라이언트 아이디
			//client_secret: 애플리케이션 등록 후 발급받은 클라이언트 시크릿
			//grant_type: 인증 타입에 대한 구분값. authorization_code로 값이 고정돼 있습니다.
			//state: 애플리케이션이 생성한 상태 토큰
			//code: 콜백으로 전달받은 인증 코드
			
			StringBuffer naver_getToken_url = new StringBuffer();
			naver_getToken_url.append(prop.get("naver_login_url"));
			naver_getToken_url.append("/token");
			naver_getToken_url.append("?grant_type=authorization_code");
			naver_getToken_url.append("&client_id=");
			naver_getToken_url.append(prop.get("naver_login_client_id"));
			naver_getToken_url.append("&client_secret=");
			naver_getToken_url.append(prop.get("naver_login_client_secret"));
			naver_getToken_url.append("&code=");
			naver_getToken_url.append(code);
			naver_getToken_url.append("&state=");
			naver_getToken_url.append(state);
			naver_getToken_url.append("&response_type=token");
			logger.debug("naver_getToken_url:"+naver_getToken_url.toString());

			StringBuffer result = new StringBuffer();
			HttpsClientWithoutValidation httpsClientWithoutValidation = new HttpsClientWithoutValidation();
			//httpsClientWithoutValidation.getHttps(naver_getToken_url.toString());
			result = httpsClientWithoutValidation.getHttps(naver_getToken_url.toString());
			logger.info("RESULT:"+result.toString());
			
			// 접근 토큰 json파싱 
			Map map = Collections.EMPTY_MAP;
			map = JsonParserUtil.getJsonParser(result.toString());
			String access_token, refresh_token, token_type, expires_in;
			access_token = map.get("access_token").toString();
			refresh_token = map.get("refresh_token").toString();
			token_type = map.get("token_type").toString();
			expires_in = map.get("expires_in").toString();
			
			session.setAttribute("ACCESS_TOKEN", access_token);
			logger.debug("access_token:"+access_token+"\n"+"refresh_token:"+refresh_token+"\n"+"token_type:"+token_type+"\n"+"expires_in:"+expires_in+"\n");
			//====================================================================================
			
			
			//====================================================================================
			// 네이버 사용자 기본 정보 조회
			
			//https://openapi.naver.com/v1/nid/getUserProfile.xml
			//요청예
			//User-Agent: curl/7.12.1 (i686-redhat-linux-gnu) libcurl/7.12.1 OpenSSL/0.9.7a zlib/1.2.1.2 libidn/0.5.6
			//Host: openapi.naver.com
			//Pragma: no-cache
			//Accept: */*
			//Authorization: Bearer AAAAOLtP40eH6P5S4Z4FpFl77n3FD5I+W3ost3oDZq/nbcS+7MAYXwXbT3Y7Ib3d
			//nvcqHkcK0e5/rw6ajF7S/QlJAgUukpp1OGkG0vzi16hcRNYX6RcQ6kPxB0oAvqfUPJiJw==
			String xmlStr = httpsClientWithoutValidation.getHttpProp("https://openapi.naver.com/v1/nid/getUserProfile.xml", token_type + " " + access_token).toString();
			//응답은 xml 형식으로 다음과 같이 옴
			//<data>
			//	<result>
			//		<resultcode>00</resultcode>
			//		<message>success</message>
			//	</result>
			//	<response>
			//		<email><![CDATA[siro9866@naver.com]]></email>
			//		<nickname><![CDATA[이경원]]></nickname>
			//		<enc_id><![CDATA[b3c8587691494c086e68f34f146afe8416f83033cd549b9d26381113674e84e7]]></enc_id>
			//		<profile_image><![CDATA[https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif]]></profile_image>
			//		<age><![CDATA[30-39]]></age>
			//		<gender>M</gender>
			//		<id><![CDATA[95712832]]></id>
			//		<name><![CDATA[이경원]]></name>
			//		<birthday><![CDATA[07-26]]></birthday>
			//	</response>
			//</data>
			logger.debug("xmlStr:"+xmlStr);
			String resultcode = "";
			String message = "";
			String email = "";
			String nickname = "";
			String enc_id = "";
			String profile_image = "";
			String age = "";
			String gender = "";
			String id = "";
			String name = "";
			String birthday = "";
			
			resultcode = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_result") + "/resultcode").get(0);
			message = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_result") + "/message").get(0);
			
			logger.info("resultcode:"+resultcode);
			logger.info("message:"+message);
			
			if(!resultcode.equals("00")){
				throw new Exception(message);
			}else{
				email = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/email").get(0);
				nickname = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/nickname").get(0);
				enc_id = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/enc_id").get(0);
				profile_image = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/profile_image").get(0);
				age = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/age").get(0);
				gender = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/gender").get(0);
				id = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/id").get(0);
				name = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/name").get(0);
				birthday = XPathUtil.getNodeList(xmlStr, prop.get("naver_login_xml_response") + "/birthday").get(0);
				
				//회원정보값 세션에 저장
				session.setAttribute("P_ID", id);
				session.setAttribute("P_NICKNAME", nickname);
				session.setAttribute("P_EMAIL", email);
				session.setAttribute("P_ENC_ID", enc_id);
				session.setAttribute("P_PROFILE_IMAGE", profile_image);
				session.setAttribute("P_AGE", age);
				session.setAttribute("P_GENDER", gender);
				session.setAttribute("P_NAME", name);
				session.setAttribute("P_BIRTHDAY", birthday);
			}
			
			logger.debug("email:"+email);
			logger.debug("nickname:"+nickname);
			logger.debug("enc_id:"+enc_id);
			logger.debug("profile_image:"+profile_image);
			logger.debug("age:"+age);
			logger.debug("gender:"+gender);
			logger.debug("id:"+id);
			logger.debug("name:"+name);
			logger.debug("birthday"+birthday);
			
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		logger.info(json.toJSONString());
		return json;
	}
	
	
	/**
	 * 구글아이디로그 인증절차 프로세스 
	 */
	public JSONObject googleCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		logger.info(json.toJSONString());
		return json;
	}
	
	
	
	public JSONObject kakaoCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		JSONObject propertiesJson = new JSONObject();
		
		try {
			//====================================================================================
			// 로그인시 전달 받은 데이타 처리 
			//{"access_token":"9x0pwqdkdrAsnYByMkLpDRsTeycX-BcRpdg6ZawQQI4AAAFSTubkXg","token_type":"bearer","refresh_token":"UQ5A0ypAoh-4dNjaY2SSfJ2nrGqwnmdc0xg0bKwQQI4AAAFSTubkXA","expires_in":7199,"scope":"story_publish story_read profile"}
			//{"id":82401986,"properties":{"nickname":"이경원","thumbnail_image":"http://mud-kage.kakao.co.kr/14/dn/btqcEDsbn93/OxLs8wHLkWvH1Ya3YUWCE1/o.jpg","profile_image":"http://mud-kage.kakao.co.kr/14/dn/btqcFfYIHL1/J3t2iiDkBVEXTRQ7UG0NR0/o.jpg"}}
			
			String param_kakaoAouth = request.getParameter("param_kakaoAouth");
			String param_kakaoProfile = request.getParameter("param_kakaoProfile");
			logger.info("param_kakaoAouth:"+param_kakaoAouth);
			logger.info("param_kakaoProfile:"+param_kakaoProfile);
			
			// 접근 토큰 json파싱 
			//Aouth 설정 
			Map mapA = Collections.EMPTY_MAP;
			mapA = JsonParserUtil.getJsonParser(param_kakaoAouth);
			String access_token, refresh_token, token_type, expires_in, scope;
			access_token = mapA.get("access_token").toString();
			refresh_token = mapA.get("refresh_token").toString();
			token_type = mapA.get("token_type").toString();
			expires_in = mapA.get("expires_in").toString();
			scope = mapA.get("scope").toString();
			
			logger.debug("JSON CHECK1:\n"+access_token+"\n"+refresh_token+"\n"+token_type+"\n"+expires_in+"\n"+scope);
			
			//개인정보설정 
			Map mapP = Collections.EMPTY_MAP;
			mapP = JsonParserUtil.getJsonParser(param_kakaoProfile);
			String id, properties, nickname, profile_image;
			id = mapP.get("id").toString();
			
			propertiesJson = JsonParserUtil.getJsonObject(param_kakaoProfile);
			properties = propertiesJson.get("properties").toString();
			
			Map mapQ = Collections.EMPTY_MAP;
			mapQ = JsonParserUtil.getJsonParser(properties);
			nickname = mapQ.get("nickname").toString();
			profile_image = mapQ.get("profile_image").toString();
			
			logger.debug("JSON CHECK2:\n"+id+"\n"+properties+"\n"+nickname+"\n"+profile_image);
			
			session.setAttribute("ACCESS_TOKEN", access_token);
			logger.debug("access_token:"+access_token+"\n"+"refresh_token:"+refresh_token+"\n"+"token_type:"+token_type+"\n"+"expires_in:"+expires_in+"\n");
			
			//회원정보값 세션에 저장
			session.setAttribute("P_ID", id);
			session.setAttribute("P_NICKNAME", nickname);
			session.setAttribute("P_EMAIL", "");
			session.setAttribute("P_ENC_ID", "");
			session.setAttribute("P_PROFILE_IMAGE", profile_image);
			session.setAttribute("P_AGE", "");
			session.setAttribute("P_GENDER", "");
			session.setAttribute("P_NAME", "");
			session.setAttribute("P_BIRTHDAY", "");

			
			logger.debug("nickname:"+nickname);
			logger.debug("profile_image:"+profile_image);
			logger.debug("id:"+id);
			
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		logger.info(json.toJSONString());
		return json;
	}
	
	/**
	 * 페이스북아이디로그인 다른정보 가져오기 
	 * */
	public JSONObject facebookCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		JSONObject jsonp = new JSONObject();
		String jsonStr = "";
		
		try {
			//====================================================================================
			// 로그인시 전달 받은 데이타 처리 
			//{"email":"siro9866@gmail.com","name":"Kyungwon Lee","id":"102870046763273"}
			//{"authResponse":{"accessToken":"CAAPn20gW9UMBAGMbNnqAmIwUzLmHFSDVwwGZBdMI2ZAdsVPLJJCKDUDiXc4M2ThiAFvJTp0FWyZAkEiXaaAO5PlGz43qPnWWfKJLGgtnnhSQ7wKC1OcobByZCZB25vr3kk6Um9BOjSL6PGm56QVM1u4hVJI0RIbMWNgTeKrJl7F0ljQsgJXHZAzWxVWqNVvsQ1UOtgOHIEbQZDZD","userID":"102870046763273","expiresIn":5267,"signedRequest":"NInSQSXOkBEt2kyP4D-_ozz4UNJHuG7NdZsuDHAM-UU.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImNvZGUiOiJBUUNaSlN3dkFnQ3dMYW5nb0pSckVWSEZINFVJX3RsVWVtT1JUUUNndmFsUjdyUWpRX3QzaFNGWFQwSFY5SWhEQ3IyZ3lEbjFkMV9aUTJtOVVYR2VHQWZBMmlKbl9zSjBOT21zb19kdkdocy1meWstUkd4ajh5ZDZRb3dGaElEaHgtY2w3WnMwMFE0YU5YNkdiTWdleVpXSWtGQ2tFMjlhUFVNallQSDh3ODVpX2thODg1VWc5Z0lqS2drcllhX2Q5NUlYY0Z1QzVoQzdiRlNpbjg3UmEzRE5wRzRUN3ZXREItS0xzbDZmYkVBdFhsQU4wa0hYcE4xaXNsdDV1XzBNcFlHeko5emZfdkducVZtR3RtWUE1ckoyYkhYTjIwSEFQN2VmdWpROGhFdE5sU3pMczhOZjB2YVdhQWIwQS03OWtscU50X1lRV2tJeHpuUnRaQzc1aUI4dCIsImlzc3VlZF9hdCI6MTQ1MzIwMzEzMiwidXNlcl9pZCI6IjEwMjg3MDA0Njc2MzI3MyJ9"},"status":"connected"}
			String param_facebooMe = request.getParameter("param_facebooMe");
			String param_facebookStats = request.getParameter("param_facebookStats");
			
			jsonp = JsonParserUtil.getJsonObject(param_facebookStats);
			jsonStr = jsonp.get("authResponse").toString();
			// 접근 토큰 json파싱 
			//authResponse 정보 
			Map mapA = Collections.EMPTY_MAP;
			mapA = JsonParserUtil.getJsonParser(jsonStr);
			String access_token, signedRequest, expires_in;
			access_token = mapA.get("accessToken").toString();
			signedRequest = mapA.get("signedRequest").toString();
			expires_in = mapA.get("expiresIn").toString();
			
			logger.debug("JSON CHECK1:\n"+access_token+"\n"+signedRequest+"\n"+expires_in);
			
//			개인정보설정 
			Map mapP = Collections.EMPTY_MAP;
			mapP = JsonParserUtil.getJsonParser(param_facebooMe);
			String id, email, name;
			id = mapP.get("id").toString();
			email = mapP.get("email").toString();
			name = mapP.get("name").toString();

			//회원정보값 세션에 저장
			session.setAttribute("P_ID", id);
			session.setAttribute("P_NICKNAME", "");
			session.setAttribute("P_EMAIL", email);
			session.setAttribute("P_ENC_ID", "");
			session.setAttribute("P_PROFILE_IMAGE", "");
			session.setAttribute("P_AGE", "");
			session.setAttribute("P_GENDER", "");
			session.setAttribute("P_NAME", name);
			session.setAttribute("P_BIRTHDAY", "");

			
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		logger.info(json.toJSONString());
		return json;
	}
	
	/**
	 * 트위터로그 인증절차 프로세스 
	 * */
	public JSONObject twitterCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			Twitter twitter = new TwitterFactory().getInstance();           
			twitter.setOAuthConsumer(twitter_login_api_key, twitter_login_api_secret); //저장된 consumerKey, consumerSecret
			AccessToken accessToken = null;     

			//callbackURL에서 전달받은 oauth_verifier
			String oauth_verifier = request.getParameter("oauth_verifier");

			//트위터 로그인 연동시 담은 requestToken 의 세션값을 가져온다.
			RequestToken requestToken = (RequestToken )request.getSession().getAttribute("REQUEST_TOKEN");           
			accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);            
			twitter.setOAuthAccessToken(accessToken);

			//해당 트위터 사용자의 이름과 아이디를 가져온다.
			System.out.println(accessToken.getUserId());    //트위터의 사용자 아이디
			System.out.println(accessToken.getScreenName()); //트워터에 표시되는 사용자명   

			//회원정보값 세션에 저장
			session.setAttribute("P_ID", accessToken.getUserId());
			session.setAttribute("P_NICKNAME", "");
			session.setAttribute("P_EMAIL", "");
			session.setAttribute("P_ENC_ID", "");
			session.setAttribute("P_PROFILE_IMAGE", "");
			session.setAttribute("P_AGE", "");
			session.setAttribute("P_GENDER", "");
			session.setAttribute("P_NAME", accessToken.getScreenName());
			session.setAttribute("P_BIRTHDAY", "");
			
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		logger.info(json.toJSONString());
		return json;
	}
	
	public JSONObject meeCallback(UserDto userDto, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//회원정보값 세션에 저장
			session.setAttribute("P_ID", userDto.getP_id());
			session.setAttribute("P_NICKNAME", "");
			session.setAttribute("P_EMAIL", "");
			session.setAttribute("P_ENC_ID", "");
			session.setAttribute("P_PROFILE_IMAGE", "");
			session.setAttribute("P_AGE", "");
			session.setAttribute("P_GENDER", "");
			session.setAttribute("P_NAME", "");
			session.setAttribute("P_BIRTHDAY", "");
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		return json;
	}
}
