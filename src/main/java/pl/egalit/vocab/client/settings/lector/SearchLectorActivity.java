package pl.egalit.vocab.client.settings.lector;

import java.util.List;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.entity.UserProxy;
import pl.egalit.vocab.client.places.SearchLectorPlace;
import pl.egalit.vocab.client.settings.lector.SearchLectorView.Presenter;

import com.google.common.collect.Lists;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SearchLectorActivity extends
		AbstractVocabActivity<SearchLectorView> implements Presenter {

	public SearchLectorActivity(MyRequestFactory requestFactory,
			PlaceController placeController, SearchLectorView view) {
		super(requestFactory, placeController, view);
		view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);

	}

	@Override
	public void loadUsers() {
		this.requestFactory.userRequest().getUsers()
				.fire(new VocabReceiver<List<UserProxy>>(placeController) {
					@Override
					public void onSuccess(List<UserProxy> response) {
						if (response != null) {
							List<UserDto> result = Lists.newArrayList();
							for (UserProxy userProxy : response) {
								result.add(UserDto.fromProxy(userProxy));
							}
							view.initView(result);
							initializeViewInAdminModeIfPossible();
						}
					}
				});

	}

	protected void initializeViewInAdminModeIfPossible() {
		this.requestFactory.authentificationRequest().isCurrentUserAdmin()
				.fire(new VocabReceiver<Boolean>(placeController) {
					@Override
					public void onSuccess(Boolean response) {
						view.setAdminMode(response);
					}
				});

	}

	@Override
	public void removeUser(long id) {
		this.requestFactory.userRequest().removeUser(id)
				.fire(new VocabReceiver<Void>(placeController) {
					@Override
					public void onSuccess(Void v) {
						placeController.goTo(new SearchLectorPlace());
					}

				});

	}
}
