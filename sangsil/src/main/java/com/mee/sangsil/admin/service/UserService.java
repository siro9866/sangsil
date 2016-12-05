package com.mee.sangsil.admin.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.mee.sangsil.dto.UserDto;


public interface UserService {
	
	public List<UserDto> list(String sql, UserDto userDto);
	public UserDto detail(String sql, UserDto userDto);
	public JSONObject insert(String sql, UserDto userDto, HttpServletRequest request);
	public JSONObject update(String sql, UserDto userDto, HttpServletRequest request);
	public int delete(String sql, UserDto userDto);
	
	public String getString(String sql, HashMap<String, String> map);
}
