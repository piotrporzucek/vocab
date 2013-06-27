package pl.egalit.vocab.shared;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ListCoursesDto implements Serializable, HasCollection {

	private List<CourseDto> activeCourses;

	private List<CourseDto> archiveCoursesFromLastUpdate;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6171647855554796694L;

	public ListCoursesDto() {

	}

	public ListCoursesDto(List<CourseDto> activeCourses,
			List<CourseDto> archiveCoursesFromLastUpdate) {
		this.setActiveCourses(activeCourses);
		this.setArchiveCoursesFromLastUpdate(archiveCoursesFromLastUpdate);
	}

	public List<CourseDto> getArchiveCoursesFromLastUpdate() {
		return archiveCoursesFromLastUpdate;
	}

	public void setArchiveCoursesFromLastUpdate(
			List<CourseDto> archiveCoursesFromLastUpdate) {
		this.archiveCoursesFromLastUpdate = archiveCoursesFromLastUpdate;
	}

	public List<CourseDto> getActiveCourses() {
		return activeCourses;
	}

	public void setActiveCourses(List<CourseDto> activeCourses) {
		this.activeCourses = activeCourses;
	}

	@Override
	@JsonIgnore
	public boolean isEmpty() {
		return (activeCourses == null || activeCourses.isEmpty())
				&& (archiveCoursesFromLastUpdate == null || archiveCoursesFromLastUpdate
						.isEmpty());
	}

}
