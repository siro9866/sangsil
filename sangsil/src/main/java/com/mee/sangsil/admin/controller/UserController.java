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
import com.mee.sangsil.admin.service.UserService;
import com.mee.sangsil.common.CommonUtil;
import com.mee.sangsil.common.PagingView;
import com.mee.sangsil.dto.CdDto;
import com.mee.sangsil.dto.UserDto;


@Controller
@RequestMapping(value="/admin/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private CdService cdService;
	
	@Value("#{config['CD_ID_AAA']}") String auth_cd_id;	//권한
	
	/**
	 * 리스트
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/user/user_list");
		
		List<UserDto> resultList = null;
		resultList = userService.list("admin.user.list", userDto);
		
		if(resultList.size() > 0){
			//paging		
			UserDto pg = resultList.get(0);
			PagingView pv = new PagingView(pg.getPageNum(), userDto.getPageSize(), userDto.getBlockSize(), pg.getTotCnt());
			mav.addObject("paging", pv.print());
			//paging		
		}
		
		mav.addObject("paramDto", userDto);
		mav.addObject("resultList", resultList);
		
		logger.info("properties:");
		
		return mav;
	}
	
	/**
	 * 상세
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/user/user_detail");
		
		UserDto result = null;
		List<UserDto> resultList = null;
		try {
			result = userService.detail("admin.user.detail", userDto);
			//이전 다음글
			resultList  = userService.list("admin.user.detailList", userDto);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mav.addObject("paramDto", userDto);
		mav.addObject("result", result);
		mav.addObject("resultList", resultList);
		
		return mav;
	}
	
	/**
	 * 인서트 업데이트 폼
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/form")
	public ModelAndView iuForm(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/user/user_form");
		
		UserDto result = userService.detail("admin.user.detail", userDto);
		
		//권한 리스트
		CdDto cdDto = new CdDto();
		cdDto.setCd_id(auth_cd_id);
		List<CdDto> cdList = cdService.list("admin.cd.detailList", cdDto);
		
		mav.addObject("paramDto", userDto);
		mav.addObject("result", result);
		mav.addObject("cdList", cdList);
		return mav;
	}	
	
	/**
	 * 인서트
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/insert")
	@ResponseBody
	public JSONObject insert(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		try {
			//사용자정보
			CommonUtil.setInUserInfo(request, userDto);		
			CommonUtil.setUpUserInfo(request, userDto);		
			json = userService.insert("admin.user.insert", userDto, request);
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
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public JSONObject update(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//사용자정보
			CommonUtil.setUpUserInfo(request, userDto);
			json = userService.update("admin.user.update", userDto, request);
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
	 * @param userDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JSONObject delete(@ModelAttribute UserDto userDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//사용자정보
			CommonUtil.setUpUserInfo(request, userDto);			
			userService.delete("admin.user.delete", userDto);
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
