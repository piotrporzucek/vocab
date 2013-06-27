package pl.egalit.vocab.client.settings.course;

import java.util.List;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.core.dto.CourseDto;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.entity.CourseProxy;
import pl.egalit.vocab.client.settings.course.SearchCourseView.Presenter;

import com.google.common.collect.Lists;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SearchCourseActivity extends
		AbstractVocabActivity<SearchCourseView> implements Presenter {

	public SearchCourseActivity(MyRequestFactory requestFactory,
			PlaceController placeController, SearchCourseView view) {
		super(requestFactory, placeController, view);
		view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);

	}

	@Override
	public void loadUsers() {
		this.requestFactory.courseRequest().getAllCourses()
				.fire(new VocabReceiver<List<CourseProxy>>(placeController) {
					@Override
					public void onSuccess(List<CourseProxy> response) {
						if (response != null) {
							List<CourseDto> result = Lists.newArrayList();
							for (CourseProxy courseProxy : response) {
								result.add(CourseDto.fromProxy(courseProxy));
							}
							view.initView(result);
							initializeViewInAdminModeIfPossible();
						}
					}
				});

	}

	protected void initializeViewInAdminModeIfPossible() {
		this.requestFactory.authentificationRequest().isCurrentUserAdmin()
				.fire(new VocabReceiver<Boolean>(placeController) {
					@Override
					public void onSuccess(Boolean response) {
						view.setAdminMode(response);
					}
				});

	}

}
