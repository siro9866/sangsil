package com.mee.sangsil.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


@Repository("dao")
public class DaoImpl implements Dao {

	@Resource(name="sqlSession")
	private SqlSession sqlSession;

	@Override
	public List list(String sqlId) {
		return sqlSession.selectList(sqlId);
	}

	@Override
	public List list(String sqlId, Object param) {
		return sqlSession.selectList(sqlId, param);
	}

	@Override
	public List list(String sqlId, HashMap param) {
		return sqlSession.selectList(sqlId, param);
	}

	@Override
	public int listCount(String sqlId) {
		return sqlSession.selectOne(sqlId);
	}

	@Override
	public int listCount(String sqlId, Object param) {
		return sqlSession.selectOne(sqlId, param);
	}

	@Override
	public int listCount(String sqlId, HashMap param) {
		return sqlSession.selectOne(sqlId, param);
	}

	@Override
	public Object detail(String sqlId) {
		return sqlSession.selectOne(sqlId);
	}

	@Override
	public Object detail(String sqlId, Object param) {
		return sqlSession.selectOne(sqlId, param);
	}

	@Override
	public Object detail(String sqlId, HashMap param) {
		return sqlSession.selectOne(sqlId, param);
	}

	@Override
	public int getInt(String sqlId) {
		return sqlSession.selectOne(sqlId);
	}

	@Override
	public int getInt(String sqlId, Object param) {
		return sqlSession.selectOne(sqlId, param);
	}

	@Override
	public int getInt(String sqlId, HashMap param) {
		return sqlSession.selectOne(sqlId, param);
	}

	@Override
	public String getString(String sqlId) {
		return sqlSession.selectOne(sqlId);
	}

	@Override
	public String getString(String sqlId, Object param) {
		return sqlSession.selectOne(sqlId, param);
	}

	@Override
	public String getString(String sqlId, HashMap param) {
		return sqlSession.selectOne(sqlId, param);
	}

	@Override
	public int insert(String sqlId) {
		return sqlSession.insert(sqlId);
	}

	@Override
	public int insert(String sqlId, Object param) {
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
		System.out.println("sqlId:sqlId"+sqlId);
		System.out.println("param"+param);
		return sqlSession.insert(sqlId, param);
	}

	@Override
	public int insert(String sqlId, HashMap param) {
		return sqlSession.insert(sqlId, param);
	}

	@Override
	public int update(String sqlId, Object param) {
		return sqlSession.update(sqlId, param);
	}

	@Override
	public int update(String sqlId, HashMap param) {
		return sqlSession.update(sqlId, param);
	}

	@Override
	public int delete(String sqlId, Object param) {
		return sqlSession.delete(sqlId, param);
	}

	@Override
	public int delete(String sqlId, HashMap param) {
		return sqlSession.delete(sqlId, param);
	}

	@Override
	public Object callProcedure(String sqlId, Object param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
