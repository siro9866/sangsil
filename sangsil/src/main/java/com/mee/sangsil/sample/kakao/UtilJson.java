package com.mee.sangsil.sample.kakao;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mee.sangsil.common.CommonUtil;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


public class UtilJson {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilJson.class);
	
	
	
	/**
	 * JSON -> MAP 으로
	 * @param json
	 * @return
	 */
	public static Map<String, Object> changeJsonToMap(JSONObject json){
		ObjectMapper mapper = new ObjectMapper();
		MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
		
		Map<String, Object> data = null;
		try {
			data = mapper.readValue(StringEscapeUtils.unescapeJava(json.toString()), type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * OBJ -> JSON 으로
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONObject changeObjToJson( Object object ) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = mapper.convertValue(object, Map.class);
		JSONObject json = new JSONObject();
		for (Object obj : result.entrySet()) {
			Entry ety = (Entry) obj;
			String keyName = ((String) ety.getKey());
			
			if (ety.getValue() != null ) {
				if (ety.getValue() instanceof List<?>) {
					 if(((List<?>) ety.getValue()).size() > 0){
							if (((List) ety.getValue()).get(0) instanceof Map) {
								List<Map<String, String>> mapList = (List<Map<String, String>>) ety.getValue();
								json.put(keyName, mapList);
							}else{
								List<String>  list =(List<String>) ety.getValue();
								json.put(keyName, list);
							}
					 }else{
							String str = "";
							json.put(keyName, str);
					 }
				} else if(ety.getValue() instanceof Map<?,?>) {
					Map<String, Object> map = (Map<String, Object>) ety.getValue();
					json.put(keyName, map);
				}else{
					Object str = CommonUtil.isEmpty(ety.getValue()) ? null : ety.getValue();
					json.put(keyName, str);
				}
			}
		}
		return json;
	}
	
	/**
	 * String -> JSON 으로
	 * @param str
	 * @return
	 */
	public static JSONObject changeStringToJson(String str ) {
		JSONObject json = (JSONObject) JSONSerializer.toJSON(str);
		return json;
	}
	
}
