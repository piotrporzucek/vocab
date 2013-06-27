package pl.egalit.vocab.client.core.mvp;

import pl.egalit.vocab.client.core.gin.VocabGinjector;
import pl.egalit.vocab.client.listCourses.ListCoursesActivity;
import pl.egalit.vocab.client.places.ChangeCoursePlace;
import pl.egalit.vocab.client.places.ChangeLectorPlace;
import pl.egalit.vocab.client.places.CoursePlace;
import pl.egalit.vocab.client.places.LoginPlace;
import pl.egalit.vocab.client.places.MyAccountPlace;
import pl.egalit.vocab.client.places.SearchCoursePlace;
import pl.egalit.vocab.client.places.SearchLectorPlace;
import pl.egalit.vocab.client.places.SettingsPlace;
import pl.egalit.vocab.client.places.StartPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class VocabWestActivityMapper extends VocabActivityMapper {

	private final SimplePanel westView;
	private ListCoursesActivity listCoursesActivity;

	@Inject
	public VocabWestActivityMapper(
			@Named("appWidget") DockLayoutPanel appWidget,
			@Named("west") SimplePanel westView) {

		super(appWidget);
		this.westView = westView;
	}

	@Override
	protected Widget getView() {
		return westView;
	}

	@Override
	protected Activity getVocabActivity(Place place) {
		if (place instanceof StartPlace) {
			this.listCoursesActivity = new ListCoursesActivity(
					VocabGinjector.INSTANCE.getPlaceController(),
					VocabGinjector.INSTANCE.getRequestFactory(),
					VocabGinjector.INSTANCE.getListCoursesView());
			return this.listCoursesActivity;

		}
		if (place instanceof CoursePlace) {
			CoursePlace coursePlace = (CoursePlace) place;
			if (this.listCoursesActivity == null) {
				this.listCoursesActivity = new ListCoursesActivity(
						VocabGinjector.INSTANCE.getPlaceController(),
						VocabGinjector.INSTANCE.getRequestFactory(),
						VocabGinjector.INSTANCE.getListCoursesView(),
						((CoursePlace) place).getCourseId());
			} else if (this.listCoursesActivity.getChosenCourseId() != coursePlace
					.getCourseId()) {
				listCoursesActivity
						.setChosenCourseId(coursePlace.getCourseId());
			}
			return this.listCoursesActivity;

		}

		return null;

	}

	@Override
	protected boolean shouldViewBeHidden(Place place) {
		return place instanceof SettingsPlace
				|| place instanceof ChangeLectorPlace
				|| place instanceof SearchLectorPlace
				|| place instanceof SearchCoursePlace
				|| place instanceof ChangeCoursePlace
				|| place instanceof MyAccountPlace
				|| place instanceof LoginPlace;

	}
}
