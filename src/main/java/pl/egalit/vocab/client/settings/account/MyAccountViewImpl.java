package pl.egalit.vocab.client.settings.account;

import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.core.resources.VocabConstants;

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

public class MyAccountViewImpl extends Composite implements MyAccountView {

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
	Label emailMessage;
	@UiField
	Label nameMessage;
	@UiField
	Label passwordMessage;

	@UiField
	Button changeAccount;

	@UiField
	Button changePassword;

	private VocabConstants vocabConstants;

	interface MainSettingsImplUiBinder extends
			UiBinder<Widget, MyAccountViewImpl> {
	}

	@Inject
	public MyAccountViewImpl(VocabConstants vocabConstants) {
		this.vocabConstants = vocabConstants;
		initWidget(uiBinder.createAndBindUi(this));

	}

	private Presenter presenter;
	private UserDto modifiedUser;

	public MyAccountViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("changeAccount")
	public void changeAccount(ClickEvent e) {
		clearMessagesForAccount();
		presenter.changeAccount(modifiedUser, name.getText(), email.getText());

	}

	@UiHandler("changePassword")
	public void changePassword(ClickEvent e) {
		clearMessagesForPassword();

		// modifiedUser.setName(name.getText());
		// modifiedUser.setEmail(email.getText());
		presenter.changePassword(modifiedUser, password.getText(),
				repeatPassword.getText());

	}

	@Override
	public void setErrorEmail(String message) {
		emailMessage.setText(message);
		emailMessage.setVisible(true);
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
	public void setError(String message) {
		generalMessage.setText(message);
		generalMessage.setVisible(true);
	}

	@Override
	public void init(Presenter presenter, UserDto user) {
		this.presenter = presenter;
		this.modifiedUser = user;
		email.setText(user.getEmail());
		name.setText(user.getName());
	}

	void clearMessagesForAccount() {
		emailMessage.setText("");
		emailMessage.setVisible(false);
		nameMessage.setText("");
		nameMessage.setVisible(false);
	}

	void clearMessagesForPassword() {
		passwordMessage.setText("");
		passwordMessage.setVisible(false);
	}

}
