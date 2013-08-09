package pl.egalit.vocab.client.settings.lector;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.Path;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.MyRequestFactory.UserRequest;
import pl.egalit.vocab.client.core.dto.CourseDto;
import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.core.exceptions.UserExistsException;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabPresenterValidationAware;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.places.SearchLectorPlace;
import pl.egalit.vocab.client.requestfactory.CourseProxy;
import pl.egalit.vocab.client.requestfactory.UserProxy;
import pl.egalit.vocab.client.settings.lector.ChangeLectorView.Presenter;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class ChangeLectorActivity extends
		AbstractVocabActivity<ChangeLectorView> implements Presenter,
		VocabPresenterValidationAware {

	private final Long lectorId;
	private final VocabConstants vocabConstants;

	public ChangeLectorActivity(MyRequestFactory requestFactory,
			PlaceController placeController, ChangeLectorView view,
			VocabConstants vocabConstants, Long lectorId) {
		super(requestFactory, placeController, view);
		this.lectorId = lectorId;
		this.vocabConstants = vocabConstants;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		if (lectorId != null) {
			this.requestFactory.userRequest()
					.findUserFromCurrentSchool(lectorId).with("courseEntities")
					.fire(new VocabReceiver<UserProxy>(placeController) {
						@Override
						public void onSuccess(UserProxy response) {
							if (response != null) {
								view.init(ChangeLectorActivity.this,
										UserDto.fromProxy(response));
							} else {
								Window.alert("Niepoprawny uzytkownik.");
								placeController.goTo(new SearchLectorPlace());
							}
						}
					});
		} else {
			view.init(this);
		}

	}

	@Override
	public void loadAvailableCourses() {
		this.requestFactory.courseRequest().getActiveCourses()
				.fire(new VocabReceiver<List<CourseProxy>>(placeController) {
					@Override
					public void onSuccess(List<CourseProxy> response) {
						Collection<CourseDto> result = Collections2.transform(
								response,
								new Function<CourseProxy, CourseDto>() {
									@Override
									public CourseDto apply(CourseProxy input) {
										return CourseDto.fromProxy(input);
									}
								});
						view.setCourses(result);
					};
				});

	}

	@Override
	public void saveLector(String name, String email, String password,
			String repeatPassword, LinkedList<CourseDto> selectedItems) {
		boolean error = false;
		if (Strings.isNullOrEmpty(password)
				|| !Objects.equal(password, repeatPassword)) {
			view.setErrorPassword(vocabConstants.passwordsNotMatch());

			error = true;
		}
		if (Strings.isNullOrEmpty(email)) {
			view.setErrorEmail(vocabConstants.noEmail());
			error = true;
		}
		if (Strings.isNullOrEmpty(name)) {
			view.setErrorEmail(vocabConstants.noLectorName());
			error = true;
		}
		if (error) {
			return;
		}
		UserRequest request = this.requestFactory.userRequest();
		UserProxy proxy = request.create(UserProxy.class);
		proxy.setEmail(email);
		proxy.setPassword(password);
		proxy.setName(name);
		Set<Long> courseIds = Sets.newHashSet();
		for (CourseDto course : selectedItems) {
			courseIds.add(course.getId());
		}
		request.save(proxy, courseIds).fire(
				new VocabReceiver<Void>(placeController, this) {
					@Override
					public void onSuccess(Void response) {
						placeController.goTo(new SearchLectorPlace());
					}

					@Override
					public void onFailure(ServerFailure error) {
						if (error.getExceptionType() != null
								&& error.getExceptionType().equals(
										UserExistsException.class.getName())) {
							view.setErrorEmail("Taki uzytkownik juz jest stworzony");
						} else {
							super.onFailure(error);
						}
					}

				});

	}

	@Override
	public void modifyLector(UserDto modifiedUser, String newPassword,
			String repeatPassword, LinkedList<CourseDto> selectedItems) {
		boolean error = false;
		if (Strings.isNullOrEmpty(modifiedUser.getEmail())) {
			view.setErrorEmail(vocabConstants.noEmail());
			error = true;
		}
		if (Strings.isNullOrEmpty(modifiedUser.getName())) {
			view.setErrorName(vocabConstants.noLectorName());
			error = true;
		}
		if (!Objects.equal(modifiedUser.getPassword(), repeatPassword)) {
			view.setErrorPassword(vocabConstants.passwordsNotMatch());
			error = true;
		} else if (!Strings.isNullOrEmpty(newPassword)) {
			modifiedUser.setPassword(newPassword);
		}
		if (error) {
			return;
		}
		UserRequest request = this.requestFactory.userRequest();
		Set<Long> courseIds = Sets.newHashSet();
		for (CourseDto course : selectedItems) {
			courseIds.add(course.getId());
		}
		UserProxy proxy = request.create(UserProxy.class);
		proxy.setEmail(modifiedUser.getEmail());
		proxy.setPassword(modifiedUser.getPassword());
		proxy.setName(modifiedUser.getName());
		proxy.setId(modifiedUser.getId());
		request.modify(proxy, courseIds).fire(
				new VocabReceiver<Void>(placeController, this) {
					@Override
					public void onSuccess(Void response) {
						placeController.goTo(new SearchLectorPlace());

					}

					@Override
					public void onFailure(ServerFailure error) {
						if (error.getExceptionType() != null
								&& error.getExceptionType().equals(
										UserExistsException.class.getName())) {
							view.setErrorEmail("Taki uzytkownik juz jest stworzony. Musisz podac inny adres e-mail.");
						}
					}
				});

	}

	@Override
	public void showError(Path propertyPath, String message) {
		if (propertyPath.toString().contains("email")) {
			view.setErrorEmail(message);
		} else {
			view.setError(message);
		}

	}

}
