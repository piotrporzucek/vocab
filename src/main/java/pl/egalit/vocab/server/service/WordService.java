package pl.egalit.vocab.server.service;

import java.util.List;

import pl.egalit.vocab.server.entity.Word;

public interface WordService {

	void saveAndSend(List<Word> words, long courseId);

	List<Word> getWordsForWordsUnit(long courseId, long wordsUnitId);

}
