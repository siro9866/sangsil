package com.mee.sangsil.common.service;

import javax.servlet.http.HttpServletRequest;

import com.mee.sangsil.dto.UserDto;

public interface LoginService {
	
	/**
	 * 로그인 히스토리 저장
	 * @param sql
	 * @param userDto
	 * @param request
	 * @return
	 */
	public int insert_history(String sql, UserDto userDto, HttpServletRequest request);
	
	/**
	 * 로그인 회원 확인
	 * @param sql
	 * @param userDto
	 * @return
	 */
	public UserDto login(String sql, UserDto userDto);
	
	/**
	 * 회원정보 저장
	 * @param sql
	 * @param userDto
	 * @param request
	 * @return
	 */
	public int insert_user(String sql, UserDto userDto);
	
	/**
	 * 회원의 로그인 정보 업데이트
	 * @param sql
	 * @param userDto
	 * @param request
	 * @return
	 */
	public int update(String sql, UserDto userDto);
	
}
