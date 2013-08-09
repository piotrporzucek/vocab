package pl.egalit.vocab.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.egalit.vocab.server.entity.Multicast;

import com.googlecode.objectify.Key;

@Repository
public class MulticastDao extends ObjectifyGenericDao<Multicast> {

	@Autowired
	private RegistrationInfoDao registrationInfoDao;

	protected MulticastDao() {
		super(Multicast.class);
	}

	public void updateMulticast(long multicastId, List<String> retriableRegIds) {

		Multicast multicast = getById(multicastId);
		multicast.setRegIds(retriableRegIds);
		save(multicast);
	}

	public Multicast getById(long multicastId) {
		Key<Multicast> key = Key.create(Multicast.class, multicastId);
		Multicast multicast = ofy().load().key(key).get();
		return multicast;
	}

}
