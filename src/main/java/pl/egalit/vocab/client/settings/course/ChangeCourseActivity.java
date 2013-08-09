package pl.egalit.vocab.client.settings.course;

import javax.validation.Path;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.MyRequestFactory.CourseRequest;
import pl.egalit.vocab.client.core.dto.CourseDto;
import pl.egalit.vocab.client.core.exceptions.CourseExistsException;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabPresenterValidationAware;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.places.SearchCoursePlace;
import pl.egalit.vocab.client.requestfactory.CourseProxy;
import pl.egalit.vocab.client.settings.course.ChangeCourseView.Presenter;

import com.google.common.base.Strings;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class ChangeCourseActivity extends
		AbstractVocabActivity<ChangeCourseView> implements Presenter,
		VocabPresenterValidationAware {

	private Long courseId;
	private final VocabConstants vocabConstants;

	@Inject
	public ChangeCourseActivity(MyRequestFactory requestFactory,
			PlaceController placeController, ChangeCourseView view,
			VocabConstants vocabConstants) {
		super(requestFactory, placeController, view);

		this.vocabConstants = vocabConstants;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		if (courseId != null) {
			this.requestFactory.courseRequest().findCourse(courseId)
					.fire(new VocabReceiver<CourseProxy>(placeController) {
						@Override
						public void onSuccess(CourseProxy response) {
							if (response != null) {
								view.init(ChangeCourseActivity.this,
										CourseDto.fromProxy(response));
							} else {
								Window.alert("Niepoprawny kurs.");
							}
						}

					});
		} else {
			view.init(this);
		}

	}

	@Override
	public void saveCourse(CourseDto course) {

		boolean error = false;
		if (Strings.isNullOrEmpty(course.getName())) {
			view.setErrorName(vocabConstants.noCourseName());
			error = true;
		}
		if (course.getStartDate() == null) {
			view.setErrorStartDate(vocabConstants.noStartDate());
			error = true;
		}
		if (course.getEndDate() == null) {
			view.setErrorEndDate(vocabConstants.noEndDate());
			error = true;
		}
		if (course.getStartDate() != null && course.getEndDate() != null) {
			if (course.getStartDate().after(course.getEndDate())) {
				view.setErrorStartDate(vocabConstants.startDateAfterEndDate());
				error = true;
			}
		}
		if (Strings.isNullOrEmpty(course.getPassword())) {
			view.setErrorPassword(vocabConstants.noCoursePassword());
			error = true;
		}
		if (error) {
			return;
		}
		CourseRequest request = this.requestFactory.courseRequest();
		CourseProxy proxy = request.create(CourseProxy.class);
		proxy.setName(course.getName());
		proxy.setStartDate(course.getStartDate());
		proxy.setEndDate(course.getEndDate());
		proxy.setActive(course.isActive());
		proxy.setId(course.getId());
		proxy.setPassword(course.getPassword());
		proxy.setDescription(course.getDescription());
		proxy.setLanguage(course.getLanguage());
		request.save(proxy).fire(
				new VocabReceiver<Void>(placeController, this) {
					@Override
					public void onSuccess(Void response) {
						placeController.goTo(new SearchCoursePlace());

					}

					@Override
					public void onFailure(ServerFailure error) {
						if (error.getExceptionType() != null
								&& error.getExceptionType().equals(
										CourseExistsException.class.getName())) {
							view.setErrorName("Taki kurs juz jest stworzony.");
						} else {
							super.onFailure(error);
						}
					}

				});

	}

	@Override
	public void showError(Path propertyPath, String message) {
		if (propertyPath.toString().contains("name")) {
			view.setErrorName(message);
		}
		if (propertyPath.toString().contains("password")) {
			view.setErrorPassword(message);
		}
		if (propertyPath.toString().contains("startDate")) {
			view.setErrorStartDate(message);
		}
		if (propertyPath.toString().contains("endDate")) {
			view.setErrorEndDate(message);
		}

	}

}
