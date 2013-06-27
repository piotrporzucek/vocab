package pl.egalit.vocab.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.entity.WordsUnit;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Ref;

@Repository
public class WordsUnitDao extends ObjectifyGenericDao<WordsUnit> {

	protected WordsUnitDao() {
		super(WordsUnit.class);
	}

	public List<WordsUnit> getWordsUnit(long schoolId, Long courseId) {
		List<WordsUnit> result = null;
		Key<School> schoolKey = ofy().load().type(School.class).id(schoolId)
				.key();
		Key<Course> courseKey = Key.create(schoolKey, Course.class, courseId);
		try {

			result = ofy().load().type(WordsUnit.class).ancestor(courseKey)
					.order("addedDate").list();

		} catch (NotFoundException e) {
			return new ArrayList<WordsUnit>();
		}
		return result;
	}

	public Ref<WordsUnit> saveWordsUnit(long schoolId, long courseId,
			String lector) {
		WordsUnit wordsUnit = new WordsUnit();
		wordsUnit.setAddedDate(Calendar.getInstance().getTime());
		wordsUnit.setLector(lector);
		Key<School> schoolKey = ofy().load().type(School.class).id(schoolId)
				.key();
		Key<Course> courseKey = Key.create(schoolKey, Course.class, courseId);
		wordsUnit.setCourse(courseKey);
		Key<WordsUnit> wordsUnitKey = ofy().save().entity(wordsUnit).now();
		Ref<WordsUnit> wordsUnitRef = ofy().load().key(wordsUnitKey);
		return wordsUnitRef;
	}

}
