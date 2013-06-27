package pl.egalit.vocab.server.service;

import java.util.List;

import pl.egalit.vocab.client.core.exceptions.CourseExistsException;
import pl.egalit.vocab.client.core.exceptions.CourseNotExistsException;
import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.WordsUnit;

public interface CourseService {
	public List<Course> getActiveCourses(Long schoolId);

	public List<Course> getActiveCourses();

	public List<Course> getArchiveCourses();

	public List<Course> getActiveCourses(Long schoolId, long lastUpdateTime);

	public List<Course> getArchiveCourses(Long schoolId);

	public Course findCourse(long id);

	List<WordsUnit> getWordsUnits(Long courseId);

	List<Course> getAllCourses();

	void save(Course course) throws CourseNotExistsException,
			CourseExistsException;

}
