package pl.egalit.vocab.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import pl.egalit.vocab.server.entity.RegistrationInfo;

@Repository
public class RegistrationInfoDao extends ObjectifyGenericDao<RegistrationInfo> {

	protected RegistrationInfoDao() {
		super(RegistrationInfo.class);

	}

	public RegistrationInfo getRegistration(String deviceRegistrationId) {
		return ofy().load().type(RegistrationInfo.class)
				.filter("deviceRegistrationId", deviceRegistrationId).first()
				.get();
	}

}
