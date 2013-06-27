package pl.egalit.vocab.server.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import pl.egalit.vocab.client.core.exceptions.CourseExistsException;
import pl.egalit.vocab.client.core.exceptions.CourseNotExistsException;
import pl.egalit.vocab.server.dao.CourseDao;
import pl.egalit.vocab.server.dao.SchoolDao;
import pl.egalit.vocab.server.dao.UserDao;
import pl.egalit.vocab.server.dao.WordsUnitDao;
import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.UserEntity;
import pl.egalit.vocab.server.entity.WordsUnit;
import pl.egalit.vocab.server.security.GwtMethod;
import pl.egalit.vocab.server.security.VocabUser;
import pl.egalit.vocab.server.service.CourseService;

import com.google.common.collect.Lists;

@Service
public class CourseServiceImpl extends AbstractService implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private WordsUnitDao wordsUnitDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SchoolDao schoolDao;

	@Override
	@Secured("ROLE_USER")
	public List<Course> getActiveCourses(Long schoolId) {
		return courseDao.getActiveCourses(schoolId);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public void save(Course course) throws CourseNotExistsException,
			CourseExistsException {

		Course oldCourse = null;
		if (course.getName() != null) {
			Course existingCourse = courseDao.findCourse(course.getName(),
					getVocabUser().getSchoolKey());
			if (existingCourse != null
					&& existingCourse.getId() != course.getId()) {
				throw new CourseExistsException();
			}
		}
		if (course.getId() == null) {
			oldCourse = new Course();
		} else {
			oldCourse = findCourse(course.getId());
		}
		if (oldCourse == null) {
			throw new CourseNotExistsException(course.getId());
		}

		oldCourse.setActive(course.isActive());
		oldCourse.setId(course.getId());
		oldCourse.setDescription(course.getDescription());
		oldCourse.setName(course.getName());
		oldCourse.setStartDate(course.getStartDate());
		oldCourse.setEndDate(course.getEndDate());
		oldCourse.setPassword(course.getPassword());
		oldCourse.setLastUpdateTime(Calendar.getInstance().getTimeInMillis());
		oldCourse
				.setSchool(schoolDao.findSchool(getVocabUser().getSchoolKey()));
		courseDao.save(oldCourse);

	}

	@Override
	@Secured("ROLE_USER")
	public List<Course> getAllCourses() {
		return courseDao.getCourses(getVocabUser().getSchoolId());
	}

	@Override
	@Secured("ROLE_USER")
	public List<Course> getActiveCourses(Long schoolId, long lastUpdateTime) {
		return courseDao.getActiveCourses(schoolId, lastUpdateTime);
	}

	@Override
	@Secured("ROLE_USER")
	public List<Course> getArchiveCourses(Long schoolId) {
		return courseDao.getInactiveCourses(schoolId);
	}

	@Override
	@Secured("ROLE_USER")
	@GwtMethod
	public List<Course> getActiveCourses() {
		VocabUser vocabUser = getVocabUser();

		if (hasRole(vocabUser.getAuthorities(), "ROLE_ADMIN")) {
			return getActiveCourses(vocabUser, true);
		} else {
			return getActiveCourses(vocabUser, false);
		}

	}

	private List<Course> getActiveCourses(VocabUser vocabUser, boolean forAdmin) {
		if (forAdmin) {
			return getActiveCourses(vocabUser.getSchoolId());
		} else {
			UserEntity userEntity = userDao.getUser(vocabUser.getUsername(),
					vocabUser.getSchoolId());
			if (userEntity.getCourses() != null) {
				return courseDao.getActiveCourses(userEntity.getCourses());
			} else {
				return Lists.newArrayList();
			}

		}
	}

	private List<Course> getArchiveCourses(VocabUser vocabUser, boolean forAdmin) {
		if (forAdmin) {
			return getArchiveCourses(vocabUser.getSchoolId());
		} else {
			UserEntity userEntity = userDao.getUser(vocabUser.getUsername(),
					vocabUser.getSchoolId());
			if (userEntity.getCourses() != null) {
				return courseDao.getArchiveCourses(userEntity.getCourses());
			} else {
				return Lists.newArrayList();
			}
		}
	}

	@Override
	@Secured("ROLE_USER")
	@GwtMethod
	public List<Course> getArchiveCourses() {
		VocabUser vocabUser = getVocabUser();
		if (hasRole(vocabUser.getAuthorities(), "ROLE_ADMIN")) {
			return getArchiveCourses(vocabUser, true);
		} else {
			return getArchiveCourses(vocabUser, false);
		}
	}

	@Override
	@Secured("ROLE_USER")
	@GwtMethod
	public Course findCourse(long id) {
		VocabUser vocabUser = getVocabUser();
		return courseDao.findCourse(id, vocabUser.getSchoolId());
	}

	@Override
	@Secured("ROLE_USER")
	public List<WordsUnit> getWordsUnits(Long courseId) {
		VocabUser vocabUser = getVocabUser();
		return wordsUnitDao.getWordsUnit(vocabUser.getSchoolId(), courseId);
	}

}
