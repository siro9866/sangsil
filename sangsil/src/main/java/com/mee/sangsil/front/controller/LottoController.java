package com.mee.sangsil.front.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mee.sangsil.common.CommonFunction;
import com.mee.sangsil.common.JsonParserUtil;
import com.mee.sangsil.common.PagingView;
import com.mee.sangsil.common.exception.CommonCheckedException;
import com.mee.sangsil.common.exception.CommonErrorCode;
import com.mee.sangsil.front.service.LottoService;


@Controller
@RequestMapping(value="/front/lotto")
public class LottoController {

	private static final Logger logger = LoggerFactory.getLogger(LottoController.class);
	
	@Autowired
	private LottoService lottoService;
	
	@Value("#{config['PAGENUM']}") String PAGENUM;
	@Value("#{config['PAGESIZE_20']}") String PAGESIZE;
	@Value("#{config['BLOCKSIZE_10']}") String BLOCKSIZE;
	@Value("#{config['GETLOTTONUMBER_URL']}") String GETLOTTONUMBER_URL;
	
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

		paramMap.put("pageSize", PAGESIZE);
		paramMap.put("pageNum", paramMap.get("pageNum")==null?"1":paramMap.get("pageNum"));

		List<Map<String, String>> resultList = null;
		
		try {
			resultList = lottoService.list(paramMap);
			
			if(resultList.size() > 0){
				//paging		
				Map<String, String> pg = resultList.get(0);
				PagingView pv = new PagingView((int)Double.parseDouble(String.valueOf(pg.get("pageNum"))), Integer.parseInt(PAGESIZE), Integer.parseInt(BLOCKSIZE), (int)Double.parseDouble(String.valueOf(pg.get("totCnt"))));
				mav.addObject("paging", pv.print());
				//paging		
			}
			
		} catch (Exception e) {
			logger.info("Exception:" + e.toString());
		}
		
		mav.addObject("paramMap", paramMap);
		mav.addObject("resultList", resultList);
		return mav;
	}
	
	/**
	 * 당첨확인 리스트
	 * @param paramMap
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/listDang")
	public ModelAndView listDang(@RequestParam Map<String, String> paramMap, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/front/lottoDang");
		List<Map<String, String>> resultList = null;

		paramMap.put("pageSize", PAGESIZE);
		paramMap.put("pageNum", paramMap.get("pageNum")==null?"1":paramMap.get("pageNum"));
		
		logger.info("lotto_dang_num1:"+paramMap.get("lotto_dang_num1"));
		logger.info("lotto_dang_num2:"+paramMap.get("lotto_dang_num2"));
		logger.info("lotto_dang_num3:"+paramMap.get("lotto_dang_num3"));
		logger.info("lotto_dang_num4:"+paramMap.get("lotto_dang_num4"));
		logger.info("lotto_dang_num5:"+paramMap.get("lotto_dang_num5"));
		logger.info("lotto_dang_num6:"+paramMap.get("lotto_dang_num6"));
		
		try {
			logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
			resultList = lottoService.listDang(paramMap);
			
			if(resultList.size() > 0){
				//paging		
				Map<String, String> pg = resultList.get(0);
				PagingView pv = new PagingView((int)Double.parseDouble(String.valueOf(pg.get("pageNum"))), Integer.parseInt(PAGESIZE), Integer.parseInt(BLOCKSIZE), (int)Double.parseDouble(String.valueOf(pg.get("totCnt"))));
				mav.addObject("paging", pv.print());
				//paging		
			}
			
			logger.info("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
			
		} catch (Exception e) {
			logger.info("Exception:" + e.toString());
		}
		
		mav.addObject("paramMap", paramMap);
		mav.addObject("resultList", resultList);
		return mav;
	}
	
	/**
	 * 로또 당첨결과 가져와서 DB 적재
	 * http://localhost:8080/front/lotto/api/getLotto.mee?drwNo=300
	 * url이 있지만 기본적으로 스케줄러로 적용 누락부분은 URL로 수동으로 처리가능
	 * 
	 * @param paramMap
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 * @throws CommonCheckedException 
	 */
	@RequestMapping(value="/api/getLotto")
	@ResponseBody
	public String getLotto(@RequestParam Map<String, String> paramMap, HttpServletRequest request) throws IOException, CommonCheckedException{

		Map<String, String> resultMap = null;
		
		String getLottoNumber_result = "";
		// 로또 API로 당첨번호 가져오기
		String lottoUrl = GETLOTTONUMBER_URL;
		// 회차가 없으면 최신 있으면 해당회차정보 가져옴 drwNo=300 으로 파람 붙여서 옴
		if(!CommonFunction.isEmpty(paramMap.get("drwNo"))){
			lottoUrl += ("&drwNo=" + paramMap.get("drwNo"));
		}
		paramMap.put("lottoUrl", lottoUrl);
		getLottoNumber_result = getLottoNumber(paramMap);
		
		// json파싱 
		resultMap = getLottoNumParse(getLottoNumber_result);
		
		if(!CommonFunction.isEmpty(resultMap)){
			if("SUCCESS".equalsIgnoreCase(resultMap.get("returnValue"))){
				logger.info("성공");
				
				HashMap<String, String> detailParamMap = new HashMap<String, String>();
				detailParamMap.put("lotto_cnt", resultMap.get("drwNo"));
				// 해당회차 등록 여부 확인 
				Map<String, String> resultDetailMap = lottoService.detail(detailParamMap);
				if(CommonFunction.isEmpty(resultDetailMap)){
					logger.info("등록되지 않았군요");
					
					// 등록
					HashMap<String, String> insertParamMap = new HashMap<String, String>();
					insertParamMap.put("lotto_cnt", resultMap.get("drwNo"));
					insertParamMap.put("lotto_date", resultMap.get("drwNoDate"));
					insertParamMap.put("lotto_dang1_cnt", resultMap.get("firstPrzwnerCo"));
					insertParamMap.put("lotto_dang1_amt", resultMap.get("firstWinamnt"));
					insertParamMap.put("lotto_dang_num1", resultMap.get("drwtNo1"));
					insertParamMap.put("lotto_dang_num2", resultMap.get("drwtNo2"));
					insertParamMap.put("lotto_dang_num3", resultMap.get("drwtNo3"));
					insertParamMap.put("lotto_dang_num4", resultMap.get("drwtNo4"));
					insertParamMap.put("lotto_dang_num5", resultMap.get("drwtNo5"));
					insertParamMap.put("lotto_dang_num6", resultMap.get("drwtNo6"));
					insertParamMap.put("lotto_dang_num9", resultMap.get("bnusNo"));
					lottoService.insert(insertParamMap);
					
				}else{
					logger.info("이미 등록되었군요");
				}
				
				
			}else{
				logger.info("인터페이스 실패");
				throw new CommonCheckedException(CommonErrorCode.ERR_9999, "인터페이스 실패");
			}
		}else{
			logger.info("인터페이스 결과 비어있음");
			throw new CommonCheckedException(CommonErrorCode.ERR_9999, "인터페이스 결과 비어있음");
		}
			
		return getLottoNumber_result;
	}
	
	/**
	 * 최근 당첨결과 스케줄로 가져오기
	 * 초 분 시간 일(Day of Month) 월 요일(Day of Week, 1-7 : SUN-SAT) 년도(생략가능) 의 순이다.
	 * cron="0 0 0 ? * 7 : 매주 일요일 0시 0분 0초에 스케줄 돌으라
	 * fixedDelay=3000 이렇게 하면 3초마다 한번씩 돔
	 * @throws IOException
	 * @throws CommonCheckedException
	 */
	@Scheduled(cron="0 0 0 ? * 7")
	public void getLottoScheduled() throws IOException, CommonCheckedException{
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		logger.info("로또 스케줄러 시작");
		Map<String, String> resultMap = null;
		
		String getLottoNumber_result = "";
		// 로또 API로 당첨번호 가져오기
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("lottoUrl", GETLOTTONUMBER_URL);
		getLottoNumber_result = getLottoNumber(paramMap);
		
		// json파싱 
		resultMap = getLottoNumParse(getLottoNumber_result);
		
		if(!CommonFunction.isEmpty(resultMap)){
			if("SUCCESS".equalsIgnoreCase(resultMap.get("returnValue"))){
				logger.info("성공");
				
				HashMap<String, String> detailParamMap = new HashMap<String, String>();
				detailParamMap.put("lotto_cnt", resultMap.get("drwNo"));
				// 해당회차 등록 여부 확인 
				Map<String, String> resultDetailMap = lottoService.detail(detailParamMap);
				if(CommonFunction.isEmpty(resultDetailMap)){
					logger.info("등록되지 않았군요");
					
					// 등록
					HashMap<String, String> insertParamMap = new HashMap<String, String>();
					insertParamMap.put("lotto_cnt", resultMap.get("drwNo"));
					insertParamMap.put("lotto_date", resultMap.get("drwNoDate"));
					insertParamMap.put("lotto_dang1_cnt", resultMap.get("firstPrzwnerCo"));
					insertParamMap.put("lotto_dang1_amt", resultMap.get("firstWinamnt"));
					insertParamMap.put("lotto_dang_num1", resultMap.get("drwtNo1"));
					insertParamMap.put("lotto_dang_num2", resultMap.get("drwtNo2"));
					insertParamMap.put("lotto_dang_num3", resultMap.get("drwtNo3"));
					insertParamMap.put("lotto_dang_num4", resultMap.get("drwtNo4"));
					insertParamMap.put("lotto_dang_num5", resultMap.get("drwtNo5"));
					insertParamMap.put("lotto_dang_num6", resultMap.get("drwtNo6"));
					insertParamMap.put("lotto_dang_num9", resultMap.get("bnusNo"));
					lottoService.insert(insertParamMap);
					
				}else{
					logger.info("이미 등록되었군요");
				}
				
			}else{
				logger.info("인터페이스 실패");
				throw new CommonCheckedException(CommonErrorCode.ERR_9999, "인터페이스 실패");
			}
		}else{
			logger.info("인터페이스 결과 비어있음");
			throw new CommonCheckedException(CommonErrorCode.ERR_9999, "인터페이스 결과 비어있음");
		}
		logger.info("로또 스케줄러 끝");
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}
	
	
	/**
	 * 로또 추첨내역 가져오기 API
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String getLottoNumber(Map<String, String> paramMap) throws IOException{
		String result="";
		URL url = new URL(paramMap.get("lottoUrl"));

		// open connection
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true); // 입력스트림 사용
		conn.setDoOutput(true); // 출력스트림 사용
		conn.setUseCaches(false); // 캐시사용 안함
		conn.setReadTimeout(50000); // 타임아웃 : 3초
		conn.setRequestMethod("GET"); // GET or POST ...

		StringBuffer sb = new StringBuffer();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		for (;;) {
			String line = br.readLine();
			if (line == null)
				break;
			sb.append(line + "\n");
		}

		br.close();
		conn.disconnect();

		result = sb.toString();
		
		logger.info("로또 API데이터 가져오기:"+result);
		
		return result;
	}
	
	/**
	 * 로또 추첨결과 파싱하기
	 * @param param
	 * @return
	 */
	@SuppressWarnings("null")
	public Map<String, String> getLottoNumParse(String param){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		// json파싱 
		Map map = Collections.EMPTY_MAP;
		map = JsonParserUtil.getJsonParser(param);

		String returnValue = "";	// 인터페이스결과
		String drwNo = "";			// 회차
		String drwNoDate = "";		// 추첨일
		String firstPrzwnerCo = "";	// 1등당첨인원
		String firstWinamnt = "";	// 1등당첨금액
		String totSellamnt = "";	// 총판매금액
		String drwtNo1 = "";		// 당첨번호
		String drwtNo2 = "";		// 당첨번호
		String drwtNo3 = "";		// 당첨번호
		String drwtNo4 = "";		// 당첨번호
		String drwtNo5 = "";		// 당첨번호
		String drwtNo6 = "";		// 당첨번호
		String bnusNo = "";			// 보너스번호
		
		returnValue = map.get("returnValue").toString();
		drwNo = map.get("drwNo").toString();
		drwNoDate = map.get("drwNoDate").toString();
		firstPrzwnerCo = map.get("firstPrzwnerCo").toString();
		firstWinamnt = map.get("firstWinamnt").toString();
		totSellamnt = map.get("totSellamnt").toString();
		drwtNo1 = map.get("drwtNo1").toString();
		drwtNo2 = map.get("drwtNo2").toString();
		drwtNo3 = map.get("drwtNo3").toString();
		drwtNo4 = map.get("drwtNo4").toString();
		drwtNo5 = map.get("drwtNo5").toString();
		drwtNo6 = map.get("drwtNo6").toString();
		bnusNo = map.get("bnusNo").toString();

		logger.info("로또 API데이터 파싱결과");
		logger.info(returnValue +":"+drwNo +":"+drwNoDate +":"+firstPrzwnerCo +":"+firstWinamnt +":"+totSellamnt +":"+drwtNo1 +":"+drwtNo2 +":"+drwtNo3 +":"+drwtNo4 +":"+drwtNo5 +":"+drwtNo6 +":"+bnusNo);
		
		resultMap.put("returnValue", returnValue);
		resultMap.put("drwNo", drwNo);
		resultMap.put("drwNoDate", drwNoDate);
		resultMap.put("firstPrzwnerCo", firstPrzwnerCo);
		resultMap.put("firstWinamnt", firstWinamnt);
		resultMap.put("totSellamnt", totSellamnt);
		resultMap.put("drwtNo1", drwtNo1);
		resultMap.put("drwtNo2", drwtNo2);
		resultMap.put("drwtNo3", drwtNo3);
		resultMap.put("drwtNo4", drwtNo4);
		resultMap.put("drwtNo5", drwtNo5);
		resultMap.put("drwtNo6", drwtNo6);
		resultMap.put("bnusNo", bnusNo);
		
		return resultMap;
	}
	
	
}
