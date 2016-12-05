package com.mee.sangsil.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.mee.sangsil.dto.SampleDto;


public interface SampleService {
	
	public List<Map<String, String>> list(String sql, Map<String, String> paramMap);
	public SampleDto detail(String sql, Map<String, String> paramMap);
	public JSONObject insert(String sql, Map<String, String> paramMap, HttpServletRequest request);
	public JSONObject update(String sql, Map<String, String> paramMap, HttpServletRequest request);
	public int delete(String sql, Map<String, String> paramMap, HttpServletRequest request);
	
	public String getString(String sql, HashMap<String, String> map);
}
