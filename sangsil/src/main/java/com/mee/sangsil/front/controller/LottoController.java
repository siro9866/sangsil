package com.mee.sangsil.front.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mee.sangsil.front.service.LottoService;


@Controller
@RequestMapping(value="/front/lotto")
public class LottoController {

	private static final Logger logger = LoggerFactory.getLogger(LottoController.class);
	
	@Autowired
	private LottoService lottoService;
	
	
	/**
	 * 리스트
	 * @param favorityDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(@RequestParam Map<String, String> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/front/lotto");

		paramMap.put("pageSize", "20");
		paramMap.put("pageNum", "1");

		List<Map<String, String>> resultList = null;
		resultList = lottoService.list(paramMap);
		mav.addObject("paramMap", paramMap);
		mav.addObject("resultList", resultList);
		return mav;
	}
	
	@RequestMapping(value="/listDang")
	public ModelAndView listDang(@RequestParam Map<String, String> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/front/lottoDang");
		List<Map<String, String>> resultList = null;

		paramMap.put("pageSize", "20");
		paramMap.put("pageNum", "1");
		
		logger.info("lotto_dang_num1:"+paramMap.get("lotto_dang_num1"));
		logger.info("lotto_dang_num2:"+paramMap.get("lotto_dang_num2"));
		logger.info("lotto_dang_num3:"+paramMap.get("lotto_dang_num3"));
		logger.info("lotto_dang_num4:"+paramMap.get("lotto_dang_num4"));
		logger.info("lotto_dang_num5:"+paramMap.get("lotto_dang_num5"));
		logger.info("lotto_dang_num6:"+paramMap.get("lotto_dang_num6"));
		
		try {
			logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
			resultList = lottoService.listDang(paramMap);
			logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
			
		} catch (Exception e) {
			logger.info("Exception:" + e.toString());
		}
		
		mav.addObject("paramMap", paramMap);
		mav.addObject("resultList", resultList);
		return mav;
	}
}
