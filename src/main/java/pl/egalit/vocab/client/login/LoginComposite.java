package pl.egalit.vocab.client.login;

import pl.egalit.vocab.client.core.resources.MyRes;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.login.LoginView.Presenter;

import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;

public class LoginComposite extends Composite {
	TextBox textBoxUzytkownik;
	PasswordTextBox passwordTextBox;
	private final Button btnLoginButton;

	private final HTML lblError;

	private ForgottenPasswordDialog forgottenPasswordPopoup;
	Presenter presenter;
	private final MyRes myRes;
	private final VocabConstants vocabConstants;

	@Inject
	public LoginComposite(MyRes myRes, VocabConstants vocabConstants) {
		this.vocabConstants = vocabConstants;
		this.myRes = myRes;
		myRes.css().ensureInjected();

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		initWidget(verticalPanel);

		lblError = new HTML();
		lblError.setStyleName("gwt-Label-Error");
		verticalPanel.add(lblError);

		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);

		Label lblUzytkonik = new Label(vocabConstants.loginName());
		lblUzytkonik.setStyleName("gwt-Label-Login");
		flexTable.setWidget(1, 0, lblUzytkonik);

		textBoxUzytkownik = new TextBox();
		textBoxUzytkownik.setStyleName("gwt-TextBox-Login");
		flexTable.setWidget(1, 1, textBoxUzytkownik);

		Label lblNewLabel = new Label(vocabConstants.loginPassword());
		lblNewLabel.setStyleName("gwt-Label-Login");
		flexTable.setWidget(2, 0, lblNewLabel);

		passwordTextBox = new PasswordTextBox();
		passwordTextBox.setStyleName("gwt-TextBox-Login");
		flexTable.setWidget(2, 1, passwordTextBox);

		CheckBox chckbxZapamietajMnie = new CheckBox(
				vocabConstants.rememberMe());
		flexTable.setWidget(3, 1, chckbxZapamietajMnie);

		btnLoginButton = new Button("New button");
		btnLoginButton.setText(vocabConstants.loginButton());
		btnLoginButton.setWidth("80px");
		flexTable.setWidget(5, 1, btnLoginButton);

		Anchor forgottenPassword = new Anchor();
		forgottenPassword.setStyleName(myRes.css().link());
		forgottenPassword.setText(vocabConstants.forgottenPassword());

		forgottenPassword.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				openForgottenPasswordPopup();
			}
		});
		flexTable.setWidget(6, 1, forgottenPassword);

	}

	public void openForgottenPasswordPopup() {
		this.forgottenPasswordPopoup = new ForgottenPasswordDialog(presenter);
		this.forgottenPasswordPopoup.show();

	}

	public static class ForgottenPasswordDialog extends DialogBox {

		private final VerticalPanel dropBoxPanel;

		public ForgottenPasswordDialog(final Presenter presenter) {
			setText("Odzyskiwanie hasla");
			center();
			setPopupPosition(getPopupLeft(), 100);
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.setSpacing(20);

			hPanel.add(new Label("e-mail"));
			// Add a drop box with the list types
			final TextBox usernameBox = new TextBox();
			hPanel.add(usernameBox);
			Button ok = new Button("OK");
			ok.addStyleName("popupButton");
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!Strings.isNullOrEmpty(usernameBox.getText())) {
						presenter.recoverForgottenPassword(usernameBox
								.getText());
					}
				}

			});
			Button cancel = new Button("Anuluj");
			cancel.addStyleName("popupButton");
			cancel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					hide();
				}

			});
			HorizontalPanel buttons = new HorizontalPanel();
			buttons.setSpacing(10);

			dropBoxPanel = new VerticalPanel();
			dropBoxPanel.setSpacing(4);
			dropBoxPanel.add(new Label("Podaj swoj adres e-mail"));
			dropBoxPanel.add(hPanel);
			buttons.add(cancel);
			buttons.add(ok);
			buttons.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			dropBoxPanel.add(buttons);
			setWidget(dropBoxPanel);

		}

		public void openSuccessView() {
			dropBoxPanel.clear();
			dropBoxPanel.add(new Label(
					"Na podany adres e-mail zostalo wyslane haslo."));
			Button ok = new Button("Zamknij");
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ForgottenPasswordDialog.this.hide();
				}

			});
			dropBoxPanel.add(ok);
		}

		public void openFailureView() {
			dropBoxPanel.clear();
			dropBoxPanel.add(new Label("E-mail nie zostal odnaleziony."));
			Button ok = new Button("Zamknij");
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ForgottenPasswordDialog.this.hide();
				}

			});
			dropBoxPanel.add(ok);

		}
	}

	void registerClickHandler(ClickHandler handler) {
		btnLoginButton.addClickHandler(handler);

	}

	public HTML getErrorLabel() {
		return lblError;
	}

	public void forgottenPasswordRestoreSucceeded() {
		if (forgottenPasswordPopoup != null) {
			forgottenPasswordPopoup.openSuccessView();
		}
	}

	public void forgottenPasswordRestoreFailure() {
		if (forgottenPasswordPopoup != null) {
			forgottenPasswordPopoup.openFailureView();
		}
	}

}
