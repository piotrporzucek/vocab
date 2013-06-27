package pl.egalit.vocab.client.core.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

public interface Images extends ClientBundle {
	@Source("add.jpg")
	ImageResource add();

	@Source("modify.jpg")
	ImageResource modify();

	@Source("search.jpg")
	@ImageOptions(flipRtl = true)
	ImageResource search();

}
