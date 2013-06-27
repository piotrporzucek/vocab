package pl.egalit.vocab.server.service;

import pl.egalit.vocab.server.entity.RegistrationInfo;

public interface RegistrationInfoService {
	public void register(RegistrationInfo registrationInfo);

	public void unregister(RegistrationInfo registrationInfo);

}
