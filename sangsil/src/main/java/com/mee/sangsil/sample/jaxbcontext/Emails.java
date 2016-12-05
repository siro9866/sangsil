package com.mee.sangsil.sample.jaxbcontext;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;


public class Emails {

	private Collection<Email> email;
	
	public Emails() {
		email = new ArrayList<Email>();
	}
	
	/**
	 * @return the emails
	 */
	@XmlElement
	public Collection<Email> getEmail() {
		return email;
	}
	/**
	 * @param emails the emails to set
	 */
	public void setEmail(Collection<Email> email) {
		this.email = email;
	}
}
