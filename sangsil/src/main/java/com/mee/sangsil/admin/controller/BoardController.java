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

import com.mee.sangsil.admin.service.BoardService;
import com.mee.sangsil.admin.service.CdService;
import com.mee.sangsil.common.CommonFunction;
import com.mee.sangsil.common.CommonUtil;
import com.mee.sangsil.common.PagingView;
import com.mee.sangsil.dto.BoardDto;
import com.mee.sangsil.dto.CdDto;
import com.mee.sangsil.dto.FileDto;



@Controller
@RequestMapping(value="/admin/board")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
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
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(@ModelAttribute BoardDto boardDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		CdDto cdDto = new CdDto();
		
		String board_cat_cd_id = null;
		String board_tag_cd_id = null;
		
		if(board_gbn_dev.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/dev/board_list");
			board_cat_cd_id = board_cat_cd_id_dev;
			board_tag_cd_id = board_tag_cd_id_dev;
		}else if(board_gbn_normal.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/normal/board_list");
			board_cat_cd_id = board_cat_cd_id_normal;
			board_tag_cd_id = board_tag_cd_id_normal;
		}else if(board_gbn_image.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/image/board_list");
			board_cat_cd_id = board_cat_cd_id_image;
			board_tag_cd_id = board_tag_cd_id_image;
		}else{
			boardDto.setBoard_gbn(board_gbn_dev);
			board_cat_cd_id = board_cat_cd_id_dev;
			board_tag_cd_id = board_tag_cd_id_dev;
			mav.setViewName("/admin/board/dev/board_list");
		}
		
		List<BoardDto> resultList = null;
		resultList = boardService.list("admin.board.list", boardDto);
		
		if(resultList.size() > 0){
			//paging		
			BoardDto pg = resultList.get(0);
			PagingView pv = new PagingView(pg.getPageNum(), boardDto.getPageSize(), boardDto.getBlockSize(), pg.getTotCnt());
			mav.addObject("paging", pv.print());
			//paging		
		}
		
		if(CommonFunction.isNotNull(board_cat_cd_id)){
			//카테고리 리스트
			cdDto.setCd_id(board_cat_cd_id);
			List<CdDto> catList = cdService.list("admin.cd.detailList", cdDto);
			mav.addObject("catList", catList);
		}
		
		if(CommonFunction.isNotNull(board_tag_cd_id)){
			//태그 리스트
			cdDto.setCd_id(board_tag_cd_id);
			List<CdDto> tagList = cdService.list("admin.cd.detailList", cdDto);
			mav.addObject("tagList", tagList);
		}
		
		mav.addObject("paramDto", boardDto);
		mav.addObject("resultList", resultList);
		
		logger.info("properties:");
		
		return mav;
	}
	
	/**
	 * 상세
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/detail")
	public ModelAndView detail(@ModelAttribute BoardDto boardDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		if(board_gbn_dev.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/dev/board_detail");
		}else if(board_gbn_normal.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/normal/board_detail");
		}else if(board_gbn_image.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/image/board_detail");
		}else{
			mav.setViewName("/admin/board/dev/board_detail");
		}		
		
		BoardDto result = null;
		List<FileDto> fileList = null;
		List<BoardDto> resultList = null;
		
		try {
			//게시글정보
			result = boardService.detail("admin.board.detail", boardDto);
			// 파일정보
			fileList = boardService.listFile("admin.file.list", boardDto);
			//이전 다음글
			resultList  = boardService.list("admin.board.detailList", boardDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mav.addObject("paramDto", boardDto);
		mav.addObject("result", result);
		mav.addObject("fileList", fileList);
		mav.addObject("resultList", resultList);
		
		return mav;
	}
	
	/**
	 * 인서트 업데이트 폼
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/form")
	public ModelAndView iuForm(@ModelAttribute BoardDto boardDto, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		List<FileDto> fileList = null;
		
		logger.info("boardDto.getBoard_gbn():"+boardDto.getBoard_gbn());
		
		String board_cat_cd_id = board_cat_cd_id_dev;
		String board_tag_cd_id = board_tag_cd_id_dev;
		
		if(board_gbn_dev.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/dev/board_form");
			board_cat_cd_id = board_cat_cd_id_dev;
			board_tag_cd_id = board_tag_cd_id_dev;
		}else if(board_gbn_normal.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/normal/board_form");
			board_cat_cd_id = board_cat_cd_id_normal;
			board_tag_cd_id = board_tag_cd_id_normal;
		}else if(board_gbn_image.equals(boardDto.getBoard_gbn())){
			mav.setViewName("/admin/board/image/board_form");
			board_cat_cd_id = board_cat_cd_id_image;
			board_tag_cd_id = board_tag_cd_id_image;
		}else{
			mav.setViewName("/admin/board/dev/board_form");
		}		
		
		BoardDto result = boardService.detail("admin.board.detail", boardDto);
		// 파일정보
		fileList = boardService.listFile("admin.file.list", boardDto);
		
		//카테고리 리스트
		CdDto cdDto = new CdDto();
		cdDto.setCd_id(board_cat_cd_id);
		List<CdDto> catList = cdService.list("admin.cd.detailList", cdDto);

		//태그 리스트
		cdDto.setCd_id(board_tag_cd_id);
		List<CdDto> tagList = cdService.list("admin.cd.detailList", cdDto);
		
		mav.addObject("paramDto", boardDto);
		mav.addObject("result", result);
		mav.addObject("fileList", fileList);
		mav.addObject("catList", catList);
		mav.addObject("tagList", tagList);
		return mav;
	}	
	
	/**
	 * 인서트
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/insert")
	public ModelAndView insert(@ModelAttribute BoardDto boardDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		JSONObject json = new JSONObject();
		mav.setViewName("/common/result");
		CommonUtil.getReturnCodeFail(json);
		
		try {
			
			//사용자정보
			CommonUtil.setInUserInfo(request, boardDto);		
			CommonUtil.setUpUserInfo(request, boardDto);
			json = boardService.insert("admin.board.insert", boardDto, request);
			//저장 성공시 코드값 세팅
			CommonUtil.getReturnCodeSuc(json);
			//저장후 페이지 이동
			json.put("goUrl", "/admin/board/detail.mee?board_id="+json.get("autoSeq").toString() + "&board_gbn=" + boardDto.getBoard_gbn());
		} catch (Exception e) {
			//저장 실패시 코드값 세팅
			logger.info("EXCEPTION insert E:" + e.toString());
			CommonUtil.getReturnCodeFail(json);
			json.put("goUrl", "/admin/board/list.mee?" + "board_gbn=" + boardDto.getBoard_gbn());
		}
		//결과값 전송
		logger.info(json.toJSONString());
		mav.addObject("resultJson", json);
		return mav;
		
	}
	
	/**
	 * 업데이트
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(@ModelAttribute BoardDto boardDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		JSONObject json = new JSONObject();
		mav.setViewName("/common/result");
		CommonUtil.getReturnCodeFail(json);
		
		try {
			
			//사용자정보
			CommonUtil.setUpUserInfo(request, boardDto);
			json = boardService.update("admin.board.update", boardDto, request);
			//저장 성공시 코드값 세팅
			CommonUtil.getReturnCodeSuc(json);
			//저장후 페이지 이동
			json.put("goUrl", "/admin/board/detail.mee?board_id="+boardDto.getBoard_id() + "&board_gbn=" + boardDto.getBoard_gbn());
		} catch (Exception e) {
			//저장 실패시 코드값 세팅
			logger.info("EXCEPTION insert E:" + e.toString());
			CommonUtil.getReturnCodeFail(json);
			json.put("goUrl", "/admin/board/list.mee?" + "board_gbn=" + boardDto.getBoard_gbn());
		}
		//결과값 전송
		logger.info(json.toJSONString());
		mav.addObject("resultJson", json);
		return mav;
		
	}
	
	/**
	 * 삭제
	 * @param boardDto
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JSONObject delete(@ModelAttribute BoardDto boardDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//사용자정보
			CommonUtil.setUpUserInfo(request, boardDto);			
			boardService.delete("admin.board.delete", boardDto, request);
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
		}
		
		logger.info(json.toJSONString());
		return json;
		
	}
	
	
	@RequestMapping(value="/updateFile")
	@ResponseBody
	public JSONObject updateFile(@ModelAttribute FileDto fileDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//사용자정보
			CommonUtil.setUpUserInfo(request, fileDto);			
			boardService.updateFile("admin.file.update", fileDto, request);
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
		}
		
		logger.info(json.toJSONString());
		return json;
		
	}
	@RequestMapping(value="/deleteFile")
	@ResponseBody
	public JSONObject deleteFile(@ModelAttribute FileDto fileDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		
		try {
			//사용자정보
			CommonUtil.setUpUserInfo(request, fileDto);			
			boardService.deleteFile("admin.file.delete", fileDto, request);
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
