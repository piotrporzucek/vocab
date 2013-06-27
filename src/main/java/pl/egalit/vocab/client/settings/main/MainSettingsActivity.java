package pl.egalit.vocab.client.settings.main;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.settings.main.MainSettingsView.Presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class MainSettingsActivity extends
		AbstractVocabActivity<MainSettingsView> implements Presenter {

	public MainSettingsActivity(MyRequestFactory requestFactory,
			PlaceController placeController, MainSettingsView view) {
		super(requestFactory, placeController, view);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		this.requestFactory.authentificationRequest().isCurrentUserAdmin()
				.fire(new VocabReceiver<Boolean>(placeController) {
					@Override
					public void onSuccess(Boolean isAdmin) {
						if (isAdmin) {
							view.showAdminControls();
						}
					}
				});

	}

}
