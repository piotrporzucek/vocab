package pl.egalit.vocab.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.server.entity.Word;
import pl.egalit.vocab.server.entity.WordsUnit;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Repository
public class WordDao extends ObjectifyGenericDao<Word> {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private WordsUnitDao wordsUnitDao;

	protected WordDao() {
		super(Word.class);
	}

	public void removeAll() {
		// Collection<Word> words = ofy().query(Word.class).list();
		// deleteAll(words);
	}

	public List<Word> getAllWords(long schoolId, long courseId,
			long lastUpdateTime) {

		List<WordsUnit> wordsUnits = wordsUnitDao.getWordsUnit(schoolId,
				courseId);
		List<Word> result = Lists.newArrayList();
		for (WordsUnit wordsUnit : wordsUnits) {
			result.addAll(ofy().load().type(Word.class)
					.filter("lastUpdateTime >=", lastUpdateTime)
					.ancestor(wordsUnit).list());
		}
		return result;
	}

	public void saveAll(List<Word> words, Ref<WordsUnit> wordsUnitRef) {
		long saveTimeInMilis = Calendar.getInstance().getTimeInMillis();
		for (Word word : words) {
			word.setWordsUnit(wordsUnitRef);
			word.setLastUpdateTime(saveTimeInMilis);
		}

		saveAll(words);
	}

	public List<Word> getWordsForWordsUnit(long schoolId, long courseId,
			long wordsUnitId) {
		List<Word> result = Lists.newArrayList();

		Key<School> schoolKey = ofy().load().type(School.class).id(schoolId)
				.key();
		Key<Course> courseKey = Key.create(schoolKey, Course.class, courseId);
		Key<WordsUnit> wordsUnitKey = Key.create(courseKey, WordsUnit.class,
				wordsUnitId);

		result = ofy().load().type(Word.class).ancestor(wordsUnitKey).list();

		return result;

	}

}
