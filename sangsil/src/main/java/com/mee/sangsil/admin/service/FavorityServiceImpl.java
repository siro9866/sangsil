package com.mee.sangsil.admin.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mee.sangsil.dao.Dao;
import com.mee.sangsil.dto.FavorityDto;

@Service("favorityService")
public class FavorityServiceImpl implements FavorityService{

	private static final Logger logger = LoggerFactory.getLogger(FavorityServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Override
	public List<FavorityDto> list(String sql, FavorityDto favorityDto) {
		List<FavorityDto> result = dao.list(sql, favorityDto);
		return result;
	}
	
	@Override
	public FavorityDto detail(String sql, FavorityDto favorityDto) {
		FavorityDto result = (FavorityDto) dao.detail(sql, favorityDto);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject insert(String sql, FavorityDto favorityDto, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		int saveResult = 0;
		
		saveResult = dao.insert(sql, favorityDto);
		//화면단에 넘겨줌
		json.put("saveResult", saveResult);
		
		return json;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject update(String sql, FavorityDto favorityDto, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		int saveResult = 0;

		saveResult = dao.update(sql, favorityDto);
		//화면단에 넘겨줌
		json.put("saveResult", saveResult);
		return json;
	}

	@Override
	public int delete(String sql, FavorityDto favorityDto) {
		int result = dao.delete(sql, favorityDto);
		return result;
	}

	@Override
	public String getString(String sql, HashMap<String, String> map) {
		return dao.getString(sql, map);
	}
}
