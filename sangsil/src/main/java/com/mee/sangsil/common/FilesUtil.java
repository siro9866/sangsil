package com.mee.sangsil.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mee.sangsil.dto.FileDto;

public class FilesUtil {

	private static final Logger logger = LoggerFactory.getLogger(FilesUtil.class);
	private static BufferedOutputStream stream;
	
	public static JSONObject upload(FileDto fileDto, HttpServletRequest request){
		JSONObject json = new JSONObject();
		List resultFileList = new ArrayList<FileDto>();
		
		try {
				if(fileDto.getFile().length == 0){
					CommonUtil.getReturnCodeNotFile(json);
					return json;
				}
				//실제 업로드될 파일명
				String fileName = "";
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				
				//연별로 디렉토리 생성
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy");
				//연별로 디렉토리 생성
				String dateDirectory = dateFormat2.format(calendar.getTime());
				
				//파일경로
				String root = request.getSession().getServletContext().getRealPath("");
				String viewPathname = File.separator + "upload" + File.separator + fileDto.getUpload_path_name() + File.separator + dateDirectory + File.separator;
				String pathname = root + viewPathname;
				logger.debug("pathname:"+pathname);
				
				File dir = new File(pathname);
				if (!dir.exists()) {
					dir.mkdirs();
				}				
	
				for(int i=0; i<fileDto.getFile().length; i++){
					//파일명 인코딩
					String originalFileName = fileDto.getFile()[i].getOriginalFilename();
					//확장자체크
					int pos = originalFileName.lastIndexOf(".");
					String ext = originalFileName.substring(pos+1, originalFileName.length());
					
					//파일명 = 초초 까지 + 시퀀스 + 확장자
					fileName = dateFormat.format(calendar.getTime()) + "_" + SequenceUtil.getSeqNumberFile() + "." + ext; 
					
					byte fileData[] = fileDto.getFile()[i].getBytes();
	
					// Create the file on server
					File serverFile = new File(pathname + File.separator + fileName);
					stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData);
	
					fileDto.setFile_name(fileName);
					fileDto.setOriginalFileName(originalFileName);
					fileDto.setPath_name(pathname);
					fileDto.setFile_ext(ext);
					fileDto.setFile_size(Long.toString(fileDto.getFile()[i].getSize()));
					fileDto.setView_path_name(viewPathname);
					
					//결과 파일 정보 적재
					FileDto resultFileDto = new FileDto();
					resultFileDto.setBoard_id(fileDto.getBoard_id());
					resultFileDto.setBoard_gbn(fileDto.getBoard_gbn());
					resultFileDto.setPath_name(fileDto.getPath_name());
					resultFileDto.setView_path_name(fileDto.getView_path_name());
					resultFileDto.setOriginalFileName(fileDto.getOriginalFileName());
					resultFileDto.setFile_size(fileDto.getFile_size());
					resultFileDto.setFile_ext(fileDto.getFile_ext());
					resultFileDto.setFile_name(fileDto.getFile_name());
					resultFileDto.setIn_user(fileDto.getIn_user());
					resultFileDto.setIn_ip(fileDto.getIn_ip());
					resultFileDto.setUp_user(fileDto.getUp_user());
					resultFileDto.setUp_ip(fileDto.getUp_ip());
					resultFileList.add(resultFileDto);
					
					
					logger.info("FILEUPLOAD===============================================================");
					logger.info("FILE COUNT("+fileDto.getFile().length+"):THIS FILE("+(i+1)+")");
					logger.info("root:"+root);
					logger.info("pathname:"+pathname);
					logger.info("dateFormat:"+dateFormat.format(calendar.getTime()));
					logger.info("Server File Location=" + serverFile.getAbsolutePath());
					logger.info("file.getOriginalFilename():"+fileDto.getFile()[i].getOriginalFilename());
					logger.info("fileName:"+fileName);
					logger.info("ext:"+ext);
					logger.info("file.getName():"+fileDto.getFile()[i].getName());
					logger.info("file.getBytes():"+fileDto.getFile()[i].getBytes());
					logger.info("file.getSize():"+fileDto.getFile()[i].getSize());
					logger.info("file.getInputStream():"+fileDto.getFile()[i].getInputStream());
					logger.info("FILEUPLOAD===============================================================");
				}
				
				json.put("resultFileList", resultFileList);
				CommonUtil.getReturnCodeSuc(json);
			} catch (Exception e) {
				// TODO: handle exception
				CommonUtil.getReturnCodeFail(json);
				logger.info("PROGRAM_Exception:"+e);
			} finally {
				if(stream != null){
					try {
						stream.close();
					} catch (Exception e2) {
						// TODO: handle exception
						logger.info("STREAM ERROR::"+e2);
					}
				}
			}
			
		return json;
	}
	
	public static JSONObject delete(FileDto fileDto, HttpServletRequest request){
		JSONObject json = new JSONObject();
		
		//기존등록된 파일 삭제
		String dFile = fileDto.getUpload_path_name() + File.separator + fileDto.getFile_name();
		logger.info("dFilePATH:"+dFile);
		try {
			File f = new File(dFile);
			if (f.delete()) {
				CommonUtil.getReturnCodeSuc(json);
			}
		} catch (Exception e) {
			CommonUtil.getReturnCodeFail(json, e);
		}

		return json;
	}
	
	// 파일을 해당위치로 복사하고 지운다.
	public static JSONObject backupDelete(FileDto fileDto, HttpServletRequest request){
		JSONObject json = new JSONObject();
		
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		
		String root = request.getSession().getServletContext().getRealPath("/");
		//기존등록된 파일 DELETE 폴더로 업로드
		String cFilePath = root + "upload" + File.separator + fileDto.getDelete_path_name();
		String cFile = cFilePath + File.separator + fileDto.getFile_name();
		//기존등록된 파일 삭제
		String dFile = fileDto.getUpload_path_name() + File.separator + fileDto.getFile_name();
		
		try {
			File dir = new File(cFilePath);
			if(! dir.exists()) dir.mkdir();
			
			inputStream = new FileInputStream(dFile);
			outputStream = new FileOutputStream(cFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			CommonUtil.getReturnCodeFail(json);
		}
			 
		FileChannel fcin = inputStream.getChannel();
		FileChannel fcout = outputStream.getChannel();
			 
		long size = 0;
		try {
			size = fcin.size();
			fcin.transferTo(0, size, fcout);
				 
			fcout.close();
			fcin.close();
			outputStream.close();
			inputStream.close();
			CommonUtil.getReturnCodeSuc(json);
		} catch (IOException e) {
			e.printStackTrace();
			CommonUtil.getReturnCodeFail(json);
		}
			 
		File f = new File(dFile);
		if (f.delete()) {
			CommonUtil.getReturnCodeSuc(json);
		}

		return json;
	}
	
	
	public static void fileUpload(MultipartFile fileData, String path, String fileName) throws IOException {
		String originalFileName = fileData.getOriginalFilename();
		String contentType = fileData.getContentType();
		long fileSize = fileData.getSize();

		
		logger.info("file Info");
		logger.info("fileName " + fileName);
		logger.info("originalFileName :" + originalFileName);
		logger.info("contentType :" + contentType);
		logger.info("fileSize :" + fileSize);

		
		InputStream is = null;
		OutputStream out = null;
		try {
			if (fileSize > 0) {
				is = fileData.getInputStream();
				File realUploadDir = new File(path);
				if (!realUploadDir.exists()) {
					realUploadDir.mkdirs();
				}
				out = new FileOutputStream(path +"/"+ fileName);
				FileCopyUtils.copy(is, out);
			}else{
				new IOException("잘못된 파일을 업로드 하셨습니다.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			new IOException("파일 업로드에 실패하였습니다.");
		}finally{
			if(out != null){out.close();}
			if(is != null){is.close();}
		}
	}	
	
}
