package pl.egalit.vocab.client.core.mvp;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;

import pl.egalit.vocab.client.core.exceptions.AppSecurityException;
import pl.egalit.vocab.client.places.LoginPlace;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public abstract class VocabReceiver<V> extends Receiver<V> {

	private final PlaceController placeController;
	private VocabPresenterValidationAware presenter;

	public VocabReceiver(PlaceController placeController) {
		this.placeController = placeController;
	}

	public VocabReceiver(PlaceController placeController,
			VocabPresenterValidationAware presenter) {
		this.placeController = placeController;
		this.presenter = presenter;
	}

	@Override
	public void onFailure(ServerFailure error) {
		String accessDeniedException = "org.springframework.security.access.AccessDeniedException";
		String exceptionType = error.getExceptionType();
		if (exceptionType != null
				&& (error.getExceptionType().equals(
						AppSecurityException.class.getName()) || exceptionType
						.equals(accessDeniedException))) {
			placeController.goTo(new LoginPlace());
		} else {
			super.onFailure(error);
		}
	}

	@Override
	public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
		for (ConstraintViolation<?> violation : violations) {
			showError(violation.getPropertyPath(), violation.getMessage());
		}
	}

	private void showError(Path propertyPath, String message) {
		presenter.showError(propertyPath, message);
	}

}
