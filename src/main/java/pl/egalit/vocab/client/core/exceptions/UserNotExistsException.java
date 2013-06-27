package pl.egalit.vocab.client.core.exceptions;

import java.io.Serializable;

public class UserNotExistsException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;

	public UserNotExistsException(String email) {
		this.email = email;
	}

	public UserNotExistsException() {
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

}
