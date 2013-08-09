package pl.egalit.vocab.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.egalit.vocab.server.dao.SchoolDao;
import pl.egalit.vocab.server.dao.WordDao;
import pl.egalit.vocab.server.entity.School;
import pl.egalit.vocab.shared.ListSchoolsDto;

@Controller
@RequestMapping("/schools")
public class SchoolController {

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private SchoolAssembler schoolAssembler;

	@Autowired
	private WordDao wordDao;

	@RequestMapping(value = { "/{lastUpdateTime}" }, method = RequestMethod.GET)
	public @ResponseBody
	ListSchoolsDto getCourses(@PathVariable long lastUpdateTime) {

		List<School> schools = schoolDao.getSchools(lastUpdateTime);
		return new ListSchoolsDto(schoolAssembler.mapToDtoList(schools));
	}
}
