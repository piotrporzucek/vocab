package pl.egalit.vocab.client.settings.account;

import java.util.Set;

import javax.validation.Path;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.MyRequestFactory.UserRequest;
import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.core.exceptions.UserExistsException;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabPresenterValidationAware;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.places.SearchLectorPlace;
import pl.egalit.vocab.client.places.SettingsPlace;
import pl.egalit.vocab.client.requestfactory.UserProxy;
import pl.egalit.vocab.client.settings.account.MyAccountView.Presenter;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class MyAccountActivity extends AbstractVocabActivity<MyAccountView>
		implements Presenter, VocabPresenterValidationAware {

	private final VocabConstants vocabConstants;

	public MyAccountActivity(MyRequestFactory requestFactory,
			PlaceController placeController, MyAccountView view,
			VocabConstants vocabConstants) {
		super(requestFactory, placeController, view);
		this.vocabConstants = vocabConstants;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		this.requestFactory.userRequest().getCurrentUserFromCurrentSchool()
				.fire(new VocabReceiver<UserProxy>(placeController) {
					@Override
					public void onSuccess(UserProxy response) {
						if (response != null) {
							view.init(MyAccountActivity.this,
									UserDto.fromProxy(response));
						} else {
							Window.alert("Niepoprawny uzytkownik.");
							placeController.goTo(new SearchLectorPlace());
						}
					}
				});

	}

	@Override
	public void changePassword(UserDto modifiedUser, String password,
			String repeatPassword) {
		boolean error = false;
		if (!Objects.equal(password, repeatPassword)) {
			view.setErrorEmail(vocabConstants.passwordsNotMatch());
			error = true;
		}
		if (Strings.isNullOrEmpty(password)) {
			view.setErrorEmail(vocabConstants.noEmail());
			error = true;
		}
		if (error) {
			return;
		}
		UserRequest request = this.requestFactory.userRequest();
		Set<Long> courseIds = Sets.newHashSet();

		UserProxy proxy = request.create(UserProxy.class);
		proxy.setEmail(modifiedUser.getEmail());
		proxy.setPassword(password);
		proxy.setName(modifiedUser.getName());
		proxy.setId(modifiedUser.getId());
		request.modify(proxy, courseIds).fire(
				new VocabReceiver<Void>(placeController, this) {
					@Override
					public void onSuccess(Void response) {
						placeController.goTo(new SettingsPlace());
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

	@Override
	public void changeAccount(UserDto modifiedUser, String name, String email) {
		boolean error = false;
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
		Set<Long> courseIds = Sets.newHashSet();

		UserProxy proxy = request.create(UserProxy.class);
		proxy.setEmail(email);
		proxy.setPassword(modifiedUser.getPassword());
		proxy.setName(name);
		proxy.setId(modifiedUser.getId());
		request.modify(proxy, courseIds).fire(
				new VocabReceiver<Void>(placeController, this) {
					@Override
					public void onSuccess(Void response) {
						placeController.goTo(new SettingsPlace());
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

}
