package com.mee.sangsil.sample.jaxbcontext;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddressBook {

	private Collection<User> user;

	public AddressBook() {
		user = new ArrayList<User>();
	}

	/**
	 * @return the user
	 */
	@XmlElement
	public Collection<User> getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Collection<User> user) {
		this.user = user;
	}
	
	
}
