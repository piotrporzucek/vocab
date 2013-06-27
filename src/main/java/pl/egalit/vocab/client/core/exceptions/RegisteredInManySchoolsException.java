package pl.egalit.vocab.client.core.exceptions;

import java.util.List;

public class RegisteredInManySchoolsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6050625892172692282L;
	private List<Long> schoolIds;

	public RegisteredInManySchoolsException(List<Long> schoolIds) {
		this.schoolIds = schoolIds;
	}

	public RegisteredInManySchoolsException() {

	}

	public List<Long> getSchoolIds() {
		return schoolIds;
	}

}
