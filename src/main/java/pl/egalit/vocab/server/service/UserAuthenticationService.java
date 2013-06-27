package pl.egalit.vocab.server.service;

import java.util.Set;

import pl.egalit.vocab.client.core.exceptions.UserNotExistsException;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.request.LoginRequest;

public interface UserAuthenticationService {
	Boolean isUserAuthenticated();

	void login(LoginRequest loginRequest);

	String getUsername();

	Set<School> getSchoolsForUser(String username);

	public Boolean isCurrentUserAdmin();

	void sendPassword(String email) throws UserNotExistsException;
}
