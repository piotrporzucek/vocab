package pl.egalit.vocab.server.dao;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.entity.UserEntity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Component
public class Setup {

	@Autowired
	private SchoolDao schoolDao;

	private final String[] expressions = new String[] { "tak", "nie", "dobry",
			"ciekawy", "inny", "silny", "nowy", "stary", "pomocny", "gorzki",
			"luty" };
	private final String[] answers = new String[] { "yes", "no", "good",
			"interesting", "other", "strong", "new", "old", "helpful",
			"bitter", "february" };

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private WordDao wordDao;

	@Autowired
	private UserDao userDao;

	@PostConstruct
	public void initLoad() {
		School school1 = new School();
		school1.setName("OEI Wroclaw");
		school1.setCity("Wroclaw");
		school1.setId(1l);
		Key<School> schoolKey1 = schoolDao.save(school1);

		School school2 = new School();
		school2.setName("Cosmopolitan Opole");
		school2.setId(2l);
		school2.setCity("Wroclaw");
		Key<School> schoolKey2 = schoolDao.save(school2);

		UserEntity userEntity1 = new UserEntity();
		userEntity1.setEmail("a@a.pl");
		userEntity1.setPassword("a");
		userEntity1.setRole("ROLE_ADMIN");
		userEntity1.setSchool(schoolKey1);

		UserEntity userEntity2 = new UserEntity();
		userEntity2.setEmail("a@a.pl");
		userEntity2.setPassword("a");
		userEntity2.setRole("ROLE_ADMIN");
		userEntity2.setSchool(schoolKey2);

		UserEntity userEntity3 = new UserEntity();
		userEntity3.setEmail("b@b.pl");
		userEntity3.setPassword("b");
		userEntity3.setRole("ROLE_USER");
		userEntity3.setName("Jan Kowalski");
		userEntity3.setSchool(schoolKey1);

		Key<UserEntity> userKey1 = userDao.save(userEntity1);
		Key<UserEntity> userKey2 = userDao.save(userEntity2);
		Key<UserEntity> userKey3 = userDao.save(userEntity3);
		Set<Course> courses = new HashSet<Course>();
		for (int i = 1; i < 20; i++) {
			createCourse(schoolKey2, courses, i, "Cos");
			createCourse(schoolKey1, courses, i, "OEi");
			// List<Word> words = new ArrayList<Word>();
			// for (int j = 1; j < expressions.length; j++) {
			// Word word = new Word();
			// word.setId(1000 * i + new Long(j));
			// word.setAnswer(answers[j]);
			// word.setExpression(expressions[j]);
			// word.setExample("This is example usage of " + answers[j]);
			// words.add(word);
			// }
			// wordDao.saveAll(words, courseKey.getId());
		}
	}

	private Key<Course> createCourse(Key<School> schoolKey,
			Set<Course> courses, int i, String prefix) {
		Course course = new Course();
		course.setPassword("password" + i);
		course.setId(new Long(i));
		course.setSchool(Ref.create(schoolKey));
		course.setName(prefix + " Course " + i);
		course.setLastUpdateTime(System.currentTimeMillis());
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		if (i % 2 == 0) {
			endDate.add(Calendar.MONTH, 6);
			course.setActive(true);
		} else {
			startDate.add(Calendar.MONTH, -10);
			course.setActive(false);
			endDate.add(Calendar.MONTH, -6);
		}

		if (i % 3 == 0) {
			course.setLanguage(Locale.ENGLISH.getLanguage());
		} else {
			course.setLanguage(Locale.GERMAN.getLanguage());
		}
		course.setEndDate(endDate.getTime());
		course.setStartDate(startDate.getTime());
		courses.add(course);
		return courseDao.save(course);
	}

}
