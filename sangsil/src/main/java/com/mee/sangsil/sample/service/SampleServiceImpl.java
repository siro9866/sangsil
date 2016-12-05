package com.mee.sangsil.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mee.sangsil.common.FilesUtil;
import com.mee.sangsil.dao.Dao;
import com.mee.sangsil.dto.SampleDto;
import com.mee.sangsil.dto.FileDto;

@Service("sampleService")
public class SampleServiceImpl implements SampleService{

	private static final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);
	
	@Autowired
	private Dao dao;

	@Override
	public List<Map<String, String>> list(String sql, Map<String, String> paramMap) {
		List<Map<String, String>> result = dao.list(sql, paramMap);
		return result;
	}
	
	@Override
	public SampleDto detail(String sql, Map<String, String> paramMap) {
		SampleDto result = (SampleDto) dao.detail(sql, paramMap);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject insert(String sql, Map<String, String> paramMap, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		return json;
	}

	@Override
	public JSONObject update(String sql, Map<String, String> paramMap, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		return json;
	}

	@Override
	public int delete(String sql, Map<String, String> paramMap, HttpServletRequest request) {
		FileDto delfileDto = new FileDto();
		int result = dao.delete(sql, paramMap);
		return result;
	}

	@Override
	public String getString(String sql, HashMap<String, String> paramMap) {
		return dao.getString(sql, paramMap);
	}
}
