package com.mee.sangsil.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

//	@Value("#{config['code01']}") String code;
	
	/**
	 * 파일 인클루드
	 * @param fineName
	 * @param depth1
	 * @param depth2
	 * @return
	 */
	@RequestMapping("/include")
	public ModelAndView include(
				@RequestParam(value="fileName", required = false) String fileName
				,@RequestParam(value="depth1", required = false) String depth1
				,@RequestParam(value="depth2", required = false) String depth2
			) {
//		logger.info(fileName);
		ModelAndView mav = new ModelAndView(fileName);

		mav.addObject("depth2", depth2);
		mav.addObject("depth1", depth1);
		return mav;
	}
	
	
	/**
	 * 파일 다운로드
	 * @param path
	 * @param fileName
	 * @return
	 */
	@RequestMapping("/fileDownload")
	public ModelAndView fileDownload(
			@RequestParam(value="path", required = false) String path
			,@RequestParam(value="fileName", required = false) String fileName
			,@RequestParam(value="originalFileName", required = false) String originalFileName
		) {
		ModelAndView mav = new ModelAndView("downloadView");
		String fullPath = path+fileName;
		File file = new File(fullPath);
		mav.addObject("downloadFile", file);
		mav.addObject("downloadFileName", originalFileName);
		logger.info("fullPath:"+fullPath);
		
		return mav;
	}
	
	/**
	 * SmartEditor 이미지 업로드 팝업
	 * @return
	 */
	@RequestMapping("/photo_uploader")
	public ModelAndView photo_uploader() {
		ModelAndView mav = new ModelAndView("/common/photo_uploader");
		return mav;
	}

	/**
	 * 파일업로드(에디터용)
	 * //SmartEditor 이미지업로드부분 수정
	 * @param fileData
	 * @param path
	 * @param fileName
	 * @throws IOException
	 */
	@RequestMapping(value = "/smartFileUpload", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject fileUpload(Model model, MultipartRequest multipartRequest, HttpServletRequest request) throws IOException{
		JSONObject json = new JSONObject();
		MultipartFile imgfile = multipartRequest.getFile("Filedata");
		Calendar cal = Calendar.getInstance();
		String fileName = imgfile.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy");
		//파일명 = 초초 까지 + 시퀀스 + 확장자
		String replaceName = dateFormat.format(calendar.getTime()) + "_" + SequenceUtil.getSeqNumberFile(); 		
		
		String root = request.getSession().getServletContext().getRealPath("/");
		
		//월별로 디렉토리 생성
		String dateDirectory = dateFormat2.format(calendar.getTime());
		//파일업로드시 root 있어야함
		String path = root + "upload" + File.separator + "smartEdit" + File.separator + dateDirectory + File.separator;
		//에디터에 경로 전달시 root 없어야함
		String editPath = File.separator + "upload" + File.separator + "smartEdit" + File.separator + dateDirectory + File.separator;
		logger.info("File Upload Path:::"+path);

		//제한 10M
		long maxFileSize = 100000000;
		long fileSize = imgfile.getSize();
		
		if(fileSize >= maxFileSize){
			CommonUtil.getReturnCodeFail(json);
			CommonUtil.getReturnCodeFailFileSize(json);
			return json;
		}else{
			FilesUtil.fileUpload(imgfile, path, replaceName+fileType);
		}
		
		//에디터에 응답주는 내용
		json.put("path", editPath);
		json.put("filename", replaceName+fileType);
		
		return json;
	}
}
