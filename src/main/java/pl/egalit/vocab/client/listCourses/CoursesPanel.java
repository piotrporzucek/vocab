package pl.egalit.vocab.client.listCourses;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.egalit.vocab.client.core.mvp.VocabPlaceHistoryMapper;
import pl.egalit.vocab.client.entity.CourseProxy;
import pl.egalit.vocab.client.places.CoursePlace;

import com.google.common.collect.TreeMultimap;
import com.google.common.primitives.Ints;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;

public class CoursesPanel extends VerticalPanel {
	private final VocabPlaceHistoryMapper placeHistoryMapper;
	private TreeMultimap<Integer, CourseProxy> date2Courses;
	private HashMap<CourseProxy, Hyperlink> course2Link;
	private Long chosenCourseId;

	@Inject
	public CoursesPanel(VocabPlaceHistoryMapper placeHistoryMapper) {
		this.placeHistoryMapper = placeHistoryMapper;
		date2Courses = TreeMultimap.create(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Ints.compare(o2, o1);
			}
		}, new Comparator<CourseProxy>() {
			@Override
			public int compare(CourseProxy o1, CourseProxy o2) {
				return o1.getStartDate().before(o2.getStartDate()) ? -1 : 1;
			};
		});
		course2Link = new HashMap<CourseProxy, Hyperlink>();
	}

	void addCourse(CourseProxy course) {
		addCourse(course, course.getName());
	}

	void addCourse(CourseProxy course, String name) {
		Hyperlink link = new Hyperlink(name,
				placeHistoryMapper.getToken(new CoursePlace(course.getId())));
		if (course.getId().equals(chosenCourseId)) {
			link.setStylePrimaryName("linkcoursechosen");
		} else {
			link.setStylePrimaryName("linkcoursenormal");
		}
		course2Link.put(course, link);
		add(link);
	}

	void addCourses(List<CourseProxy> response) {
		date2Courses.clear();
		course2Link.clear();
		for (CourseProxy course : response) {
			Date startDate = course.getStartDate();
			Integer year = startDate.getYear() + 1900;
			date2Courses.put(year, course);
		}
		boolean manyYears = date2Courses.keySet().size() > 1;
		for (Integer y : date2Courses.keySet()) {
			if (manyYears) {
				add(new Label("" + y));
			}
			for (CourseProxy course : date2Courses.get(y)) {
				addCourse(course);
			}
		}
	}

	public void setChosenCourseId(Long chosenCourseId) {
		this.chosenCourseId = chosenCourseId;
		if (!date2Courses.isEmpty()) {
			for (Map.Entry<CourseProxy, Hyperlink> entry : course2Link
					.entrySet()) {
				Hyperlink link = entry.getValue();
				if (chosenCourseId != null
						&& entry.getKey().getId().equals(chosenCourseId)) {
					link.setStylePrimaryName("linkcoursechosen");
				} else {
					link.setStylePrimaryName("linkcoursenormal");
				}
			}

		}
	}
}
