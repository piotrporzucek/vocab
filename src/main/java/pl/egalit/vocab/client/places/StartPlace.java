package pl.egalit.vocab.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class StartPlace extends Place {
	/**
	 * Sample property (stores token).
	 */
	private String courseIdStr;
	private long courseId;

	public StartPlace(String courseIdStr) {
		this.courseIdStr = courseIdStr;
		try {
			this.courseId = Long.parseLong(courseIdStr);
		} catch (RuntimeException rex) {
			this.courseId = -1;
		}
	}

	public StartPlace(long courseId) {
		this.courseIdStr = courseId + "";
		this.courseId = courseId;
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
	public static class Tokenizer implements PlaceTokenizer<StartPlace> {

		@Override
		public String getToken(StartPlace place) {
			return place.getCourseIdStr() != null ? place.getCourseIdStr() : "";
		}

		@Override
		public StartPlace getPlace(String token) {
			return new StartPlace(token);
		}
	}

}
