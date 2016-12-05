package com.mee.sangsil.dao;

import java.util.HashMap;
import java.util.List;

public interface Dao {
	/**
	 * 데이터 목록 조회
	 * @param id : SQLMap Query id 
	 * @return List
	 * @version 1.0
	 */
	public List list(String sqlId);
	public List list(String sqlId, Object param);
	public List list(String sqlId, HashMap param);
	
	
	/**
	 * Row Count 조회
	 * @param id : SQLMap Query id
	 * @return int
	 * @version 1.0
	 */
	public int listCount(String sqlId);
	public int listCount(String sqlId, Object param);
	public int listCount(String sqlId, HashMap param);
	
	/**
	 * 상세조회
	 * @param id
	 * @return
	 */
	public Object detail(String sqlId);
	public Object detail(String sqlId, Object param);
	public Object detail(String sqlId, HashMap param);
	
	/**
	 * 데이터 한개 구하기
	 * @param id
	 * @return
	 */
	public int getInt(String sqlId);
	public int getInt(String sqlId, Object param);
	public int getInt(String sqlId, HashMap param);
	public String getString(String sqlId);
	public String getString(String sqlId, Object param);
	public String getString(String sqlId, HashMap param);
	
	
	/**
	 * Insert 쿼리 실행
	 * @param id : SQLMap Query id
	 * @return int
	 * @version 1.0
	 */
	public int insert(String sqlId);
	public int insert(String sqlId, Object param);
	public int insert(String sqlId, HashMap param);
	
	/**
	 * Update 쿼리 실행
	 * @param id : SQLMap Query id
	 * @param param : Object Parameter
	 * @return int
	 * @version 1.0
	 */
	public int update(String sqlId, Object param);
	public int update(String sqlId, HashMap param);
	
	/**
	 * Delete 쿼리 실행
	 * @param id : SQLMap Query id
	 * @param param : Object Parameter
	 * @return int
	 * @version 1.0
	 */
	public int delete(String sqlId, Object param);
	public int delete(String sqlId, HashMap param);
	
	/**
	 * Procedure 실행
	 * @param id
	 * @param param
	 * @return
	 */
	public Object callProcedure(String sqlId, Object param);
	
	/**
	 * 데용량 엑셀 다운로드 쿼리 실행
	 * @param id
	 * @param param
	 * @param handler
	 */
//	public void listRowHandler(String sqlId, Object param, RowHandler handler);

}
