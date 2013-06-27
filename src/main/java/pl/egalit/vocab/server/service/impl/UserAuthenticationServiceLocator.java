package pl.egalit.vocab.server.service.impl;

import pl.egalit.vocab.server.service.UserAuthenticationService;

public class UserAuthenticationServiceLocator extends
		AbstractServiceLocator<UserAuthenticationService> {

	@Override
	public Class<UserAuthenticationService> getServiceClass() {
		return UserAuthenticationService.class;
	}

}
