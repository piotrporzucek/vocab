package pl.egalit.vocab.server.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.egalit.vocab.server.entity.Word;
import pl.egalit.vocab.shared.WordDto;

@Component
public class WordAssembler {
	public WordDto mapToDto(Word w) {
		WordDto dto = new WordDto();
		dto.setId(w.getId());
		dto.setAnswer(w.getAnswer());
		dto.setExpression(w.getExpression());
		dto.setNextShownOn(Calendar.getInstance().getTime());
		dto.setCourseId(w.getWordsUnit().getValue().getCourse().getId());
		dto.setExample(w.getExample());
		return dto;
	}

	public List<WordDto> mapToDtoList(List<Word> list) {
		List<WordDto> result = new ArrayList<WordDto>();
		for (Word c : list) {
			result.add(mapToDto(c));
		}
		return result;
	}
}
