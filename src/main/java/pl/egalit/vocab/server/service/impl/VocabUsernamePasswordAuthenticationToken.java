package pl.egalit.vocab.server.service.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class VocabUsernamePasswordAuthenticationToken extends
		UsernamePasswordAuthenticationToken {

	/**
	 * UUID.
	 */
	private static final long serialVersionUID = 1L;
	private final Long schoolId;

	public VocabUsernamePasswordAuthenticationToken(String username,
			String password, Long schoolId) {
		super(username, password);
		this.schoolId = schoolId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

}
