package com.mee.sangsil.common;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonFunction {

	private static final Logger logger = LoggerFactory.getLogger(CommonFunction.class);

//================================================================================================================	
//================================================================================================================	
//	S:스트링	
//================================================================================================================	
//================================================================================================================	
	
	
	/**
	 * 널 여부
	 * @param obj
	 * @return null: true, not null: false
	 */
	public static Boolean isNull(Object obj){
		Boolean result = false;
		
		if(obj == null){
			result = true;
		}
		
		return result;
	}
	
	/**
	 * 낫 널 여부 
	 * @param obj
	 * @return null: false, not null: true
	 */
	public static Boolean isNotNull(Object obj){
		Boolean result = true;
		
		if(obj == null){
			result = false;
		}else{
			result = true;
		}
		
		return result;
	}
	
	public static boolean isEmpty(Object obj){
		if( obj instanceof String ) return obj==null || "".equals(obj.toString().trim());
		else if( obj instanceof List ) return obj==null || ((List)obj).isEmpty();
		else if( obj instanceof Map ) return obj==null || ((Map)obj).isEmpty();
		else if( obj instanceof Object[] ) return obj==null || Array.getLength(obj)==0;
		else return obj==null;
	}
	public static boolean isNotEmpty(Object s){
		return !isEmpty(s);
	}
	
	
	/**
	 * 값이 null 이거나 빈값일때 뒤의 값을 반환 한다.
	 * @param input
	 * @param defaultValue
	 * @return
	 */
	public static String default2String ( String input , String defaultValue ) {

		logger.info("input:"+input);
		logger.info("defaultValue:"+defaultValue);
		
		if (isNotEmpty(input) ) {
			return input.trim();
		} else {
			if ( isEmpty(defaultValue)) {
				return "";
			} else {
				return defaultValue.trim();
			}
		}
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
	* @param val
	* @param positionCnt
	* @param maskByte
	* @return
	*/
   public  String maskToString(String val, int positionCnt, int maskByte)
	   {
	   String maskedStr    = null;
	   byte[] bytes        = null;
	   byte[] maskedBytes  = null;

	   int bytesLength     = 0;

	   if(val != null && val != ""){
	       bytes       = val.getBytes();
	       bytesLength = bytes.length;

	       if(bytesLength > 0){
	           maskedBytes = new byte[bytesLength];

	           for(int i = 0 ; i < bytesLength ; i++){
	               byte byteVal = bytes[i];

	               if(byteVal != 45 && byteVal != 64){     // '-' 혹은 '@'은 마스킹 제외.
	                   if( positionCnt == -1
	                   || (positionCnt != -1 && i >positionCnt))
	                       byteVal = (byte) maskByte; //42;    // '*'로 마스킹
	               }

	               maskedBytes[i] = byteVal;
	           }
	       }
	       if(isNotEmpty(maskedBytes) && isNotEmpty(maskedBytes)){
	    	   maskedStr = new String(maskedBytes,0,bytesLength);
	       }
	   }
	   return maskedStr;
   }

	/**
	 * 리턴받은 맵에서 KEY 매핑해서 마스킹
	 * @param resultMap
	 * @return
	 */
	 public HashMap masking_map(HashMap paramMap)
	 {
		 HashMap rutMap = new HashMap();
			rutMap.putAll(paramMap); 
		 
		if(rutMap.containsKey("CARDNUMBER"))	{
			rutMap.put("CARDNUMBER", maskToString(String.valueOf(rutMap.get("CARDNUMBER")), 3, 35));
		}
		if(rutMap.containsKey("VACCT")){
			rutMap.put("VACCT", maskToString(String.valueOf(rutMap.get("VACCT")), 3, 35));
		}
		if(rutMap.containsKey("VR_ACNT_NO")){ 
			rutMap.put("VR_ACNT_NO", maskToString(String.valueOf(rutMap.get("VR_ACNT_NO")), 3, 35));
		}
		if(rutMap.containsKey("ACNT_NO")){
			rutMap.put("ACNT_NO", maskToString(String.valueOf(rutMap.get("ACNT_NO")), 3, 35));
		} 
		return rutMap;
	}

/**
 *
 * @param resultMap
 * @return
 */
 public String masking_string(String key, String value)
 { 
	if(key.equalsIgnoreCase("CARDNUMBER"))	{
		return  maskToString(value, 3, 35);
	} 
	if(key.equalsIgnoreCase("VACCT"))	{
		return  maskToString(value, 3, 35);
	}

	if(key.equalsIgnoreCase("VR_ACNT_NO"))	{
		return  maskToString(value, 3, 35);
	}

	if(key.equalsIgnoreCase("ACNT_NO"))	{
		return  maskToString(value, 3, 35);
	} 
	return value;
}

 	/**
 	 * 가격정보표시
 	 * @param amt
 	 * @param amtDispNm
 	 * @return
 	 */
    public static String getPriceAMT(String amt, String amtDispNm){

    	if(amt.equals("0")) return amtDispNm;
    	else{
    		return getFormatPayment(amt);
    	}
    }
    
    public static String getFormatPayment( String paymentAmt ){
//	logger.info("paymentAmt:"+paymentAmt);
		DecimalFormat df = new DecimalFormat( "###,###" );
		double amt = Double.valueOf( paymentAmt );
		String dfResult = df.format( amt ) +"원";
		return dfResult;
	}
	
	public Map<String,List<String>> getParams(HttpServletRequest req){

		Map<String,String[]> params = req.getParameterMap();
		Map<String,List<String>> rtnMap = new HashMap<String,List<String>>();
 
		Iterator<String> it = params.keySet().iterator();
	 
		while(it.hasNext()){ 
			String key = it.next(); 
			String[] values = params.get(key); 
			rtnMap.put(key, Arrays.asList(values));
		}
		return rtnMap;
	}

	/**
	 * String.valueOf가 null 일때 "null" string 을 봔환해서 공백으로 반환하도록 
	 * @param object
	 * @return
	 */
	public static String stringValueOf(Object object) {
		return object == null ? "" : String.valueOf(object);
	}

	
	//================================================================================================================	
	//================================================================================================================	
	//		E:스트링	
	//================================================================================================================	
	//================================================================================================================		
	
	
	
	
	//================================================================================================================	
	//================================================================================================================	
	//		S:DATE	
	//================================================================================================================	
	//================================================================================================================		
	

	/**
	 * 형식에 맞는 오늘 날짜 가져오기
	 * @param format
	 * @return
	 */
	public static String getToday(String format) {
		SimpleDateFormat formtter = new SimpleDateFormat(format);
		Date date = new Date();
		
		return formtter.format(date);
	}

	//================================================================================================================	
	//================================================================================================================	
	//		E:DATE	
	//================================================================================================================	
	//================================================================================================================
	
	
}
