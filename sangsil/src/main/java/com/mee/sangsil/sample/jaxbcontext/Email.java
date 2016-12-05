package com.mee.sangsil.sample.jaxbcontext;

import javax.xml.bind.annotation.XmlAttribute;

public class Email {

	private String id;
	private String emailAddr;
	/**
	 * @return the id
	 */
	@XmlAttribute
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the emailAddr
	 */
	@XmlAttribute
	public String getEmailAddr() {
		return emailAddr;
	}
	/**
	 * @param emailAddr the emailAddr to set
	 */
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	
	
}
