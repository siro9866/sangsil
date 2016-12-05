package com.mee.sangsil.app.controller;

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

import com.mee.sangsil.app.service.AppService;
import com.mee.sangsil.admin.service.CdService;
import com.mee.sangsil.common.CommonFunction;
import com.mee.sangsil.common.CommonUtil;
import com.mee.sangsil.common.PagingView;
import com.mee.sangsil.dto.BoardDto;
import com.mee.sangsil.dto.CdDto;



@Controller
@RequestMapping(value="/app/board")
public class AppController {

	private static final Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private AppService appService;
	@Autowired
	private CdService cdService;
	
	@Value("#{config['CD_ID_BAA']}") String board_gbn_cd_id;
	@Value("#{config['CD_ID_BAA01']}") String board_gbn_dev;
	@Value("#{config['CD_ID_BAA02']}") String board_gbn_normal;
	@Value("#{config['CD_ID_BAA03']}") String board_gbn_image;
	
	//개발게시
	@Value("#{config['CD_ID_CBA']}") String board_cat_cd_id_dev;
	@Value("#{config['CD_ID_DAA']}") String board_tag_cd_id_dev;
	//일반게시
	@Value("#{config['CD_ID_CBB']}") String board_cat_cd_id_normal;
	@Value("#{config['CD_ID_DAB']}") String board_tag_cd_id_normal;
	//이미지게시
	@Value("#{config['CD_ID_CBC']}") String board_cat_cd_id_image;
	@Value("#{config['CD_ID_DAC']}") String board_tag_cd_id_image;	
	
	/**
	 * 리스트
	 * @param board_gbn
	 * url:/app/board/list.mee?board_gbn=BAA03
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public JSONObject list(@ModelAttribute BoardDto boardDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject result = new JSONObject();
		JSONObject json = new JSONObject();
		
		String board_cat_cd_id = null;
		String board_tag_cd_id = null;
		
		if(board_gbn_dev.equals(boardDto.getBoard_gbn())){
			board_cat_cd_id = board_cat_cd_id_dev;
			board_tag_cd_id = board_tag_cd_id_dev;
		}else if(board_gbn_normal.equals(boardDto.getBoard_gbn())){
			board_cat_cd_id = board_cat_cd_id_normal;
			board_tag_cd_id = board_tag_cd_id_normal;
		}else if(board_gbn_image.equals(boardDto.getBoard_gbn())){
			board_cat_cd_id = board_cat_cd_id_image;
			board_tag_cd_id = board_tag_cd_id_image;
		}else{
			board_cat_cd_id = board_cat_cd_id_dev;
			board_tag_cd_id = board_tag_cd_id_dev;
		}
		
		List<BoardDto> rows = null;
		try{
			rows = appService.list("app.board.list", boardDto);
			CommonUtil.getReturnCodeSuc(json);
		}catch(Exception e){
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		
//		if(resultList.size() > 0){
//			//paging		
//			BoardDto pg = resultList.get(0);
//			PagingView pv = new PagingView(pg.getPageNum(), boardDto.getPageSize(), boardDto.getBlockSize(), pg.getTotCnt());
//			mav.addObject("paging", pv.print());
//			//paging		
//		}
		
		json.put("rows", rows);
		result.put("RESULT", json);
		
		logger.debug(request.getRequestURI() +"\n" + "RESULT:" + result.toJSONString());
		
		return result;
		
	}
	
	/**
	 * 게시글에 업로드된 이미지 리스트 가져오기
	 * url:/app/board/imageList.mee?board_gbn=BAA03&board_id=30
	 * @param board_gbn, board_id
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/imageList")
	@ResponseBody
	public JSONObject imageList(@ModelAttribute BoardDto boardDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject result = new JSONObject();
		JSONObject json = new JSONObject();
		
		List<BoardDto> rows = null;
		try{
			rows = appService.list("app.file.list", boardDto);
			CommonUtil.getReturnCodeSuc(json);
		}catch(Exception e){
			CommonUtil.getReturnCodeFail(json, e);
		}
		
		json.put("rows", rows);
		result.put("RESULT", json);
		
		logger.debug(request.getRequestURI() +"\n" + "RESULT:" + result.toJSONString());
		
		return result;
		
	}
	
	
}
