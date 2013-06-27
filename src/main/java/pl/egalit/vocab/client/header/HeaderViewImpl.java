package pl.egalit.vocab.client.header;

import pl.egalit.vocab.client.core.resources.MyRes;
import pl.egalit.vocab.client.places.SettingsPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class HeaderViewImpl extends Composite implements HeaderView {

	private static HeaderViewImplUiBinder uiBinder = GWT
			.create(HeaderViewImplUiBinder.class);

	@UiField
	Label loginName;
	@UiField
	Anchor settings;
	@UiField
	Anchor logout;

	private MyRes myRes;

	interface HeaderViewImplUiBinder extends UiBinder<Widget, HeaderViewImpl> {
	}

	@Inject
	public HeaderViewImpl(MyRes myRes) {
		this.myRes = myRes;
		myRes.css().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));

	}

	private Presenter presenter;

	public HeaderViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setUsername(String response) {
		loginName.setText(response);

	}

	@UiHandler("settings")
	public void goToSettings(ClickEvent event) {
		presenter.goTo(new SettingsPlace());

	}

	@UiHandler("logout")
	public void logout(ClickEvent event) {
		presenter.logout();

	}

}
