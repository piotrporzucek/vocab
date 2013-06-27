package pl.egalit.vocab.client.core.mvp;

import pl.egalit.vocab.client.core.gin.VocabGinjector;
import pl.egalit.vocab.client.courseDetails.CourseDetailsActivity;
import pl.egalit.vocab.client.login.LoginActivity;
import pl.egalit.vocab.client.places.ChangeCoursePlace;
import pl.egalit.vocab.client.places.ChangeLectorPlace;
import pl.egalit.vocab.client.places.CoursePlace;
import pl.egalit.vocab.client.places.LoginPlace;
import pl.egalit.vocab.client.places.MyAccountPlace;
import pl.egalit.vocab.client.places.SearchCoursePlace;
import pl.egalit.vocab.client.places.SearchLectorPlace;
import pl.egalit.vocab.client.places.SettingsPlace;
import pl.egalit.vocab.client.settings.course.ChangeCourseActivity;
import pl.egalit.vocab.client.settings.course.SearchCourseActivity;
import pl.egalit.vocab.client.settings.lector.ChangeLectorActivity;
import pl.egalit.vocab.client.settings.lector.SearchLectorActivity;
import pl.egalit.vocab.client.settings.main.MainSettingsActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class VocabContentActivityMapper extends VocabActivityMapper {

	private final LoginActivity loginActivity;
	private CourseDetailsActivity currentCourseDetailsActivity;
	private final SimplePanel contentView;

	@Inject
	public VocabContentActivityMapper(LoginActivity loginActivity,
			@Named("content") SimplePanel contentView,
			@Named("appWidget") DockLayoutPanel appWidget) {
		super(appWidget);
		this.loginActivity = loginActivity;
		this.contentView = contentView;
	}

	@Override
	protected Widget getView() {
		return contentView;
	}

	@Override
	protected Activity getVocabActivity(Place place) {
		if (place instanceof LoginPlace) {
			this.currentCourseDetailsActivity = null;
			if (((LoginPlace) place).isResetActivity()) {
				loginActivity.resetActivity();
			}
			return loginActivity;
		} else if (place instanceof CoursePlace) {
			CoursePlace coursePlace = (CoursePlace) place;
			if (currentCourseDetailsActivity != null
					&& currentCourseDetailsActivity.getCourseId() == coursePlace
							.getCourseId()) {
				this.currentCourseDetailsActivity.setNewTab(coursePlace
						.getTabPlace());
			} else {
				this.currentCourseDetailsActivity = new CourseDetailsActivity(
						VocabGinjector.INSTANCE.getPlaceController(),
						VocabGinjector.INSTANCE.getRequestFactory(),
						VocabGinjector.INSTANCE.getCourseDetailsView(),
						(CoursePlace) place,
						VocabGinjector.INSTANCE.getVocabConstants());
			}
			return this.currentCourseDetailsActivity;

		} else if (place instanceof SettingsPlace) {
			return new MainSettingsActivity(
					VocabGinjector.INSTANCE.getRequestFactory(),
					VocabGinjector.INSTANCE.getPlaceController(),
					VocabGinjector.INSTANCE.getMainSettingsView());
		} else if (place instanceof ChangeLectorPlace) {
			return new ChangeLectorActivity(
					VocabGinjector.INSTANCE.getRequestFactory(),
					VocabGinjector.INSTANCE.getPlaceController(),
					VocabGinjector.INSTANCE.getAddLectorView(),
					VocabGinjector.INSTANCE.getVocabConstants(),
					((ChangeLectorPlace) place).getLectorId());
		} else if (place instanceof SearchLectorPlace) {
			return new SearchLectorActivity(
					VocabGinjector.INSTANCE.getRequestFactory(),
					VocabGinjector.INSTANCE.getPlaceController(),
					VocabGinjector.INSTANCE.getSearchLectorView());
		} else if (place instanceof SearchCoursePlace) {
			return new SearchCourseActivity(
					VocabGinjector.INSTANCE.getRequestFactory(),
					VocabGinjector.INSTANCE.getPlaceController(),
					VocabGinjector.INSTANCE.getSearchCourseView());
		} else if (place instanceof ChangeCoursePlace) {
			ChangeCourseActivity activity = VocabGinjector.INSTANCE
					.getChangeCourseActivity();
			activity.setCourseId(((ChangeCoursePlace) place).getLectorId());
			return activity;
		} else if (place instanceof MyAccountPlace) {
			return VocabGinjector.INSTANCE.getMyAccountActivity();
		}
		return null;

	}

	@Override
	protected boolean shouldViewBeHidden(Place place) {
		return false;
	}
}
