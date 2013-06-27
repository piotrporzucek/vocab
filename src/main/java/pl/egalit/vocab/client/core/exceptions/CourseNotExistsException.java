package pl.egalit.vocab.client.core.exceptions;

import java.io.Serializable;

public class CourseNotExistsException extends Exception implements Serializable {

	private Long courseId;

	public CourseNotExistsException(Long id) {
		this.courseId = id;
	}

	public CourseNotExistsException() {

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5343371049477076156L;

	public Long getCourseId() {
		return courseId;
	}

}
