package pl.egalit.vocab.client.core.widget;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import pl.egalit.vocab.client.core.dto.CourseDto;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gwt.user.client.ui.ListBox;

public class MultiselectionListBox extends ListBox {

	private Map<Integer, CourseDto> name2Course;
	private Set<CourseDto> selectedItems = Sets.newHashSet();

	public LinkedList<CourseDto> getSelectedItems() {
		LinkedList<CourseDto> selectedItems = new LinkedList<CourseDto>();
		for (int i = 0; i < getItemCount(); i++) {
			if (isItemSelected(i)) {
				selectedItems.add(name2Course.get(i));
			}
		}
		return selectedItems;
	}

	public void setCourses(Collection<CourseDto> courses) {
		name2Course = Maps.newHashMap();
		int idx = 0;
		for (CourseDto course : courses) {
			addItem(course.getName());
			if (isCourseSelected(course)) {
				setItemSelected(idx, true);
			}
			name2Course.put(idx++, course);
		}
	}

	private boolean isCourseSelected(CourseDto course) {
		for (CourseDto selectedCourse : selectedItems) {
			if (selectedCourse.getId() == course.getId()) {
				return true;
			}
		}
		return false;
	}

	public void setSeletectedItems(Set<CourseDto> courses) {
		this.selectedItems = courses;
		if (name2Course != null) {
			setCourses(name2Course.values());
		}
	}

}
