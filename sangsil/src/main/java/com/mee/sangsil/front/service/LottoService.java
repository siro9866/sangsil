package com.mee.sangsil.front.service;

import java.util.List;
import java.util.Map;


public interface LottoService {
	
	/**
	 * 당첨기록 전체
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, String>> list(Map<String, String> paramMap);
	public List<Map<String, String>> listDang(Map<String, String> paramMap);
	
}
