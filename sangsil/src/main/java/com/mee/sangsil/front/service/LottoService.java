package com.mee.sangsil.front.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;


public interface LottoService {
	
	/**
	 * 당첨기록 전체
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, String>> list(Map<String, String> paramMap);
	public List<Map<String, String>> listDang(Map<String, String> paramMap);
	public Map<String, String> detail(Map<String, String> paramMap);
	public JSONObject insert(Map<String, String> paramMap);
}
