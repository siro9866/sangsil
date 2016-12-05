package com.mee.sangsil.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.mee.sangsil.dto.CdDto;


public interface CdService {
	
	public List<CdDto> list(String sql, CdDto cdDto);
	public CdDto detail(String sql, CdDto cdDto);
	public JSONObject insert(String sql, CdDto cdDto, HttpServletRequest request);
	public JSONObject update(String sql, CdDto cdDto, HttpServletRequest request);
	public int delete(String sql, CdDto cdDto);
}
