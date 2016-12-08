package com.mee.sangsil.common;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static final String EMPTY = "";

	/**
	 * 값이 null 이거나 빈값일때 뒤의 값을 반환 한다.
	 * 
	 * @param input
	 * @param defaultValue
	 * @return
	 */
	public static String default2String(String inputStr, String defaultValue) {
		return inputStr == null ? defaultValue : inputStr;
	}

	public static String default2String(String inputStr) {
		return inputStr == null ? EMPTY : inputStr;
	}

	/**
	 *
	 * <pre>
	*   예1 ) 전체 마스킹 : StringUtil.maskToString("010-11@11-2222", -1, 35);
	*            > 결과 : ###-##@##-####
	*   예2 ) 부분 마스킹 : StringUtil.maskToString("010-11@11-2222", 2, 35);
	*             > 결과 : ###-##@##-##22
	*   ------------------------------------------
	*   |  masking 문자  |   masking 문자 바이트     |
	*   ------------------------------------------
	*   |     '*'       |           42           |
	*   ------------------------------------------
	*   |     '#'       |           35           |
	*   ------------------------------------------
	 * </pre>
	 * 
	 * @param val
	 * @param positionCnt
	 * @param maskByte
	 * @return
	 */
	public String maskToString(String val, int positionCnt, int maskByte) {
		String maskedStr = null;
		byte[] bytes = null;
		byte[] maskedBytes = null;

		int bytesLength = 0;

		if (val != null && val != "") {
			bytes = val.getBytes();
			bytesLength = bytes.length;

			if (bytesLength > 0) {
				maskedBytes = new byte[bytesLength];

				for (int i = 0; i < bytesLength; i++) {
					byte byteVal = bytes[i];

					if (byteVal != 45 && byteVal != 64) { // '-' 혹은 '@'은 마스킹 제외.
						if (positionCnt == -1 || (positionCnt != -1 && i > positionCnt))
							byteVal = (byte) maskByte; // 42; // '*'로 마스킹
					}

					maskedBytes[i] = byteVal;
				}
			}
			if (CommonFunction.isNotEmpty(maskedBytes)) {
				maskedStr = new String(maskedBytes, 0, bytesLength);
			}
		}
		return maskedStr;
	}

	/**
	 * 리턴받은 맵에서 KEY 매핑해서 마스킹
	 * 
	 * @param resultMap
	 * @return
	 */
	public HashMap masking_map(HashMap paramMap) {
		HashMap rutMap = new HashMap();
		rutMap.putAll(paramMap);

		if (rutMap.containsKey("CARDNUMBER")) {
			rutMap.put("CARDNUMBER", maskToString(String.valueOf(rutMap.get("CARDNUMBER")), 3, 35));
		}
		if (rutMap.containsKey("VACCT")) {
			rutMap.put("VACCT", maskToString(String.valueOf(rutMap.get("VACCT")), 3, 35));
		}
		if (rutMap.containsKey("VR_ACNT_NO")) {
			rutMap.put("VR_ACNT_NO", maskToString(String.valueOf(rutMap.get("VR_ACNT_NO")), 3, 35));
		}
		if (rutMap.containsKey("ACNT_NO")) {
			rutMap.put("ACNT_NO", maskToString(String.valueOf(rutMap.get("ACNT_NO")), 3, 35));
		}
		return rutMap;
	}

	/**
	 *
	 * @param resultMap
	 * @return
	 */
	public String masking_string(String key, String value) {
		if (key.equalsIgnoreCase("CARDNUMBER")) {
			return maskToString(value, 3, 35);
		}
		if (key.equalsIgnoreCase("VACCT")) {
			return maskToString(value, 3, 35);
		}

		if (key.equalsIgnoreCase("VR_ACNT_NO")) {
			return maskToString(value, 3, 35);
		}

		if (key.equalsIgnoreCase("ACNT_NO")) {
			return maskToString(value, 3, 35);
		}
		return value;
	}

	/**
	 * 가격정보표시
	 * 
	 * @param amt
	 * @param amtDispNm
	 * @return
	 */
	public static String getPriceAMT(String amt, String amtDispNm) {

		if (amt.equals("0"))
			return amtDispNm;
		else {
			return getFormatPayment(amt);
		}
	}

	public static String getFormatPayment(String paymentAmt) {
		// logger.info("paymentAmt:"+paymentAmt);
		DecimalFormat df = new DecimalFormat("###,###");
		double amt = Double.valueOf(paymentAmt);
		String dfResult = df.format(amt) + "원";
		return dfResult;
	}

	public Map<String, List<String>> getParams(HttpServletRequest req) {

		Map<String, String[]> params = req.getParameterMap();
		Map<String, List<String>> rtnMap = new HashMap<String, List<String>>();

		Iterator<String> it = params.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			String[] values = params.get(key);
			rtnMap.put(key, Arrays.asList(values));
		}
		return rtnMap;
	}

	/**
	 * String.valueOf가 null 일때 "null" string 을 봔환해서 공백으로 반환하도록
	 * 
	 * @param object
	 * @return
	 */
	public static String stringValueOf(Object object) {
		return object == null ? "" : String.valueOf(object);
	}

	//전화번호 결합 ex) getHphone('010','1234','5678') -> 01012345678
	public static String getHphone(String str1, String str2, String str3){
		return str1 + str2 + str3;
	}
	//전화번호 결합 ex) getHphone('010','1234','5678', '-') -> 010-1234-5678
	public static String getHphone(String str1, String str2, String str3, String sep){
		return str1 +sep +  str2 + sep + str3;
	}
	
	
	/**
	 * Base64 로 인코딩
	 * @param str
	 * @return
	 */
	public static String base64Encoder(String str) {
		String resultStr = "";
		try {
			byte[] encoded = Base64.encodeBase64(str.getBytes());
			resultStr = new String(encoded);
		}catch(Exception e) {
			logger.debug(e.getMessage());
		}
		logger.debug("base64Encoder:"+resultStr);
		return resultStr;
	}
	/**
	 * Base64 로 디코딩
	 * @param str
	 * @return
	 */
	public static String base64Decoder(String str) {
		String resultStr = "";
		try {
			byte[] decoded = Base64.decodeBase64(str);
			resultStr = new String(decoded);
		}catch(Exception e) {
			logger.debug(e.getMessage());
		}
		logger.debug("base64Decoder:"+resultStr);
		return resultStr;
	}
	
	/**
	 * 입력받은 난수만큼 랜덤 수 생성
	 * @param textSize
	 * @return
	 */
	public static String getRandomText(int textSize){
		Random random = new Random(System.currentTimeMillis());
		int rmSeed = random.nextInt(10);
		String rmText = "";
		int rmNum = 0;
		for (int i = 0; i < textSize; i++) {
			random.setSeed(System.currentTimeMillis() * rmSeed * i + rmSeed + i);
			rmNum = random.nextInt(10);
			rmText += Integer.valueOf(rmNum).toString();
		}
		return rmText;
	}
	
	/**
	 * 입력받은 난수만큼 알파벳 생성
	 * @param textSize
	 * @param rmSeed
	 * @return
	 */
	public static String getRandomText(int textSize , Integer rmSeed){
		Random random = new Random(System.currentTimeMillis());
		if(CommonFunction.isEmpty(rmSeed) || rmSeed <= 0){
			rmSeed = random.nextInt(10);
		}
		String rmText = "";
		int rmNum = 0;
		char ch = 'a';
		for (int i = 0; i < textSize; i++) {
			random.setSeed(System.currentTimeMillis() * rmSeed * i + rmSeed + i);
			rmNum = random.nextInt(10);
			ch += rmNum;
			rmText = rmText + ch ;
			ch = 'a';
		}
		return rmText;
	}
}
