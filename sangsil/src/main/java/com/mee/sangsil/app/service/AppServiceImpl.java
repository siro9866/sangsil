package com.mee.sangsil.app.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mee.sangsil.common.FilesUtil;
import com.mee.sangsil.dao.Dao;
import com.mee.sangsil.dto.BoardDto;
import com.mee.sangsil.dto.FileDto;

@Service("appService")
public class AppServiceImpl implements AppService{

	private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
	
	@Autowired
	private Dao dao;

	@Value("#{config['TABLESC']}") String TABLESC;
	@Value("#{config['TABLE_T_BOARD']}") String TABLE_T_BOARD;
	@Value("#{config['FILEUPLOADPATH_BOARD']}") String FILEUPLOADPATH_BOARD;
	@Value("#{config['FILEDELETEPATH_BOARD']}") String FILEDELETEPATH_BOARD;
	
	@Override
	public List<BoardDto> list(String sql, BoardDto boardDto) {
		List<BoardDto> result = dao.list(sql, boardDto);
		return result;
	}
	
	@Override
	public BoardDto detail(String sql, BoardDto boardDto) {
		BoardDto result = (BoardDto) dao.detail(sql, boardDto);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject insert(String sql, BoardDto boardDto, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		FileDto fileDto = new FileDto();

		String autoSeq = "";
		//BOARD autoincre 값 가져옴
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tableSC", TABLESC);
		map.put("tableNM", TABLE_T_BOARD);
		autoSeq = dao.getString("app.board.autoSeq", map);		
		
		//파일저장
		if(boardDto.getFile().length > 0){
			fileDto.setUpload_path_name(FILEUPLOADPATH_BOARD);
			fileDto.setBoard_id(autoSeq);
			fileDto.setBoard_gbn(boardDto.getBoard_gbn());
			fileDto.setFile(boardDto.getFile());
			json = FilesUtil.upload(fileDto, request);
			
			FileDto resultDto = (FileDto) json.get("resultFileList");
			logger.info("resultDto:"+resultDto.getFile_name());
			logger.info("resultDto:"+resultDto.getFile_size());
			logger.info("resultDto:"+resultDto.getFile_ext());
			dao.insert("app.file.insert", resultDto);
		}
		//파일저장
		dao.insert(sql, boardDto);
		//화면단에 넘겨줌
		json.put("autoSeq", autoSeq);
		
		return json;
	}

	@Override
	public JSONObject update(String sql, BoardDto boardDto, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		FileDto fileDto = new FileDto();
		FileDto delfileDto = new FileDto();
		
		String sqlFileInst = "app.file.insert";
		
		//파일저장
		if(boardDto.getFile().length > 0){
			fileDto.setUpload_path_name(FILEUPLOADPATH_BOARD);
			fileDto.setBoard_id(boardDto.getBoard_id());
			fileDto.setBoard_gbn(boardDto.getBoard_gbn());
			fileDto.setFile(boardDto.getFile());
			json = FilesUtil.upload(fileDto, request);
			
			FileDto resultDto = (FileDto) json.get("resultFileList");
			logger.info("resultDto:"+resultDto.getFile_name());
			logger.info("resultDto:"+resultDto.getFile_size());
			logger.info("resultDto:"+resultDto.getFile_ext());
			
			logger.info("boardDto.getFile_id():"+boardDto.getFile_id());
			//기존 등록된 파일이 있으면 DB삭제
			if(!boardDto.getFile_id().isEmpty()){
				delfileDto.setFile_id(boardDto.getFile_id());
				dao.delete("app.file.delete", delfileDto);

				//실제파일 delete 폴더로 이동
				delfileDto.setUpload_path_name(boardDto.getPath_name());
				delfileDto.setFile_name(boardDto.getFile_name());
				delfileDto.setDelete_path_name(FILEDELETEPATH_BOARD);
				json = FilesUtil.delete(delfileDto, request);
			}
			dao.insert(sqlFileInst, resultDto);
		}		
		//파일저장
		dao.update(sql, boardDto);
		
		return json;
	}

	@Override
	public int delete(String sql, BoardDto boardDto, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		FileDto delfileDto = new FileDto();
		//기존 등록된 파일이 있으면 DB삭제
		if(!boardDto.getFile_id().isEmpty()){
			delfileDto.setFile_id(boardDto.getFile_id());
			dao.delete("app.file.delete", delfileDto);

			//실제파일 delete 폴더로 이동
			delfileDto.setUpload_path_name(boardDto.getPath_name());
			delfileDto.setFile_name(boardDto.getFile_name());
			delfileDto.setDelete_path_name(FILEDELETEPATH_BOARD);
			json = FilesUtil.delete(delfileDto, request);
		}		
		int result = dao.delete(sql, boardDto);
		return result;
	}

	@Override
	public String getString(String sql, HashMap<String, String> map) {
		return dao.getString(sql, map);
	}
}
