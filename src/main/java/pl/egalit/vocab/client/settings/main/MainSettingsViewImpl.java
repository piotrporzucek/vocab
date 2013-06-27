package pl.egalit.vocab.client.settings.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

public class MainSettingsViewImpl extends Composite implements MainSettingsView {

	@UiField
	Grid gridAdmin;

	@UiField
	Grid gridUser;

	private static MainSettingsImplUiBinder uiBinder = GWT
			.create(MainSettingsImplUiBinder.class);

	interface MainSettingsImplUiBinder extends
			UiBinder<Widget, MainSettingsViewImpl> {
	}

	public MainSettingsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private Presenter presenter;

	public MainSettingsViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.presenter = listener;

	}

	@Override
	public void showAdminControls() {
		gridAdmin.setVisible(true);
		gridUser.setVisible(false);
	}

}
