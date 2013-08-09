package pl.egalit.vocab.client.login;

import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.egalit.vocab.client.core.resources.MyRes;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.listCourses.ListCoursesView;
import pl.egalit.vocab.client.requestfactory.SchoolProxy;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * Sample implementation of {@link ListCoursesView}.
 */
public class LoginViewImpl extends Composite implements LoginView {

	interface Binder extends UiBinder<Widget, LoginViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;

	@UiField(provided = true)
	LoginComposite loginComposite;

	private final MyRes myRes;

	private final VocabConstants vocabConstants;

	@Inject
	public LoginViewImpl(MyRes myRes, LoginComposite loginCompositeVar,
			VocabConstants vocabConstants) {
		this.vocabConstants = vocabConstants;
		this.myRes = myRes;
		myRes.css().ensureInjected();
		this.loginComposite = loginCompositeVar;
		initWidget(binder.createAndBindUi(this));
		this.loginComposite.registerClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setErrorMessage("");
				listener.login(loginComposite.textBoxUzytkownik.getText(),
						loginComposite.passwordTextBox.getText());

			}
		});
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		loginComposite.presenter = listener;
	}

	@Override
	public void setErrorMessage(String message) {
		loginComposite.getErrorLabel().setText(message);
	}

	@Override
	public void setErrorMessage(List<String> messages) {
		loginComposite.getErrorLabel().setHTML(
				new SafeHtmlBuilder()
						.appendEscapedLines(Joiner.on('\n').join(messages))
						.toSafeHtml().asString());
	}

	@Override
	public void openSchoolSelectionPopup(Set<SchoolProxy> response) {
		new SchoolChooserDialog(response, this.listener,
				loginComposite.textBoxUzytkownik.getText(),
				loginComposite.passwordTextBox.getText(), vocabConstants)
				.show();

	}

	private static class SchoolChooserDialog extends DialogBox {

		final Map<Integer, SchoolProxy> idx2School = Maps.newHashMap();
		private final VocabConstants vocabConstants;

		public SchoolChooserDialog(final Set<SchoolProxy> schools,
				final Presenter presenter, final String username,
				final String password, final VocabConstants vocabConstants) {
			this.vocabConstants = vocabConstants;
			setText(vocabConstants.chooseSchool());
			center();
			setPopupPosition(getPopupLeft(), 100);
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.setSpacing(20);

			// Add a drop box with the list types
			final ListBox dropBox = new ListBox(false);
			int idx = 0;
			for (SchoolProxy school : schools) {
				dropBox.addItem(school.getName());
				idx2School.put(idx++, school);
			}
			dropBox.ensureDebugId("cwListBox-dropBox");
			Button ok = new Button(vocabConstants.ok());
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.login(username, password,
							idx2School.get(dropBox.getSelectedIndex()).getId());
					SchoolChooserDialog.this.hide();

				}

			});
			ok.addStyleName("popupButton");
			Button cancel = new Button(vocabConstants.cancel());
			cancel.addStyleName("popupButton");
			cancel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					hide();
				}

			});
			HorizontalPanel buttons = new HorizontalPanel();
			buttons.setSpacing(10);
			buttons.add(cancel);
			buttons.add(ok);
			buttons.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

			VerticalPanel dropBoxPanel = new VerticalPanel();
			dropBoxPanel.setSpacing(4);

			dropBoxPanel.add(dropBox);
			dropBoxPanel.add(buttons);
			setWidget(dropBoxPanel);

		}
	}

	@Override
	public void forgottenPasswordRestoreSucceeded() {
		loginComposite.forgottenPasswordRestoreSucceeded();
	}

	@Override
	public void forgottenPasswordRestoreFailure() {
		loginComposite.forgottenPasswordRestoreFailure();
	}

}
