package com.mee.sangsil.front.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mee.sangsil.dao.Dao;

@Service("lottoService")
public class LottoServiceImpl implements LottoService{

	private static final Logger logger = LoggerFactory.getLogger(LottoServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Override
	public List<Map<String, String>> list(Map<String, String> paramMap) {
		List<Map<String, String>> result = dao.list("front.lotto.list", paramMap);
		return result;
	}
	
	@Override
	public List<Map<String, String>> listDang(Map<String, String> paramMap) {
		List<Map<String, String>> result = dao.list("front.lotto.listDang", paramMap);
		return result;
	}

	@Override
	public Map<String, String> detail(Map<String, String> paramMap) {
		Map<String, String> result = (Map<String, String>) dao.detail("front.lotto.detail", paramMap);
		return result;
	}

	@Override
	public JSONObject insert(Map<String, String> paramMap) {
		JSONObject json = new JSONObject();
		json =  (JSONObject) dao.detail("front.lotto.insert", paramMap);
		return json;
	}

	@Override
	public List<Map<String, String>> listDangNum(Map<String, String> paramMap) {
		List<Map<String, String>> result = dao.list("front.lotto.listDangNum", paramMap);
		return result;
	}
	
}
