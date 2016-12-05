package com.mee.sangsil.dto;

public class FavorityDto extends ComDto{

	
	private String favority_id; 
	private String favority_nm; 
	private String favority_url; 
	private String favority_img; 
	private String favority_cmt; 
	private String disp_order; 
	private String hit_cnt; 
	
	public String getFavority_id() {
		return favority_id;
	}
	public void setFavority_id(String favority_id) {
		this.favority_id = favority_id;
	}
	public String getFavority_nm() {
		return favority_nm;
	}
	public void setFavority_nm(String favority_nm) {
		this.favority_nm = favority_nm;
	}
	public String getFavority_url() {
		return favority_url;
	}
	public void setFavority_url(String favority_url) {
		this.favority_url = favority_url;
	}
	public String getFavority_img() {
		return favority_img;
	}
	public void setFavority_img(String favority_img) {
		this.favority_img = favority_img;
	}
	public String getFavority_cmt() {
		return favority_cmt;
	}
	public void setFavority_cmt(String favority_cmt) {
		this.favority_cmt = favority_cmt;
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
	
}
