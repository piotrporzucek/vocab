package pl.egalit.vocab.client.core.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface CssResources extends ClientBundle {

	public static final CssResources INSTANCE = GWT.create(CssResources.class);

	@Source("widgets.css")
	@CssResource.NotStrict
	CssResource css();

}
