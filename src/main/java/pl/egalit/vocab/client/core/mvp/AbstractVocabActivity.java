package pl.egalit.vocab.client.core.mvp;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.listCourses.ListCoursesView;
import pl.egalit.vocab.client.places.LoginPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public abstract class AbstractVocabActivity<V extends VocabView>
		extends AbstractActivity {

	protected MyRequestFactory requestFactory;
	protected PlaceController placeController;
	protected AcceptsOneWidget panel;
	protected V view;

	public AbstractVocabActivity(MyRequestFactory requestFactory,
			PlaceController placeController, V view) {
		this.requestFactory = requestFactory;
		this.placeController = placeController;
		this.view = view;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		redirectUserBasedOnAuthenticationStatus();
		this.panel = panel;

	}

	protected void userNotAuthenticated() {
		placeController.goTo(new LoginPlace());
	}

	protected void userAuthenticated() {
		panel.setWidget(view);
	}

	private void redirectUserBasedOnAuthenticationStatus() {

		requestFactory.authentificationRequest().isUserAuthenticated()
				.fire(new Receiver<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if (result != null && result) {
							userAuthenticated();
						} else {
							userNotAuthenticated();
						}
						
					}
					@Override
					public void onFailure(ServerFailure error) {						
						userNotAuthenticated();
					}
					
				});

	}

	/**
	 * @see ListCoursesView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		placeController.goTo(place);
	}

}
