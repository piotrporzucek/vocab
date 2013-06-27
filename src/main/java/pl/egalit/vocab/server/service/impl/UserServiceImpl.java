package pl.egalit.vocab.server.service.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import pl.egalit.vocab.client.core.exceptions.UserExistsException;
import pl.egalit.vocab.server.dao.UserDao;
import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.entity.UserEntity;
import pl.egalit.vocab.server.security.GwtMethod;
import pl.egalit.vocab.server.security.VocabUser;
import pl.egalit.vocab.server.service.UserService;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.googlecode.objectify.Key;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<UserEntity> getUser(String email) {

		return userDao.getUser(email);
	}

	@Override
	@GwtMethod
	@Secured("ROLE_ADMIN")
	public List<UserEntity> getUsers() {
		VocabUser vocabUser = getVocabUser();
		return userDao.getUsers(vocabUser.getSchoolId());
	}

	@Override
	@GwtMethod
	@Secured("ROLE_ADMIN")
	public void save(UserEntity user, Set<Long> courseIds)
			throws UserExistsException {
		VocabUser vocabUser = getVocabUser();

		if (!userExists(user.getEmail(), vocabUser.getSchoolId())) {
			user.setRole("ROLE_USER");
			Key<School> schoolKey = ofy().load().type(School.class)
					.id(vocabUser.getSchoolId()).key();
			user.setCourses(buildCoursesKeys(courseIds, schoolKey));
			user.setSchool(schoolKey);
			userDao.save(user);
		} else {
			throw new UserExistsException();
		}

	}

	@Override
	@GwtMethod
	@Secured("ROLE_ADMIN")
	public void modify(UserEntity user, Set<Long> courseIds)
			throws UserExistsException {
		VocabUser vocabUser = getVocabUser();
		Key<School> schoolKey = ofy().load().type(School.class)
				.id(vocabUser.getSchoolId()).key();
		user.setSchool(schoolKey);
		UserEntity oldUserEntity = userDao.getUser(user.getId(),
				user.getSchoolId(), false);
		if (!Objects.equal(oldUserEntity.getEmail(), user.getEmail())
				&& userExists(user.getEmail(), vocabUser.getSchoolId())) {
			throw new UserExistsException();
		}
		user.setCourses(buildCoursesKeys(courseIds, schoolKey));
		user.setRole(oldUserEntity.getRole());
		userDao.modifyUser(user, courseIds);

	}

	@Override
	@GwtMethod
	@Secured("ROLE_ADMIN")
	public UserEntity findUserFromCurrentSchool(Long userId) {
		VocabUser vocabUser = getVocabUser();
		UserEntity userEntity = userDao.getUser(userId,
				vocabUser.getSchoolId(), true);
		if (userEntity != null && userEntity.getCourses() != null) {
			userEntity.setCourseEntities(Lists.newArrayList(ofy().load()
					.keys(userEntity.getCourses()).values()));
		}
		return userEntity;

	}

	@Override
	@GwtMethod
	@Secured("ROLE_USER")
	public UserEntity getCurrentUserFromCurrentSchool() {
		VocabUser vocabUser = getVocabUser();
		UserEntity userEntity = userDao.getUser(vocabUser.getId(),
				vocabUser.getSchoolId(), true);
		if (userEntity != null && userEntity.getCourses() != null) {
			userEntity.setCourseEntities(Lists.newArrayList(ofy().load()
					.keys(userEntity.getCourses()).values()));
		}
		return userEntity;

	}

	@Override
	@GwtMethod
	@Secured("ROLE_ADMIN")
	public void removeUser(long userId) {
		userDao.removeUser(userId, getVocabUser().getSchoolId());
	}

	private Set<Key<Course>> buildCoursesKeys(Set<Long> courseIds,
			Key<School> schoolKey) {
		Set<Key<Course>> coursesKeys = Sets.newHashSet();
		for (Long courseId : courseIds) {
			Key<Course> courseKey = Key.create(schoolKey, Course.class,
					courseId);
			coursesKeys.add(courseKey);
		}
		return coursesKeys;
	}

	private boolean userExists(String email, Long schoolId) {
		return userDao.getUser(email, schoolId) != null;
	}

}
