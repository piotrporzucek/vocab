package pl.egalit.vocab.client.core.gin;

import pl.egalit.vocab.client.MainVocab;
import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.core.css.TableRes;
import pl.egalit.vocab.client.core.mvp.ContentPanel;
import pl.egalit.vocab.client.core.mvp.NorthPanel;
import pl.egalit.vocab.client.core.mvp.SouthPanel;
import pl.egalit.vocab.client.core.mvp.VocabContentActivityMapper;
import pl.egalit.vocab.client.core.mvp.VocabNorthActivityMapper;
import pl.egalit.vocab.client.core.mvp.VocabSouthActivityMapper;
import pl.egalit.vocab.client.core.mvp.VocabWestActivityMapper;
import pl.egalit.vocab.client.core.mvp.WestPanel;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.courseDetails.ArchiveView;
import pl.egalit.vocab.client.courseDetails.ArchiveViewImpl;
import pl.egalit.vocab.client.courseDetails.CourseDetailsView;
import pl.egalit.vocab.client.courseDetails.CourseDetailsViewImpl;
import pl.egalit.vocab.client.header.HeaderView;
import pl.egalit.vocab.client.header.HeaderViewImpl;
import pl.egalit.vocab.client.listCourses.ListCoursesView;
import pl.egalit.vocab.client.listCourses.ListCoursesViewImpl;
import pl.egalit.vocab.client.login.LoginActivity;
import pl.egalit.vocab.client.login.LoginView;
import pl.egalit.vocab.client.login.LoginViewImpl;
import pl.egalit.vocab.client.settings.account.MyAccountActivity;
import pl.egalit.vocab.client.settings.account.MyAccountView;
import pl.egalit.vocab.client.settings.account.MyAccountViewImpl;
import pl.egalit.vocab.client.settings.course.ChangeCourseActivity;
import pl.egalit.vocab.client.settings.course.ChangeCourseView;
import pl.egalit.vocab.client.settings.course.ChangeCourseViewImpl;
import pl.egalit.vocab.client.settings.course.SearchCourseView;
import pl.egalit.vocab.client.settings.course.SearchCourseViewImpl;
import pl.egalit.vocab.client.settings.lector.ChangeLectorView;
import pl.egalit.vocab.client.settings.lector.ChangeLectorViewImpl;
import pl.egalit.vocab.client.settings.lector.SearchLectorView;
import pl.egalit.vocab.client.settings.lector.SearchLectorViewImpl;
import pl.egalit.vocab.client.settings.main.MainSettingsView;
import pl.egalit.vocab.client.settings.main.MainSettingsViewImpl;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class VocabGinmodule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(PlaceController.class).to(VocabPlaceController.class).in(
				Singleton.class);
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(LoginActivity.class).in(Singleton.class);
		bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);		
		bind(CourseDetailsView.class).to(CourseDetailsViewImpl.class);
		bind(ArchiveView.class).to(ArchiveViewImpl.class);
		bind(ActivityMapper.class).annotatedWith(Names.named("west"))
				.to(VocabWestActivityMapper.class).in(Singleton.class);
		bind(ActivityMapper.class).annotatedWith(Names.named("content"))
				.to(VocabContentActivityMapper.class).in(Singleton.class);
		bind(ActivityMapper.class).annotatedWith(Names.named("south"))
				.to(VocabSouthActivityMapper.class).in(Singleton.class);
		bind(ActivityMapper.class).annotatedWith(Names.named("north"))
				.to(VocabNorthActivityMapper.class).in(Singleton.class);
		bind(SimplePanel.class).annotatedWith(Names.named("north"))
				.to(NorthPanel.class).in(Singleton.class);
		bind(SimplePanel.class).annotatedWith(Names.named("content"))
				.to(ContentPanel.class).in(Singleton.class);
		bind(SimplePanel.class).annotatedWith(Names.named("south"))
				.to(SouthPanel.class).in(Singleton.class);
		bind(SimplePanel.class).annotatedWith(Names.named("west"))
				.to(WestPanel.class).in(Singleton.class);

		bind(ListCoursesView.class).to(ListCoursesViewImpl.class);
		bind(CellTable.Resources.class).annotatedWith(Names.named("tableRes"))
				.to(TableRes.class);
		bind(HeaderView.class).to(HeaderViewImpl.class);
		bind(MainSettingsView.class).to(MainSettingsViewImpl.class);
		bind(ChangeLectorView.class).to(ChangeLectorViewImpl.class);
		bind(SearchLectorView.class).to(SearchLectorViewImpl.class);
		bind(SearchCourseView.class).to(SearchCourseViewImpl.class);
		bind(ChangeCourseView.class).to(ChangeCourseViewImpl.class);
		bind(MyAccountView.class).to(MyAccountViewImpl.class);
		requestStaticInjection(MainVocab.class);

	}

	@Provides
	@Inject
	@Singleton
	public MyRequestFactory provideRequestFactory(EventBus eventBus) {
		MyRequestFactory requestFactory = GWT.create(MyRequestFactory.class);
		requestFactory.initialize(eventBus);
		return requestFactory;
	}

	@Provides
	@Singleton
	@Named("appWidget")
	public DockLayoutPanel providesAppWidget() {
		DockLayoutPanel appWidget = new DockLayoutPanel(Unit.EM);
		appWidget.addStyleName("appWidget");
		return appWidget;
	}

	@Provides
	@Inject
	public ChangeCourseActivity providesChangeCourseActivity(
			MyRequestFactory requestFactory, PlaceController placeController,
			ChangeCourseView view, VocabConstants vocabConstants) {
		return new ChangeCourseActivity(requestFactory, placeController, view,
				vocabConstants);
	}

	@Provides
	@Inject
	public MyAccountActivity providesMyAccountActivity(
			MyRequestFactory requestFactory, PlaceController placeController,
			MyAccountView view, VocabConstants vocabConstants) {
		return new MyAccountActivity(requestFactory, placeController, view,
				vocabConstants);
	}

}
