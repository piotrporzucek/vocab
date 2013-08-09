package pl.egalit.vocab.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.shared.SchoolDto;

@Component
public class SchoolAssembler {
	public SchoolDto mapToDto(School school) {
		SchoolDto dto = new SchoolDto();
		dto.setId(school.getId());
		dto.setName(school.getName());
		dto.setCity(school.getCity());
		return dto;
	}

	public List<SchoolDto> mapToDtoList(List<School> list) {
		List<SchoolDto> result = new ArrayList<SchoolDto>();
		for (School s : list) {
			result.add(mapToDto(s));
		}
		return result;
	}
}
