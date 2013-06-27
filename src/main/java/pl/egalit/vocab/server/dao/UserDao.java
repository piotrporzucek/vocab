package pl.egalit.vocab.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.entity.UserEntity;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;

@Repository
public class UserDao extends ObjectifyGenericDao<UserEntity> {

	protected UserDao() {
		super(UserEntity.class);

	}

	public UserEntity getUser(String email, Long schoolId) {
		Key<School> schoolKey = Key.create(School.class, schoolId);

		UserEntity user = ofy().load().type(UserEntity.class)
				.filter("email =", email).ancestor(schoolKey).first().get();
		return user;
	}

	public UserEntity getUser(String email, Key<School> schoolKey) {
		UserEntity user = ofy().load().type(UserEntity.class)
				.filter("email =", email).ancestor(schoolKey).first().get();
		return user;
	}

	public List<UserEntity> getUser(String email) {
		return ofy().load().type(UserEntity.class).filter("email =", email)
				.list();

	}

	public List<UserEntity> getUsers(Long schoolId) {
		Key<School> schoolKey = Key.create(School.class, schoolId);

		return ofy().load().type(UserEntity.class).ancestor(schoolKey).list();

	}

	public UserEntity getUser(Long userId, Long schoolId, boolean loadCourses) {
		Key<School> schoolKey = Key.create(School.class, schoolId);
		Key<UserEntity> userKey = Key.create(schoolKey, UserEntity.class,
				userId);
		UserEntity user = ofy().load().type(UserEntity.class)
				.filterKey(userKey).first().get();
		if (loadCourses && user != null && user.getCourses() != null) {
			Collection<Course> coursesOfUser = ofy().load()
					.keys(user.getCourses()).values();
			user.setCourseEntities(Lists.newArrayList(coursesOfUser));
		}
		return user;

	}

	public void modifyUser(UserEntity user, Set<Long> courseIds) {
		Map<Key<Course>, Course> keyCourses2Courses = ofy().load().keys(
				user.getCourses());
		user.setCourses(keyCourses2Courses.keySet());
		ofy().save().entity(user);
	}

	public void removeUser(long userId, Long schoolId) {
		Key<School> schoolKey = Key.create(School.class, schoolId);
		Key<UserEntity> userKey = Key.create(schoolKey, UserEntity.class,
				userId);
		ofy().delete().key(userKey).now();
	}
}
