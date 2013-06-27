package pl.egalit.vocab.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CoursePlace extends Place {
	/**
	 * Sample property (stores token).
	 */
	private final String courseIdStr;

	private TabPlace tabPlace;

	private Long courseId;

	public enum TabPlace {
		NEWWORDS, ARCHIVE;
	}

	public CoursePlace(String courseIdStr, String tabPlaceStr) {
		this.courseIdStr = courseIdStr;
		try {
			this.courseId = Long.parseLong(courseIdStr);
		} catch (RuntimeException rex) {
			this.courseId = -1l;
		}
		if (tabPlaceStr != null) {
			try {
				this.setTabPlace(TabPlace.values()[Integer
						.parseInt(tabPlaceStr)]);
			} catch (RuntimeException rex) {
				this.setTabPlace(TabPlace.NEWWORDS);
			}
		}

	}

	public CoursePlace(Long courseId, TabPlace tabPlace) {
		this.courseIdStr = courseId + "";
		this.courseId = courseId;
		this.tabPlace = tabPlace;
	}

	public CoursePlace(long courseId) {
		this(courseId, TabPlace.NEWWORDS);
	}

	public String getCourseIdStr() {
		return courseIdStr;
	}

	public boolean isValid() {
		return courseId != -1;
	}

	/**
	 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
	 */
	public static class Tokenizer implements PlaceTokenizer<CoursePlace> {

		@Override
		public String getToken(CoursePlace place) {
			StringBuilder sb = new StringBuilder();
			sb.append(place.getCourseIdStr());
			if (place.getTabPlace() != null) {
				sb.append(":" + place.getTabPlace().ordinal());
			}
			return sb.toString();
		}

		@Override
		public CoursePlace getPlace(String token) {
			String[] ids = token.split(":");
			return new CoursePlace(ids[0], ids.length > 1 ? ids[1] : null);
		}
	}

	public Long getCourseId() {
		return courseId;
	}

	public TabPlace getTabPlace() {
		return tabPlace;
	}

	public void setTabPlace(TabPlace tabPlace) {
		this.tabPlace = tabPlace;
	}

}
