package pl.egalit.vocab.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import pl.egalit.vocab.server.dao.WordDao;
import pl.egalit.vocab.server.dao.WordsUnitDao;
import pl.egalit.vocab.server.entity.Word;
import pl.egalit.vocab.server.entity.WordsUnit;
import pl.egalit.vocab.server.security.GwtMethod;
import pl.egalit.vocab.server.security.VocabUser;
import pl.egalit.vocab.server.service.WordService;

import com.googlecode.objectify.Ref;

@Service
public class WordServiceImpl extends AbstractService implements WordService {

	@Autowired
	private WordDao wordDao;

	@Autowired
	private WordsUnitDao wordsUnitDao;

	@Override
	@GwtMethod
	@Secured("ROLE_USER")
	public void saveAndSend(List<Word> words, long courseId) {
		VocabUser vocabUser = getVocabUser();
		Ref<WordsUnit> wordsUnitRef = wordsUnitDao.saveWordsUnit(
				vocabUser.getSchoolId(), courseId, vocabUser.getUsername());
		wordDao.saveAll(words, wordsUnitRef);
	}

	@Override
	@GwtMethod
	@Secured("ROLE_USER")
	public List<Word> getWordsForWordsUnit(long courseId, long wordsUnitId) {
		VocabUser vocabUser = getVocabUser();

		return wordDao.getWordsForWordsUnit(vocabUser.getSchoolId(), courseId,
				wordsUnitId);
	}

}
