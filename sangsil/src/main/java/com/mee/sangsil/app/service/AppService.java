package com.mee.sangsil.app.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.mee.sangsil.dto.BoardDto;


public interface AppService {
	
	public List<BoardDto> list(String sql, BoardDto boardDto);
	public BoardDto detail(String sql, BoardDto boardDto);
	public JSONObject insert(String sql, BoardDto boardDto, HttpServletRequest request);
	public JSONObject update(String sql, BoardDto boardDto, HttpServletRequest request);
	public int delete(String sql, BoardDto boardDto, HttpServletRequest request);
	
	public String getString(String sql, HashMap<String, String> map);
}
