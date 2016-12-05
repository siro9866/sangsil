package com.mee.sangsil.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mee.sangsil.dao.Dao;
import com.mee.sangsil.dto.CdDto;

@Service("cdService")
public class CdServiceImpl implements CdService{

	private static final Logger logger = LoggerFactory.getLogger(CdServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Override
	public List<CdDto> list(String sql, CdDto cdDto) {
		List<CdDto> result = dao.list(sql, cdDto);
		return result;
	}
	
	@Override
	public CdDto detail(String sql, CdDto cdDto) {
		CdDto result = (CdDto) dao.detail(sql, cdDto);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject insert(String sql, CdDto cdDto, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		int saveResult = 0;
		
		saveResult = dao.insert(sql, cdDto);
		//화면단에 넘겨줌
		json.put("saveResult", saveResult);
		
		return json;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject update(String sql, CdDto cdDto, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		int saveResult = 0;

		saveResult = dao.update(sql, cdDto);
		//화면단에 넘겨줌
		json.put("saveResult", saveResult);
		return json;
	}

	@Override
	public int delete(String sql, CdDto cdDto) {
		int result = dao.delete(sql, cdDto);
		return result;
	}
}
