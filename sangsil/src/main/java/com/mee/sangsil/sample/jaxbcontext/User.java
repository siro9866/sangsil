package com.mee.sangsil.sample.jaxbcontext;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private String name;
	private String address;
	private String phone;
	private Emails emails;
	
	public User() {
	}
	/**
	 * @return the name
	 */
	@XmlElement
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	@XmlElement
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the phone
	 */
	@XmlElement
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the emails
	 */
	@XmlElement
	public Emails getEmails() {
		return emails;
	}
	/**
	 * @param emails the emails to set
	 */
	public void setEmails(Emails emails) {
		this.emails = emails;
	}
	
	
}
