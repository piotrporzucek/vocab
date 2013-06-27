package pl.egalit.vocab.server.service.impl;

import pl.egalit.vocab.server.service.UserService;

public class UserServiceLocator extends AbstractServiceLocator<UserService> {

	@Override
	public Class<UserService> getServiceClass() {
		return UserService.class;
	}

}
