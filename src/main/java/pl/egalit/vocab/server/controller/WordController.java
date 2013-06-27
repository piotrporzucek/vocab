package pl.egalit.vocab.server.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.egalit.vocab.server.dao.WordDao;
import pl.egalit.vocab.server.entity.Word;
import pl.egalit.vocab.shared.ListWordsDto;

@Controller
public class WordController {

	@Autowired
	private WordAssembler wordAssembler;

	@Autowired
	private WordDao wordDao;

	@PostConstruct
	public void init() {

	}

	@RequestMapping(value = { "/school/{schoolId}/course/{courseId}/words/{lastUpdateTime}" }, method = RequestMethod.GET)
	public @ResponseBody
	ListWordsDto getWords(@PathVariable long schoolId,
			@PathVariable long courseId, @PathVariable long lastUpdateTime) {
		List<Word> words = wordDao.getAllWords(schoolId, courseId,
				lastUpdateTime);
		return new ListWordsDto(wordAssembler.mapToDtoList(words));
	}

}
