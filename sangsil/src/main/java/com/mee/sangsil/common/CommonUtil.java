package com.mee.sangsil.common;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mee.sangsil.dto.ComDto;

import net.sf.json.JSONObject;


public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * 성공값 리턴
	 * @param json
	 */
	public static void getReturnCodeSuc(JSONObject json){
		json.put("rCode", "0000");
		json.put("rMsg", "성공");
	}
	
	/**
	 * 오류는 아니지만 특별한 메세지가 필요할 경우
	 */
	public static void getReturnCodeSuc(JSONObject json, String rMsg){
		json.put("rCode", "1111");
		json.put("rMsg", rMsg);
	}
	
	/**
	 * 실패 값 리턴
	 * @param json
	 */
	public static void getReturnCodeFail(JSONObject json){
		json.put("rCode", "9999");
		json.put("rMsg", "실패");
	}
	
	/**
	 * Exception 내용
	 * @param json
	 */	
	public static void getReturnCodeFail(JSONObject json, Exception e){
		json.put("rCode", "9999");
		json.put("rMsg", "실패");
		json.put("rReason", e.toString());
		logger.info("FAIL_PROCESS:"+json.toString());
	}

	/**
	 * 실패사유전
	 * @param json
	 * @param str
	 */
	public static void getReturnCodeFail(JSONObject json, String str){
		json.put("rCode", "9999");
		json.put("rMsg", "실패");
		json.put("rReason", str);
		logger.info("FAIL_PROCESS:"+json.toString());
	}
	
	/**
	 * 파일용량제한 10M
	 * @param json
	 */
	public static void getReturnCodeFailFileSize(JSONObject json){
		json.put("rCode", "8887");
		json.put("rMsg", "10M 용량제한");
	}

	/**
	 * 실패 값 리턴
	 * @param json
	 */
	public static void getReturnCodeNotFile(JSONObject json){
		json.put("rCode", "8888");
		json.put("rMsg", "파일 첨부 없음");
	}
	
	/**
	 * client ip 가져오기
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request){
		String clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
		if(null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")){
		  clientIp = request.getHeader("REMOTE_ADDR");
		}
		 
		if(null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")){
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}
	
	public static void setInUserInfo(HttpServletRequest request, ComDto comDto){
		comDto.setIn_user((String)request.getSession().getAttribute("USER_ID"));
		try {
			comDto.setIn_ip(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setUpUserInfo(HttpServletRequest request, ComDto comDto){
		comDto.setUp_user((String)request.getSession().getAttribute("USER_ID"));
		try {
			comDto.setUp_ip(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 네로아 - STATE 코드 생
	 * @return
	 */
	public static String generateState(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String)
			return obj == null || "".equals(obj.toString().trim());
		else if (obj instanceof List)
			return obj == null || ((List) obj).isEmpty();
		else if (obj instanceof Map)
			return obj == null || ((Map) obj).isEmpty();
		else if (obj instanceof Object[])
			return obj == null || Array.getLength(obj) == 0;
		else
			return obj == null;
	}

	public static boolean isNotEmpty(Object s) {
		return !isEmpty(s);
	}
	
}
