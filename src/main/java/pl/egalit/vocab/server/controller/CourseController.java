package pl.egalit.vocab.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.egalit.vocab.server.dao.CourseDao;
import pl.egalit.vocab.server.dao.WordDao;
import pl.egalit.vocab.server.entity.Course;
import pl.egalit.vocab.shared.ListCoursesDto;

@Controller
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseAssembler courseAssembler;

	@Autowired
	private WordDao wordDao;

	@RequestMapping(value = { "/{schoolId}/{lastUpdateTime}" }, method = RequestMethod.GET)
	public @ResponseBody
	ListCoursesDto getCourses(@PathVariable Long schoolId,
			@PathVariable long lastUpdateTime) {
		List<Course> activeCourses = courseDao.getActiveCourses(schoolId,
				lastUpdateTime);
		List<Course> archiveCoursesModifiedAfterLastUpdateTime = courseDao
				.getArchiveCourses(schoolId, lastUpdateTime);
		return new ListCoursesDto(
				courseAssembler.mapToDtoList(activeCourses),
				courseAssembler
						.mapToDtoList(archiveCoursesModifiedAfterLastUpdateTime));
	}

}
