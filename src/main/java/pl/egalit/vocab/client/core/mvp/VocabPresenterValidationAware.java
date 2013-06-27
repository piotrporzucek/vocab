package pl.egalit.vocab.client.core.mvp;

import javax.validation.Path;

public interface VocabPresenterValidationAware {
	void showError(Path propertyPath, String message);
}
