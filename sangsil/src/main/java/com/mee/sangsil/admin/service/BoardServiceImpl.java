package com.mee.sangsil.admin.service;

import java.util.ArrayList;
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

@Service("boardService")
public class BoardServiceImpl implements BoardService{

	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	private Dao dao;

	@Value("#{config['TABLESC']}") String TABLESC;
	@Value("#{config['TABLE_T_BOARD']}") String TABLE_T_BOARD;
	@Value("#{config['FILEUPLOADPATH_BOARD']}") String FILEUPLOADPATH_BOARD;
	@Value("#{config['FILEDELETEPATH_BOARD']}") String FILEDELETEPATH_BOARD;
	@Value("#{config['CD_ID_BAA01']}") String board_gbn_dev;
	@Value("#{config['CD_ID_BAA02']}") String board_gbn_normal;
	@Value("#{config['CD_ID_BAA03']}") String board_gbn_image;
	
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
		List resultFileList = new ArrayList<FileDto>();
		FileDto fileDto = new FileDto();
		
		String autoSeq = "";
		//BOARD autoincre 값 가져옴
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tableSC", TABLESC);
		map.put("tableNM", TABLE_T_BOARD);
		autoSeq = dao.getString("admin.board.autoSeq", map);		
		
		//파일저장
		if(!boardDto.getFile()[0].isEmpty()){
			
			fileDto.setUpload_path_name(FILEUPLOADPATH_BOARD);
			fileDto.setBoard_id(autoSeq);
			fileDto.setBoard_gbn(boardDto.getBoard_gbn());
			fileDto.setFile(boardDto.getFile());
			json = FilesUtil.upload(fileDto, request);
			
			resultFileList = (List) json.get("resultFileList");
			
			for(int i=0; i<resultFileList.size(); i++){
				FileDto resultDto = (FileDto) resultFileList.get(i);
				logger.info("resultFileList"+Integer.toString(i));
				logger.info("resultDto["+i+"]:"+resultDto.getFile_name());
				logger.info("resultDto["+i+"]:"+resultDto.getFile_size());
				logger.info("resultDto["+i+"]:"+resultDto.getFile_ext());
				dao.insert("admin.file.insert", resultDto);
			}
			
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
		List resultFileList = new ArrayList<FileDto>();
		FileDto fileDto = new FileDto();
		FileDto delfileDto = new FileDto();
		
		//파일추가저장
		if(!boardDto.getFile()[0].isEmpty()){
			
			// 삭제후 등록
			// 파일 업로드 하나만 되는 화면에서는 업데이트시삭제
			if(boardDto.getBoard_gbn().equalsIgnoreCase(board_gbn_dev) || boardDto.getBoard_gbn().equalsIgnoreCase(board_gbn_dev)){
				//기존 등록된 파일이 있으면 DB삭제
				List<FileDto> files = dao.list("admin.file.list", boardDto);
				
				// 첨부파일이 있으면 파일 정보 삭제 
				for(int i=0; i<files.size(); i++){
					delfileDto.setFile_id(files.get(i).getFile_id());
					dao.delete("admin.file.delete", delfileDto);
					//실제파일 delete 폴더로 이동
					delfileDto.setUpload_path_name(files.get(i).getPath_name());
					delfileDto.setFile_name(files.get(i).getFile_name());
					delfileDto.setDelete_path_name(FILEDELETEPATH_BOARD);
					json = FilesUtil.delete(delfileDto, request);
				}
			}
			
			fileDto.setUpload_path_name(FILEUPLOADPATH_BOARD);
			fileDto.setBoard_id(boardDto.getBoard_id());
			fileDto.setBoard_gbn(boardDto.getBoard_gbn());
			fileDto.setFile(boardDto.getFile());
			json = FilesUtil.upload(fileDto, request);
			
			resultFileList = (List) json.get("resultFileList");
			
			for(int i=0; i<resultFileList.size(); i++){
				FileDto resultDto = (FileDto) resultFileList.get(i);
				logger.info("resultFileList"+Integer.toString(i));
				logger.info("resultDto["+i+"]:"+resultDto.getFile_name());
				logger.info("resultDto["+i+"]:"+resultDto.getFile_size());
				logger.info("resultDto["+i+"]:"+resultDto.getFile_ext());
				dao.insert("admin.file.insert", resultDto);
			}
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
		
		List<FileDto> files = dao.list("admin.file.list", boardDto);
		
		// 첨부파일이 있으면 파일 정보 삭제 
		for(int i=0; i<files.size(); i++){
			delfileDto.setFile_id(files.get(i).getFile_id());
			dao.delete("admin.file.delete", delfileDto);
			//실제파일 delete 폴더로 이동
			delfileDto.setUpload_path_name(files.get(i).getPath_name());
			delfileDto.setFile_name(files.get(i).getFile_name());
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

	@Override
	public List<FileDto> listFile(String sql, BoardDto boardDto) {
		List<FileDto> result = dao.list(sql, boardDto);
		return result;
	}

	@Override
	public int updateFile(String sql, FileDto fileDto,
			HttpServletRequest request) {
		return dao.update(sql, fileDto);
	}

	@Override
	public int deleteFile(String sql, FileDto fileDto,
			HttpServletRequest request) {
		return dao.delete(sql, fileDto);
	}
}
