package pl.egalit.vocab.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.entity.UserEntity;

import com.google.common.collect.Sets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Repository
public class SchoolDao extends ObjectifyGenericDao<School> {

	protected SchoolDao() {
		super(School.class);
	}

	public Set<School> getSchoolsForUser(String username) {
		Set<School> result = Sets.newHashSet();
		List<UserEntity> users = ofy().load().type(UserEntity.class)
				.filter("email", username).list();
		for (UserEntity user : users) {
			result.add(ofy().load().key(user.getSchool()).get());
		}
		return result;
	}

	public Ref<School> findSchool(Key<School> schoolKey) {
		return ofy().load().key(schoolKey);
	}

}
