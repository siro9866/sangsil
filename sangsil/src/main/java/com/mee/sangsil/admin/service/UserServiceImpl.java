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
import com.mee.sangsil.dto.UserDto;

@Service("userService")
public class UserServiceImpl implements UserService{

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Override
	public List<UserDto> list(String sql, UserDto userDto) {
		List<UserDto> result = dao.list(sql, userDto);
		return result;
	}
	
	@Override
	public UserDto detail(String sql, UserDto userDto) {
		UserDto result = (UserDto) dao.detail(sql, userDto);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject insert(String sql, UserDto userDto, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		int saveResult = 0;
		
		saveResult = dao.insert(sql, userDto);
		//화면단에 넘겨줌
		json.put("saveResult", saveResult);
		
		return json;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject update(String sql, UserDto userDto, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		int saveResult = 0;

		saveResult = dao.update(sql, userDto);
		//화면단에 넘겨줌
		json.put("saveResult", saveResult);
		return json;
	}

	@Override
	public int delete(String sql, UserDto userDto) {
		int result = dao.delete(sql, userDto);
		return result;
	}

	@Override
	public String getString(String sql, HashMap<String, String> map) {
		return dao.getString(sql, map);
	}
}
