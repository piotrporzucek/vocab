package pl.egalit.vocab.client.header;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.header.HeaderView.Presenter;
import pl.egalit.vocab.client.places.LoginPlace;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HeaderActivity extends AbstractVocabActivity<HeaderView> implements
		Presenter {

	public HeaderActivity(MyRequestFactory requestFactory,
			PlaceController placeController, HeaderView view) {
		super(requestFactory, placeController, view);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		this.requestFactory.authentificationRequest().getUsername()
				.fire(new VocabReceiver<String>(placeController) {
					@Override
					public void onSuccess(String response) {
						view.setUsername(response);
						view.setPresenter(HeaderActivity.this);
					}
				});
	}

	@Override
	public void logout() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"/logout");

		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Logout nieudany");
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					placeController.goTo(new LoginPlace(true));
				}
			});
		} catch (RequestException e) {
			// Code omitted for clarity
		}

	}

}
