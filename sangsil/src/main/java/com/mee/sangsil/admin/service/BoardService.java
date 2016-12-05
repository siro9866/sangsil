package com.mee.sangsil.admin.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.mee.sangsil.dto.BoardDto;
import com.mee.sangsil.dto.FileDto;


public interface BoardService {
	
	public List<BoardDto> list(String sql, BoardDto boardDto);
	public BoardDto detail(String sql, BoardDto boardDto);
	public JSONObject insert(String sql, BoardDto boardDto, HttpServletRequest request);
	public JSONObject update(String sql, BoardDto boardDto, HttpServletRequest request);
	public int delete(String sql, BoardDto boardDto, HttpServletRequest request);
	
	public String getString(String sql, HashMap<String, String> map);
	public List<FileDto> listFile(String sql, BoardDto boardDto);
	
	public int updateFile(String sql, FileDto fileDto, HttpServletRequest request);
	public int deleteFile(String sql, FileDto fileDto, HttpServletRequest request);
}
