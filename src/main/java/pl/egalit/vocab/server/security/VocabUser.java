package pl.egalit.vocab.server.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import pl.egalit.vocab.server.entity.School;

import com.googlecode.objectify.Key;

public class VocabUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long schoolId;

	private Long id;

	public VocabUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities, Long schoolId,
			Long id) {
		super(username, password, authorities);
		this.schoolId = schoolId;
		this.id = id;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<School> getSchoolKey() {
		return Key.create(School.class, schoolId);
	}

}
