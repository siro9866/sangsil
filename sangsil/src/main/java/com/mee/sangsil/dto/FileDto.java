package com.mee.sangsil.dto;

public class FileDto extends ComDto{


	private String file_id;
	private String board_id;
	private String board_gbn;
	private String path_name; 	//풀 경로
	private String view_path_name; // 이미지
	private String originalFileName;
	private String file_name; 
	private String file_size;
	private String file_ext;		//확장자
	private String disp_order;
	private String upload_path_name; 	//폴더경로
	private String delete_path_name; 	//삭제폴더경로
	
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
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
	public String getPath_name() {
		return path_name;
	}
	public void setPath_name(String path_name) {
		this.path_name = path_name;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
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
	public String getFile_ext() {
		return file_ext;
	}
	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}
	public String getDelete_path_name() {
		return delete_path_name;
	}
	public void setDelete_path_name(String delete_path_name) {
		this.delete_path_name = delete_path_name;
	}
	public String getDisp_order() {
		return disp_order;
	}
	public void setDisp_order(String disp_order) {
		this.disp_order = disp_order;
	}
	public String getView_path_name() {
		return view_path_name;
	}
	public void setView_path_name(String view_path_name) {
		this.view_path_name = view_path_name;
	}
}
