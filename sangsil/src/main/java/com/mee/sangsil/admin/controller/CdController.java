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
import com.mee.sangsil.common.CommonUtil;
import com.mee.sangsil.common.PagingView;
import com.mee.sangsil.dto.CdDto;


@Controller
@RequestMapping(value="/admin/cd")
public class CdController {

	private static final Logger logger = LoggerFactory.getLogger(CdController.class);
	
	@Autowired
	private CdService cdService;
	
	//회원권한
	@Value("#{config['CD_ID_AAA']}") String cd_type;
	
	/**
	 * 리스트
	 * @param cdDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/cd/cd_list");
		
		List<CdDto> resultList = null;
		resultList = cdService.list("admin.cd.list", cdDto);
		
		if(resultList.size() > 0){
			//paging		
			CdDto pg = resultList.get(0);
			PagingView pv = new PagingView(pg.getPageNum(), cdDto.getPageSize(), cdDto.getBlockSize(), pg.getTotCnt());
			mav.addObject("paging", pv.print());
			//paging		
		}
		
		mav.addObject("paramDto", cdDto);
		mav.addObject("resultList", resultList);
		
		logger.info("properties:");
		
		return mav;
	}
	
	/**
	 * 상세
	 * @param cdDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/cd/cd_detail");
		
		CdDto result = null;
		//하위코드리스트
		List<CdDto> resultList = null;
		
		try {
			//코드정보
			result = cdService.detail("admin.cd.detail", cdDto);
			//하위코드리스트
			resultList  = cdService.list("admin.cd.detailSubList", cdDto);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mav.addObject("paramDto", cdDto);
		mav.addObject("result", result);
		mav.addObject("resultList", resultList);
		
		return mav;
	}
	
	/**
	 * 인서트 업데이트 폼
	 * @param cdDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/form")
	public ModelAndView iuForm(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/cd/cd_form");
		
		CdDto result = null;
		//권한코드리스트
		List<CdDto> resultListAuth = null;
		CdDto cdDtoAuth = new CdDto();
		cdDtoAuth.setCd_id(cd_type);
		
		result = cdService.detail("admin.cd.detail", cdDto);
		//권한코드리스트
		resultListAuth  = cdService.list("admin.cd.detailList", cdDtoAuth);
		
		mav.addObject("paramDto", cdDto);
		mav.addObject("result", result);
		mav.addObject("resultListAuth",resultListAuth);
		return mav;
	}	
	
	/**
	 * 인서트
	 * @param cdDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/insert")
	@ResponseBody
	public JSONObject insert(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		try {
			CommonUtil.getReturnCodeFail(json);
			//사용자정보
			CommonUtil.setInUserInfo(request, cdDto);		
			CommonUtil.setUpUserInfo(request, cdDto);		
			json = cdService.insert("admin.cd.insert", cdDto, request);
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
	 * @param cdDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public JSONObject update(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			CommonUtil.getReturnCodeFail(json);
			//사용자정보
			CommonUtil.setUpUserInfo(request, cdDto);
			json = cdService.update("admin.cd.update", cdDto, request);
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
	 * @param cdDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JSONObject delete(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			CommonUtil.getReturnCodeFail(json);
			//사용자정보
			CommonUtil.setUpUserInfo(request, cdDto);	
			//상위코드 삭제
			cdService.delete("admin.cd.delete", cdDto);
			//하위코드 삭제
			cdDto.setHigh_cd_id(cdDto.getCd_id());
			cdService.delete("admin.cd.deleteSubList", cdDto);
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
		}
		
		logger.info(json.toJSONString());
		return json;
		
	}
	
	/**
	 * 코드 중복 확인
	 * @param cdDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/cdDupleChk")
	@ResponseBody
	public JSONObject cdDupleChk(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		CdDto result = new CdDto();
		
		
		try {
			CommonUtil.getReturnCodeFail(json);
			//사용자정보
			result = cdService.detail("admin.cd.cdDupleChk", cdDto);
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
		}
		
		json.put("result", result);
		logger.info(json.toJSONString());
		return json;
		
	}
	
	/**
	 * 하위코드 및 등록
	 * @param cdDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/formSub")
	public ModelAndView iuFormSub(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/cd/cd_formSub");
		
		CdDto result = null;
		
		//하위코드리스트
		List<CdDto> resultList = null;
		
		//권한코드리스트
		List<CdDto> resultListAuth = null;
		CdDto cdDtoAuth = new CdDto();
		cdDtoAuth.setCd_id(cd_type);
		
		result = cdService.detail("admin.cd.detail", cdDto);
		
		//하위코드리스트
		resultList  = cdService.list("admin.cd.detailSubList", cdDto);
		
		//권한코드리스트
		resultListAuth  = cdService.list("admin.cd.detailList", cdDtoAuth);
		
		mav.addObject("paramDto", cdDto);
		mav.addObject("result", result);
		mav.addObject("resultList",resultList);
		mav.addObject("resultListAuth",resultListAuth);
		return mav;
	}	
	
	@RequestMapping(value="/saveSub")
	@ResponseBody
	public JSONObject saveSub(@ModelAttribute CdDto cdDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		try {
			CommonUtil.getReturnCodeFail(json);
			//사용자정보
			CommonUtil.setInUserInfo(request, cdDto);		
			CommonUtil.setUpUserInfo(request, cdDto);		
			
			
			logger.info("S:################# PARAM CHJECK ###########################");
			logger.info("	frm_auto_id:"+cdDto.getFrm_auto_id());
			logger.info("	getFrm_cd_id:"+cdDto.getFrm_cd_id());
			logger.info("	getFrm_cd_nm:"+cdDto.getFrm_cd_nm());
			logger.info("	getFrm_cd_cmt:"+cdDto.getFrm_cd_cmt());
			logger.info("	getFrm_cd_type:"+cdDto.getFrm_cd_type());
			logger.info("	getFrm_disp_order:"+cdDto.getFrm_disp_order());
			logger.info("	getFrm_use_yn:"+cdDto.getFrm_use_yn());
			logger.info("	getFrm_save_state:"+cdDto.getFrm_save_state());
			logger.info("E:################# PARAM CHJECK ###########################");
			
			String[] auto_id = cdDto.getFrm_auto_id().split(",");
			String[] cd_id = cdDto.getFrm_cd_id().split(",");
			String[] cd_nm = cdDto.getFrm_cd_nm().split(",");
			String[] cd_cmt = cdDto.getFrm_cd_cmt().split(",");
			String[] cd_type = cdDto.getFrm_cd_type().split(",");
			String[] disp_order = cdDto.getFrm_disp_order().split(",");
			String[] use_yn = cdDto.getFrm_use_yn().split(",");
			String[] save_state = cdDto.getFrm_save_state().split(",");
			
			
			CdDto paramCdDto = new CdDto();
			paramCdDto.setHigh_cd_id(cdDto.getHigh_cd_id());
			logger.info("cd_cmt.length:"+cd_cmt.length);
			
			for(int i=0; i < cd_id.length; i++){
				
				paramCdDto.setAuto_id(auto_id[i].trim());
				paramCdDto.setCd_id(cd_id[i].trim());
				paramCdDto.setCd_type(cd_type[i].trim());
				paramCdDto.setUse_yn(use_yn[i]);
				if(cd_nm.length > i){
					paramCdDto.setCd_nm(cd_nm[i].trim());
				}else{
					paramCdDto.setCd_nm("");
				}
				if(cd_cmt.length > i){
					paramCdDto.setCd_cmt(cd_cmt[i].trim());
				}else{
					paramCdDto.setCd_cmt("");
				}
				if(disp_order.length > i){
					paramCdDto.setDisp_order(disp_order[i].trim());
				}else{
					paramCdDto.setDisp_order("1");
				}
				
				if(save_state[i].equals("U")){
					json = cdService.update("admin.cd.update", paramCdDto, request);
				}else if(save_state[i].equals("I")){
					json = cdService.insert("admin.cd.insert", paramCdDto, request);
				}else{
					continue;
				}
				
			}
			
			//저장 성공시 코드값 세팅
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			//저장 실패시 코드값 세팅
			logger.info("EXCEPTION saveSub:"+e);
			CommonUtil.getReturnCodeFail(json);
		}
		//결과값 전송
		logger.info(json.toJSONString());
		
		return json;
		
	}	
	
}
