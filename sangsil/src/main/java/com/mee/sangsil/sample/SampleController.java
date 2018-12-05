package com.mee.sangsil.sample;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mee.sangsil.common.CommonFunction;
import com.mee.sangsil.common.CommonUtil;
import com.mee.sangsil.common.DateUtil;
import com.mee.sangsil.common.StringUtil;
import com.mee.sangsil.common.excel.ExcelRead;
import com.mee.sangsil.common.excel.ExcelReadOption;
import com.mee.sangsil.common.exception.CommonCheckedException;
import com.mee.sangsil.common.exception.CommonErrorCode;
import com.mee.sangsil.dto.BoardDto;
import com.mee.sangsil.dto.SampleDto;
import com.mee.sangsil.sample.service.SampleService;

@Controller
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private SampleService sampleService;

	@Value("#{message['LOGIN']}")
	String m_login;
	@Value("#{message['ID']}")
	String m_id;
	@Value("#{message['PASSWORD']}")
	String m_password;

	@RequestMapping(value = "/sample")
	public ModelAndView sample(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/sample/sample");
		return mav;
	}

	/**
	 * 유투브 바로보기
	 * 
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/youtube")
	public ModelAndView youtube(@ModelAttribute BoardDto boardDto, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/youtube");
		return mav;
	}

	/**
	 * 책효과
	 * 
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/bookEffect")
	public ModelAndView bookEffect(@ModelAttribute BoardDto boardDto, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/bookEffect");
		return mav;
	}

	/**
	 * 우편번호
	 * 
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/zipAddress")
	public ModelAndView zipAddress(@ModelAttribute BoardDto boardDto, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/zipAddress");
		return mav;
	}

	@RequestMapping(value = "/sample/bookEffectSample")
	public ModelAndView bookEffectSample(@ModelAttribute BoardDto boardDto, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/bookEffectSample");
		return mav;
	}

	@RequestMapping(value = "/sample/jqueryUI")
	public ModelAndView jqueryUI(@ModelAttribute BoardDto boardDto, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/jqueryUI");
		return mav;
	}

	/**
	 * HASH 맵으로 파람|리절트 모두 주고받기 아래 for 구문 처럼 데이터 가공할 수 있음
	 * 
	 * @param paramMap
	 * @param request
	 *            : 맵에 put 해서 전달
	 * @param response
	 *            : sql 결과 그대로 전달
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/paramMap")
	public ModelAndView jqueryUI(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/paramMap");

		paramMap.put("pageSize", "10");
		paramMap.put("pageNum", "1");

		logger.info(paramMap.get("searchValue"));
		logger.info(paramMap.get("param2"));
		logger.info(paramMap.get("param3"));
		logger.info(paramMap.get("param4"));

		List<Map<String, String>> resultList = null;
		resultList = sampleService.list("sample.list", paramMap);

		try {

			for (int i = 0; i < resultList.size(); i++) {
				logger.info(resultList.get(i).get("favority_nm"));
				logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
				logger.info("※※※※※※※※	" + i + "	※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
				logger.info("CommonFunction.isEmpty:" + CommonFunction.isEmpty(resultList.get(i).get("up_date")));
				logger.info("CommonFunction.isNotEmpty:" + CommonFunction.isNotEmpty(resultList.get(i).get("up_date")));
				logger.info("CommonFunction.isNull:" + CommonFunction.isNull(resultList.get(i).get("up_date")));
				logger.info("CommonFunction.isNotNull:" + CommonFunction.isNotNull(resultList.get(i).get("up_date")));
				logger.info("CommonFunction.default2String:"
						+ StringUtil.default2String(StringUtil.stringValueOf(resultList.get(i).get("up_date")),
								DateUtil.getToday("yyyy-MM-dd HH:mm:ss")));
				logger.info("CommonFunction.getPriceAMT:"
						+ StringUtil.getPriceAMT(StringUtil.stringValueOf(resultList.get(i).get("disp_order")), "빵원"));
				logger.info("CommonFunction.getToday:" + DateUtil.getToday("yyyy-MM-dd HH:mm:ss"));
				logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
				logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");

				resultList.get(i).put("favority_nm", resultList.get(i).get("favority_nm").replaceAll("사이트", "SITE"));
				// resultList.get(i).put("up_date",
				// CommonFunction.default2String(CommonFunction.stringValueOf(resultList.get(i).get("up_date")),
				// CommonFunction.getToday("yyyy-MM-dd HH:mm:ss")));
				resultList.get(i).put("disp_order1",
						StringUtil.getPriceAMT(StringUtil.stringValueOf(resultList.get(i).get("disp_order")), "빵원"));

			}

		} catch (Exception e) {
			logger.info("Exception:" + e.toString());
		}

		mav.addObject("paramMap", paramMap);
		mav.addObject("resultList", resultList);
		return mav;
	}

	/**
	 * 익셉션 발생 제어
	 * 
	 * @param paramMap
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws CommonCheckedException
	 */
	@RequestMapping(value = "/sample/newException")
	public ModelAndView newException(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/newException");
		JSONObject json = new JSONObject();
		Map<String, String> resulMap = new HashMap<String, String>();

		paramMap.put("pageSize", "10");
		paramMap.put("pageNum", "1");

		logger.info(paramMap.get("searchValue"));
		logger.info(paramMap.get("param2"));
		logger.info(paramMap.get("param3"));
		logger.info(paramMap.get("param4"));

		List<Map<String, String>> resultList = null;

		try {

			// 각종 익셉션 처리
			if (paramMap.get("loginId") == null || paramMap.get("loginId").trim().isEmpty()) {
				resulMap = setResultJson(CommonErrorCode.ERR_0001, "로그인-ID");
				throw new CommonCheckedException(CommonErrorCode.ERR_0001, m_login + "-" + m_id);
			} else if (paramMap.get("userPw") == null || paramMap.get("userPw").trim().isEmpty()) {
				resulMap = setResultJson(CommonErrorCode.ERR_0001, "로그인-PW");
				throw new CommonCheckedException(CommonErrorCode.ERR_0001, m_login + "-" + m_password);
			} else if (!paramMap.get("loginId").equals(paramMap.get("userPw"))) {
				resulMap = setResultJson(CommonErrorCode.ERR_0003, "로그인-PW");
				throw new CommonCheckedException(CommonErrorCode.ERR_0003, m_login + "-" + m_id);
			} else {
				resultList = sampleService.list("sample.list", paramMap);
			}

			for (int i = 0; i < resultList.size(); i++) {
				logger.info(resultList.get(i).get("favority_nm"));
				logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
				logger.info("※※※※※※※※	" + i + "	※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
				logger.info("CommonFunction.isEmpty:" + CommonFunction.isEmpty(resultList.get(i).get("up_date")));
				logger.info("CommonFunction.isNotEmpty:" + CommonFunction.isNotEmpty(resultList.get(i).get("up_date")));
				logger.info("CommonFunction.isNull:" + CommonFunction.isNull(resultList.get(i).get("up_date")));
				logger.info("CommonFunction.isNotNull:" + CommonFunction.isNotNull(resultList.get(i).get("up_date")));
				logger.info("CommonFunction.default2String:"
						+ StringUtil.default2String(StringUtil.stringValueOf(resultList.get(i).get("up_date")),
								DateUtil.getToday("yyyy-MM-dd HH:mm:ss")));
				logger.info("CommonFunction.getPriceAMT:"
						+ StringUtil.getPriceAMT(StringUtil.stringValueOf(resultList.get(i).get("disp_order")), "빵원"));
				logger.info("CommonFunction.getToday:" + DateUtil.getToday("yyyy-MM-dd HH:mm:ss"));
				logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
				logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");

				resultList.get(i).put("favority_nm", resultList.get(i).get("favority_nm").replaceAll("사이트", "SITE"));
				// resultList.get(i).put("up_date",
				// CommonFunction.default2String(CommonFunction.stringValueOf(resultList.get(i).get("up_date")),
				// CommonFunction.getToday("yyyy-MM-dd HH:mm:ss")));
				resultList.get(i).put("disp_order1",
						StringUtil.getPriceAMT(StringUtil.stringValueOf(resultList.get(i).get("disp_order")), "빵원"));

			}

		} catch (Exception e) {
			logger.info("\n\nException:" + e.toString() + "\n");
			// e.printStackTrace();
		}
		// resulMap = setResultJson(CommonErrorCode.ERR_0000, "");
		json.put("paramMap", paramMap);
		json.put("resultList", resultList);
		json.putAll(resulMap);

		mav.addObject("result", json);

		System.out.println("JSON1:" + json.toString());
		System.out.println("JSON2:" + json.toJSONString());

		return mav;
	}

	public Map<String, String> setResultJson(CommonErrorCode commonErrorCode, String title) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("rCode", commonErrorCode.getErrCode());
		map.put("rName", commonErrorCode.getErrName());
		map.put("rMessage", commonErrorCode.getMessage(title));

		return map;
	}

	// public ModelAndView newException(@RequestParam Map<String, String>
	// paramMap, HttpServletRequest request,
	// HttpServletResponse response, HttpSession session) throws
	// CommonCheckedException {
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("/sample/newException");
	//
	// if (paramMap.get("loginId") == null ||
	// paramMap.get("loginId").trim().isEmpty())
	// throw new CommonCheckedException(CommonErrorCode.ERR_0001, "로그인 ID");
	//
	// if (paramMap.get("userPw") == null ||
	// paramMap.get("userPw").trim().isEmpty())
	// throw new CommonCheckedException(CommonErrorCode.ERR_0003, "패스워드");
	//
	//
	// return mav;
	// }

	/**
	 * excelUpload 테스트
	 * 
	 * @param paramMap
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/excelUpload")
	public ModelAndView excelUpload(@RequestParam Map<String, String> paramMap, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/excelUpload");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/sample/excelUploadAjax", method = RequestMethod.POST)
	public ModelAndView excelUploadAjax(MultipartHttpServletRequest request) throws Exception {
		MultipartFile excelFile = request.getFile("excelFile");
		System.out.println("엑셀 파일 업로드 컨트롤러");
		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		File destFile = new File("D:\\" + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		excelReadOption.setFilePath(destFile.getAbsolutePath());
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F");
		excelReadOption.setStartRow(2);

		List<Map<String, String>> excelContent = ExcelRead.read(excelReadOption);

		for (Map<String, String> article : excelContent) {
			System.out.println(article.get("A"));
			System.out.println(article.get("B"));
			System.out.println(article.get("C"));
			System.out.println(article.get("D"));
			System.out.println(article.get("E"));
			System.out.println(article.get("F"));
		}

		// userService.excelUpload(destFile); //서비스 부분을 삭제한다.

		// FileUtils.forceDelete(destFile.getAbsolutePath());

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/sample/excelUploadView");
		mav.addObject("resultList", excelContent);
		return mav;
	}
	
	
	@RequestMapping(value="/sample/uploadSheetInsert")
	public ModelAndView uploadSheetInsert(@RequestBody List<SampleDto> sampleDtoList, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		JSONObject json = new JSONObject();
		mav.setViewName("jsonView");
		CommonUtil.getReturnCodeFail(json);
		
		try {
			logger.info("uploadSheetDtoList.size:"+sampleDtoList.size());
			
			if(sampleDtoList.size() > 0) {
				
//				for(UploadSheetDto uploadSheetDto : uploadSheetDtoList) {
//					logger.info(uploadSheetDto.getSunseo());
//					//사용자정보
//					CommonUtil.setInUserInfo(request, uploadSheetDto);
//					uploadSheetDto.setOrder_group(getOrderGroup);
//					uploadSheetService.uploadSheetInsert(uploadSheetDto, request);
//				}
				
//				uploadSheetService.uploadSheetInserts(uploadSheetDtoList, request);
				
				//저장 성공시 코드값 세팅
				CommonUtil.getReturnCodeSuc(json);
				//저장후 페이지 이동
				json.put("goUrl", "/ordersheet/ordersheetList.do");
			}else {
				CommonUtil.getReturnCodeFail(json, "저장할 데이타가 없습니다.");
			}
		} catch(RuntimeException re) {
			logger.info("RuntimeException insert E:" + re.toString());
			CommonUtil.getReturnCodeFail(json, re.toString());
		} catch (Exception e) {
			//저장 실패시 코드값 세팅
			logger.info("EXCEPTION insert E:" + e.toString());
			CommonUtil.getReturnCodeFail(json, e.toString());
		}
		//결과값 전송
		logger.info(json.toString());
		mav.addObject("resultJson", json);
		return mav;
		
	}
	
}
