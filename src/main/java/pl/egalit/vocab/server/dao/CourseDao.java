package pl.egalit.vocab.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.School;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;

@Repository
public class CourseDao extends ObjectifyGenericDao<Course> {

	@Autowired
	private SchoolDao schoolDao;

	protected CourseDao() {
		super(Course.class);
	}

	public List<Course> getActiveCourses(Long schoolId, long lastUpdateTime) {
		List<Course> result = Lists.newArrayList();

		try {
			Key<School> schoolKey = Key.create(School.class, schoolId);
			Preconditions.checkNotNull(schoolKey);
			result.addAll(ofy().load().type(Course.class)
					.filter("lastUpdateTime >=", lastUpdateTime)
					.filter("active ==", true).ancestor(schoolKey).list());
		} catch (NotFoundException e) {

		}

		return result;
	}

	public void removeAll() {
		// deleteAll(getAllCourses("OEI-Wro", 0));
	}

	private List<Course> getCourses(Long schoolId, boolean active) {
		List<Course> result = null;
		try {
			try {
				result = ofy()
						.load()
						.type(Course.class)
						.filter("active", active)
						.ancestor(
								schoolDao.get(Key
										.create(School.class, schoolId)))

						.list();
			} catch (EntityNotFoundException e) {
				return new ArrayList<Course>();
			}
		} catch (NotFoundException e) {
			return new ArrayList<Course>();
		}
		return result;
	}

	private List<Course> getCourses(Set<Key<Course>> coursesKeys, boolean active) {
		List<Course> result = Lists.newArrayList();

		Map<Key<Course>, Course> id2Course = ofy().load().keys(coursesKeys);
		for (Map.Entry<Key<Course>, Course> entry : id2Course.entrySet()) {
			if (entry.getValue().isActive() == active) {
				result.add(entry.getValue());
			}
		}
		return result;
	}

	public List<Course> getActiveCourses(Long schoolId) {
		return getCourses(schoolId, true);
	}

	public List<Course> getInactiveCourses(Long schoolId) {
		return getCourses(schoolId, false);
	}

	public Course findCourse(long courseId, long schoolId) {
		try {
			List<Course> courses = ofy()
					.load()
					.type(Course.class)
					.ancestor(schoolDao.get(Key.create(School.class, schoolId)))
					.list();

			for (Course course : courses) {
				if (courseId == course.getId()) {
					return course;
				}
			}
		} catch (EntityNotFoundException e) {
			return null;
		}
		return null;
	}

	public List<Course> getActiveCourses(Set<Key<Course>> coursesKeys) {
		return getCourses(coursesKeys, true);
	}

	public List<Course> getArchiveCourses(Set<Key<Course>> coursesKeys) {
		return getCourses(coursesKeys, false);
	}

	public List<Course> getCourses(Long schoolId) {
		List<Course> result = null;
		try {
			try {
				result = ofy()
						.load()
						.type(Course.class)
						.ancestor(
								schoolDao.get(Key
										.create(School.class, schoolId)))

						.list();
			} catch (EntityNotFoundException e) {
				return new ArrayList<Course>();
			}
		} catch (NotFoundException e) {
			return new ArrayList<Course>();
		}
		return result;
	}

	public Course findCourse(String name, Key<School> schoolKey) {
		return ofy().load().type(Course.class).ancestor(schoolKey)
				.filter("name", name).first().get();
	}

	public List<Course> getArchiveCourses(Long schoolId, long lastUpdateTime) {
		List<Course> result = null;
		try {
			try {
				result = ofy()
						.load()
						.type(Course.class)
						.filter("lastUpdateTime >=", lastUpdateTime)
						.filter("active ==", false)
						.ancestor(
								schoolDao.get(Key
										.create(School.class, schoolId)))
						.list();
			} catch (EntityNotFoundException e) {
				return new ArrayList<Course>();
			}
		} catch (NotFoundException e) {
			return new ArrayList<Course>();
		}
		return result;
	}
}
