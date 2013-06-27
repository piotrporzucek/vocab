package pl.egalit.vocab.client.core.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

public interface MyRes extends ClientBundle {

	@Source("vocab2.css")
	VocabCss css();

	@Source("add.jpg")
	@ImageOptions
	ImageResource delete();

	@Source("modify.jpg")
	@ImageOptions
	ImageResource modify();

	public interface VocabCss extends CssResource {

		@ClassName("imgClassDelete")
		String deleteButton();

		@ClassName("imgClassModify")
		String modifyButton();

		String link();

		String panelHeader();

		String listcourses();

		String cellTable();

		String buttons();

		String button();

		String sendbutton();

		String confirmationPanel();

		String restartSending();

		String backtoapp();

		String menu();

		String table();

		String header();

		String header2();

		String errorMessageStyle();

		String saveButton();

	}

}
