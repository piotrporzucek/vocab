package pl.egalit.vocab.client.settings.lector;

import java.util.Collection;

import pl.egalit.vocab.client.core.dto.CourseDto;
import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.core.widget.MultiselectionListBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ChangeLectorViewImpl extends Composite implements ChangeLectorView {

	private static MainSettingsImplUiBinder uiBinder = GWT
			.create(MainSettingsImplUiBinder.class);

	@UiField
	Label generalMessage;
	@UiField
	TextBox email;
	@UiField
	TextBox name;
	@UiField
	PasswordTextBox password;
	@UiField
	PasswordTextBox repeatPassword;
	@UiField
	MultiselectionListBox listCourses;

	@UiField
	Label emailMessage;
	@UiField
	Label passwordMessage;
	@UiField
	Label nameMessage;
	@UiField
	Label listCoursesMessage;
	@UiField
	Button modify;
	@UiField
	Button save;

	private VocabConstants vocabConstants;

	interface MainSettingsImplUiBinder extends
			UiBinder<Widget, ChangeLectorViewImpl> {
	}

	private enum Mode {
		SAVE, MODIFY;
	}

	@Inject
	public ChangeLectorViewImpl(VocabConstants vocabConstants) {
		this.vocabConstants = vocabConstants;
		initWidget(uiBinder.createAndBindUi(this));
		listCourses.setMultipleSelect(true);
	}

	private Presenter presenter;
	private UserDto modifiedUser;

	public ChangeLectorViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public void init(Presenter presenter) {
		this.presenter = presenter;
		setMode(Mode.SAVE);
		this.presenter.loadAvailableCourses();
	}

	@Override
	public void setCourses(Collection<CourseDto> courses) {
		listCourses.setCourses(courses);

	}

	@UiHandler("save")
	public void saveLector(ClickEvent e) {
		clearMessages();

		presenter.saveLector(name.getText(), email.getText(),
				password.getText(), repeatPassword.getText(),
				listCourses.getSelectedItems());
	}

	@UiHandler("modify")
	public void modifyLector(ClickEvent e) {
		clearMessages();
		modifiedUser.setName(name.getText());
		modifiedUser.setEmail(email.getText());
		presenter.modifyLector(modifiedUser, password.getText(),
				repeatPassword.getText(), listCourses.getSelectedItems());
	}

	@Override
	public void setErrorEmail(String message) {
		emailMessage.setText(message);
		emailMessage.setVisible(true);
	}

	@Override
	public void setError(String message) {
		generalMessage.setText(message);
		generalMessage.setVisible(true);
	}

	@Override
	public void init(Presenter presenter, UserDto user) {
		init(presenter);
		this.modifiedUser = user;
		setMode(Mode.MODIFY);
		email.setText(user.getEmail());
		name.setText(user.getName());

		listCourses.setSeletectedItems(user.getCourses());
	}

	private void setMode(Mode mode) {
		if (Mode.SAVE.equals(mode)) {
			save.setVisible(true);
			modify.setVisible(false);
		} else {
			save.setVisible(false);
			modify.setVisible(true);
		}
	}

	void clearMessages() {
		emailMessage.setText("");
		passwordMessage.setText("");
		emailMessage.setVisible(false);
		passwordMessage.setVisible(false);
	}

	@Override
	public void setErrorPassword(String message) {
		passwordMessage.setText(message);
		passwordMessage.setVisible(true);

	}

	@Override
	public void setErrorName(String message) {
		nameMessage.setText(message);
		nameMessage.setVisible(true);

	}
}
