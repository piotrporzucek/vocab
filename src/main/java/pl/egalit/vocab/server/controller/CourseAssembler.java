package pl.egalit.vocab.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.shared.CourseDto;

@Component
public class CourseAssembler {
	public CourseDto mapToDto(Course c) {
		CourseDto dto = new CourseDto();
		dto.setId(c.getId());
		dto.setName(c.getName());
		dto.setDescription(c.getDescription());
		dto.setStartDate(c.getStartDate());
		dto.setEndDate(c.getEndDate());
		dto.setPassword(c.getPassword());
		dto.setActive(c.isActive());
		return dto;
	}

	public List<CourseDto> mapToDtoList(List<Course> list) {
		List<CourseDto> result = new ArrayList<CourseDto>();
		for (Course c : list) {
			result.add(mapToDto(c));
		}
		return result;
	}
}
