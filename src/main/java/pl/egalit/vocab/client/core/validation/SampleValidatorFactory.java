package pl.egalit.vocab.client.core.validation;

import javax.validation.Validator;

import pl.egalit.vocab.client.requestfactory.LoginRequestProxy;
import pl.egalit.vocab.client.requestfactory.WordProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

public final class SampleValidatorFactory extends AbstractGwtValidatorFactory {

	/**
	 * Validator marker for the Validation Sample project. Only the classes and
	 * groups listed in the {@link GwtValidation} annotation can be validated.
	 */
	@GwtValidation(value = { LoginRequestProxy.class, WordProxy.class })
	public interface GwtValidator extends Validator {
	}

	@Override
	public AbstractGwtValidator createValidator() {
		return GWT.create(GwtValidator.class);
	}
}