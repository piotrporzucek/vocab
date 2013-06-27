package pl.egalit.vocab.client.core.gin;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.courseDetails.CourseDetailsView;
import pl.egalit.vocab.client.header.HeaderView;
import pl.egalit.vocab.client.listCourses.ListCoursesView;
import pl.egalit.vocab.client.settings.account.MyAccountActivity;
import pl.egalit.vocab.client.settings.course.ChangeCourseActivity;
import pl.egalit.vocab.client.settings.course.ChangeCourseView;
import pl.egalit.vocab.client.settings.course.SearchCourseView;
import pl.egalit.vocab.client.settings.lector.ChangeLectorView;
import pl.egalit.vocab.client.settings.lector.SearchLectorView;
import pl.egalit.vocab.client.settings.main.MainSettingsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;

@GinModules({ VocabGinmodule.class })
public interface VocabGinjector extends Ginjector {

	public static final VocabGinjector INSTANCE = GWT
			.create(VocabGinjector.class);

	PlaceController getPlaceController();

	MyRequestFactory getRequestFactory();

	CourseDetailsView getCourseDetailsView();

	HeaderView getHeaderView();

	MainSettingsView getMainSettingsView();

	ChangeLectorView getAddLectorView();

	SearchLectorView getSearchLectorView();

	ListCoursesView getListCoursesView();

	SearchCourseView getSearchCourseView();

	ChangeCourseView getChangeCourseView();

	ChangeCourseActivity getChangeCourseActivity();

	MyAccountActivity getMyAccountActivity();

	VocabConstants getVocabConstants();

}
