package com.mee.sangsil.common;


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonParserUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonParserUtil.class);
	
	@SuppressWarnings("rawtypes")
	public static Map getJsonParser(String jsonStr) {
		Map map = Collections.EMPTY_MAP;
		
		JSONParser parser = new JSONParser();
		ContainerFactory cf = new ContainerFactory() {
			@Override
			public Map createObjectContainer() {
				return new LinkedHashMap();
			}
			@Override
			public List creatArrayContainer() {
				return new LinkedList();
			}
		};
		
		try {
			map = (Map)parser.parse(jsonStr, cf);

		} catch (ParseException e) {
			logger.debug("JsonParserUtil.getJsonParser ParseException:"+e);
		}
		return map;
	}
	
	
	/**
	 * jsonobject 형태로 값추출 
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject getJsonObject(String jsonStr) {
		JSONObject jsonObj = new JSONObject();
		try {
			// JSON Parser 생성
			JSONParser parser = new JSONParser();

			// 넘어온 문자열을 JSON 객체로 변환
			jsonObj = (JSONObject)parser.parse(jsonStr);
		} catch (ParseException e) {
			logger.debug("JsonParserUtil.getJSONObject Exception:"+e);
		}
		return jsonObj;
	}

	
}
