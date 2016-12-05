package com.mee.sangsil.admin.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.mee.sangsil.dto.FavorityDto;


public interface FavorityService {
	
	public List<FavorityDto> list(String sql, FavorityDto favorityDto);
	public FavorityDto detail(String sql, FavorityDto favorityDto);
	public JSONObject insert(String sql, FavorityDto favorityDto, HttpServletRequest request);
	public JSONObject update(String sql, FavorityDto favorityDto, HttpServletRequest request);
	public int delete(String sql, FavorityDto favorityDto);
	
	public String getString(String sql, HashMap<String, String> map);
}
