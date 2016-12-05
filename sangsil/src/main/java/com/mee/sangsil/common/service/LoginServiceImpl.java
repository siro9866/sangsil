package com.mee.sangsil.common.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mee.sangsil.dao.Dao;
import com.mee.sangsil.dto.UserDto;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private Dao dao;
	
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Override
	public int insert_history(String sql, UserDto userDto, HttpServletRequest request) {
		return dao.insert(sql, userDto);
	}

	@Override
	public UserDto login(String sql, UserDto userDto) {
		return (UserDto) dao.detail(sql, userDto);
	}

	@Override
	public int insert_user(String sql, UserDto userDto) {
		return dao.insert(sql, userDto);
	}

	@Override
	public int update(String sql, UserDto userDto) {
		return dao.update(sql, userDto);
	}
}
