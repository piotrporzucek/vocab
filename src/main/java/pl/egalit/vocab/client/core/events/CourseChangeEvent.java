package pl.egalit.vocab.client.core.events;

import pl.egalit.vocab.client.core.dto.CourseDto;

public class CourseChangeEvent {
	private final CourseDto course;

	public CourseChangeEvent(CourseDto course) {
		this.course = course;
	}

	public CourseDto getCourse() {
		return course;
	}
}
