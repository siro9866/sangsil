package com.mee.sangsil.common;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonFunction {

	private static final Logger logger = LoggerFactory.getLogger(CommonFunction.class);

	
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
	
}
