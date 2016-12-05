package com.mee.sangsil.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mee.sangsil.admin.service.CdService;
import com.mee.sangsil.admin.service.FavorityService;
import com.mee.sangsil.common.CommonUtil;
import com.mee.sangsil.common.PagingView;
import com.mee.sangsil.dto.CdDto;
import com.mee.sangsil.dto.FavorityDto;


@Controller
@RequestMapping(value="/admin/favority")
public class FavorityController {

	private static final Logger logger = LoggerFactory.getLogger(FavorityController.class);
	
	@Autowired
	private FavorityService favorityService;
	@Autowired
	private CdService cdService;
	
	@Value("#{config['CD_ID_CAA']}") String cat_cd_id;
	
	/**
	 * 리스트
	 * @param favorityDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(@ModelAttribute FavorityDto favorityDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/favority/favority_list");
		
		List<FavorityDto> resultList = null;
		resultList = favorityService.list("admin.favority.list", favorityDto);
		
		if(resultList.size() > 0){
			//paging		
			FavorityDto pg = resultList.get(0);
			PagingView pv = new PagingView(pg.getPageNum(), favorityDto.getPageSize(), favorityDto.getBlockSize(), pg.getTotCnt());
			mav.addObject("paging", pv.print());
			//paging		
		}
		
		mav.addObject("paramDto", favorityDto);
		mav.addObject("resultList", resultList);
		
		logger.info("properties:");
		
		return mav;
	}
	
	/**
	 * 상세
	 * @param favorityDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(@ModelAttribute FavorityDto favorityDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/favority/favority_detail");
		
		FavorityDto result = null;
		List<FavorityDto> resultList = null;
		
		try {
			//게시글정보
			result = favorityService.detail("admin.favority.detail", favorityDto);
			//이전 다음글
			resultList  = favorityService.list("admin.favority.detailList", favorityDto);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mav.addObject("paramDto", favorityDto);
		mav.addObject("result", result);
		mav.addObject("resultList", resultList);
		
		return mav;
	}
	
	/**
	 * 인서트 업데이트 폼
	 * @param favorityDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/form")
	public ModelAndView iuForm(@ModelAttribute FavorityDto favorityDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/favority/favority_form");
		
		FavorityDto result = favorityService.detail("admin.favority.detail", favorityDto);
		
		//카테고리 리스트
		CdDto cdDto = new CdDto();
		cdDto.setCd_id(cat_cd_id);
		List<CdDto> cdList = cdService.list("admin.cd.detailList", cdDto);
		
		mav.addObject("paramDto", favorityDto);
		mav.addObject("result", result);
		mav.addObject("cdList", cdList);
		return mav;
	}	
	
	/**
	 * 인서트
	 * @param favorityDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/insert")
	@ResponseBody
	public JSONObject insert(@ModelAttribute FavorityDto favorityDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		try {
			//사용자정보
			CommonUtil.setInUserInfo(request, favorityDto);		
			CommonUtil.setUpUserInfo(request, favorityDto);		
			json = favorityService.insert("admin.favority.insert", favorityDto, request);
			//저장 성공시 코드값 세팅
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			//저장 실패시 코드값 세팅
			CommonUtil.getReturnCodeFail(json);
		}
		//결과값 전송
		logger.info(json.toJSONString());
		
		return json;
		
	}
	
	/**
	 * 업데이트
	 * @param favorityDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public JSONObject update(@ModelAttribute FavorityDto favorityDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//사용자정보
			CommonUtil.setUpUserInfo(request, favorityDto);
			json = favorityService.update("admin.favority.update", favorityDto, request);
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
		}
		//결과값 전송
		logger.info(json.toJSONString());
		
		return json;
		
	}
	
	/**
	 * 삭제
	 * @param favorityDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JSONObject delete(@ModelAttribute FavorityDto favorityDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//사용자정보
			CommonUtil.setUpUserInfo(request, favorityDto);			
			favorityService.delete("admin.favority.delete", favorityDto);
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
		}
		
		logger.info(json.toJSONString());
		return json;
		
	}
	
}
