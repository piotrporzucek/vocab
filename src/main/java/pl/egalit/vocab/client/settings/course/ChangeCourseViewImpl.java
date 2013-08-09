package pl.egalit.vocab.client.settings.course;

import pl.egalit.vocab.client.Language;
import pl.egalit.vocab.client.core.dto.CourseDto;
import pl.egalit.vocab.client.core.resources.LanguageConstants;
import pl.egalit.vocab.client.core.resources.VocabConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;

public class ChangeCourseViewImpl extends Composite implements ChangeCourseView {

	private static MainSettingsImplUiBinder uiBinder = GWT
			.create(MainSettingsImplUiBinder.class);

	@UiField
	Label generalMessage;

	@UiField
	TextBox name;

	@UiField
	TextBox password;

	@UiField
	Label passwordMessage;

	@UiField
	TextArea description;

	@UiField
	Label nameMessage;

	@UiField
	Button save;

	@UiField
	DateBox startDate;

	@UiField
	Label startDateMessage;

	@UiField
	DateBox endDate;

	@UiField
	Label endDateMessage;

	@UiField
	CheckBox active;

	@UiField
	ListBox language;

	@UiField
	Label languageMessage;

	private Presenter presenter;
	private CourseDto modifiedCourse;

	private VocabConstants vocabConstants;

	private LanguageConstants languageConstants;

	interface MainSettingsImplUiBinder extends
			UiBinder<Widget, ChangeCourseViewImpl> {
	}

	@Inject
	public ChangeCourseViewImpl(VocabConstants vocabConstants,
			LanguageConstants languageConstants) {
		this.vocabConstants = vocabConstants;
		this.languageConstants = languageConstants;
		initWidget(uiBinder.createAndBindUi(this));
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
		endDate.setFormat(new DateBox.DefaultFormat(dateFormat));
		startDate.setFormat(new DateBox.DefaultFormat(dateFormat));
		initLanguageListbox();

	}

	private void initLanguageListbox() {
		for (Language l : Language.values()) {
			language.addItem(languageConstants.getString(l.getCode()),
					l.getCode());
		}

	}

	public ChangeCourseViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public void init(Presenter presenter) {
		this.presenter = presenter;
		modifiedCourse = new CourseDto();
	}

	@UiHandler("save")
	public void saveCourse(ClickEvent e) {

		modifiedCourse.setName(name.getText());
		modifiedCourse.setActive(active.isEnabled());
		modifiedCourse.setStartDate(startDate.getValue());
		modifiedCourse.setEndDate(endDate.getValue());
		modifiedCourse.setPassword(password.getText());
		modifiedCourse.setActive(active.getValue());
		modifiedCourse.setDescription(description.getText());
		modifiedCourse.setLanguage(Language.values()[language
				.getSelectedIndex()].getCode());
		clearMessages();
		presenter.saveCourse(modifiedCourse);
	}

	@Override
	public void setError(String message) {
		generalMessage.setText(message);
		generalMessage.setVisible(true);
	}

	@Override
	public void init(Presenter presenter, CourseDto course) {
		init(presenter);
		this.modifiedCourse = course;
		name.setText(course.getName());
		active.setValue(course.isActive());
		startDate.setValue(course.getStartDate());
		endDate.setValue(course.getEndDate());
		password.setText(course.getPassword());
		description.setText(course.getDescription());
		language.setSelectedIndex(Language.getLanguageForCode(
				course.getLanguage()).ordinal());
	}

	void clearMessages() {
		nameMessage.setVisible(false);
		generalMessage.setVisible(false);
		startDateMessage.setVisible(false);
		endDateMessage.setVisible(false);
		passwordMessage.setVisible(false);
		languageMessage.setVisible(false);
		generalMessage.setText("");
		nameMessage.setText("");
		startDateMessage.setText("");
		endDateMessage.setText("");
		passwordMessage.setText("");
		languageMessage.setText("");
	}

	@Override
	public void setErrorName(String message) {
		nameMessage.setText(message);
		nameMessage.setVisible(true);

	}

	@Override
	public void setErrorPassword(String message) {
		passwordMessage.setText(message);
		passwordMessage.setVisible(true);

	}

	@Override
	public void setErrorStartDate(String message) {
		startDateMessage.setText(message);
		startDateMessage.setVisible(true);

	}

	@Override
	public void setErrorEndDate(String message) {
		endDateMessage.setText(message);
		endDateMessage.setVisible(true);
	}

	@Override
	public void setErrorLanguage(String message) {
		languageMessage.setText(message);
		languageMessage.setVisible(true);
	}
}
