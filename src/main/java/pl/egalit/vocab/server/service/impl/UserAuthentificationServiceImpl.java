package pl.egalit.vocab.server.service.impl;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pl.egalit.vocab.client.core.exceptions.UserNotExistsException;
import pl.egalit.vocab.server.dao.SchoolDao;
import pl.egalit.vocab.server.dao.UserDao;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.request.LoginRequest;
import pl.egalit.vocab.server.security.GwtMethod;
import pl.egalit.vocab.server.security.VocabUser;
import pl.egalit.vocab.server.service.UserAuthenticationService;

@Service
public class UserAuthentificationServiceImpl extends AbstractService implements
		UserAuthenticationService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	@GwtMethod
	public Boolean isUserAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		boolean result = authentication != null
				&& authentication.isAuthenticated()
				&& hasRole(authentication.getAuthorities(), "ROLE_USER",
						"ROLE_ADMIN");
		// if (!result) {
		// LoginRequest request = new LoginRequest();
		// request.setUsername("a@a.pl");
		// request.setPassword("a");
		// request.setSchoolId(2l);
		// login(request);
		// return true;
		// }
		return result;

	}

	@Override
	@GwtMethod
	public Boolean isCurrentUserAdmin() {
		VocabUser user = getVocabUser();
		return user != null && hasRole(user.getAuthorities(), "ROLE_ADMIN");
	}

	@GwtMethod
	@Override
	public void login(final LoginRequest loginRequest) {

		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		if (username == null) {
			username = "";
		}
		if (password == null) {
			password = "";
		}
		username = username.trim();
		password = password.trim();
		AbstractAuthenticationToken authRequest = new VocabUsernamePasswordAuthenticationToken(
				username, password, loginRequest.getSchoolId());

		Authentication result = this.authenticationManager
				.authenticate(authRequest);

		SecurityContextHolder.getContext().setAuthentication(result);
	}

	@GwtMethod
	@Secured("ROLE_USER")
	@Override
	public String getUsername() {
		return getVocabUser().getUsername();
	}

	@Override
	@GwtMethod
	public Set<School> getSchoolsForUser(String username) {
		SortedSet<School> schools = new TreeSet<School>(
				new Comparator<School>() {
					@Override
					public int compare(School o1, School o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
		schools.addAll(schoolDao.getSchoolsForUser(username));
		return schools;

	}

	@GwtMethod
	@Override
	public void sendPassword(String email) throws UserNotExistsException {
		if (userDao.getUser(email).isEmpty()) {
			throw new UserNotExistsException(email);
		}
	}

}
