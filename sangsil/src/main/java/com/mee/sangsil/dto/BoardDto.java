package com.mee.sangsil.dto;

public class BoardDto extends ComDto{


	
	private String board_id;	// '글번호'
	private String board_gbn;	//'게시판 구분'
	private String board_cat;	//'카테고리'
	private String board_tag;	//'태그'
	private String board_title;	//'제목' 
	private String board_txt;	//'내용'
	private String ans_yn;		//'답변여부' 
	private String disp_order;	//'정렬순서'
	private String hit_cnt;		//'조회수'
	
	private String file_id;		//'파일 id'
	private String path_name;		//'파일경로'
	private String view_path_name;		//'웹경'
	private String file_name;		//'파일명'
	private String file_size;		//'파일사이즈'
	private String originalFileName;		//'오리지날 파일명'
	private String file_ext;		//'파일확장자'
	private String upload_path_name;		//'파일확장자'
	private String cntFiles;		//'파일확장자'

	private String board_gbnNM;	//'게시판 구분'
	private String board_catNM;	//'카테고리'
	private String board_tagNM;	//'태그'
	
	public String getBoard_id() {
		return board_id;
	}
	public void setBoard_id(String board_id) {
		this.board_id = board_id;
	}
	public String getBoard_gbn() {
		return board_gbn;
	}
	public void setBoard_gbn(String board_gbn) {
		this.board_gbn = board_gbn;
	}
	public String getBoard_cat() {
		return board_cat;
	}
	public void setBoard_cat(String board_cat) {
		this.board_cat = board_cat;
	}
	public String getBoard_tag() {
		return board_tag;
	}
	public void setBoard_tag(String board_tag) {
		this.board_tag = board_tag;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_txt() {
		return board_txt;
	}
	public void setBoard_txt(String board_txt) {
		this.board_txt = board_txt;
	}
	public String getAns_yn() {
		return ans_yn;
	}
	public void setAns_yn(String ans_yn) {
		this.ans_yn = ans_yn;
	}
	public String getDisp_order() {
		return disp_order;
	}
	public void setDisp_order(String disp_order) {
		this.disp_order = disp_order;
	}
	public String getHit_cnt() {
		return hit_cnt;
	}
	public void setHit_cnt(String hit_cnt) {
		this.hit_cnt = hit_cnt;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getFile_ext() {
		return file_ext;
	}
	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}
	public String getBoard_gbnNM() {
		return board_gbnNM;
	}
	public void setBoard_gbnNM(String board_gbnNM) {
		this.board_gbnNM = board_gbnNM;
	}
	public String getBoard_catNM() {
		return board_catNM;
	}
	public void setBoard_catNM(String board_catNM) {
		this.board_catNM = board_catNM;
	}
	public String getBoard_tagNM() {
		return board_tagNM;
	}
	public void setBoard_tagNM(String board_tagNM) {
		this.board_tagNM = board_tagNM;
	}
	public String getPath_name() {
		return path_name;
	}
	public void setPath_name(String path_name) {
		this.path_name = path_name;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getUpload_path_name() {
		return upload_path_name;
	}
	public void setUpload_path_name(String upload_path_name) {
		this.upload_path_name = upload_path_name;
	}
	public String getCntFiles() {
		return cntFiles;
	}
	public void setCntFiles(String cntFiles) {
		this.cntFiles = cntFiles;
	}
	public String getView_path_name() {
		return view_path_name;
	}
	public void setView_path_name(String view_path_name) {
		this.view_path_name = view_path_name;
	}
}
