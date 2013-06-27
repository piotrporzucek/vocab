package pl.egalit.vocab.client.core.mvp;

import pl.egalit.vocab.client.core.gin.VocabGinjector;
import pl.egalit.vocab.client.header.HeaderActivity;
import pl.egalit.vocab.client.places.LoginPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class VocabNorthActivityMapper extends VocabActivityMapper {

	private final SimplePanel northView;

	@Inject
	public VocabNorthActivityMapper(
			@Named("appWidget") DockLayoutPanel appWidget,
			@Named("north") SimplePanel northView) {
		super(appWidget);
		this.northView = northView;

	}

	@Override
	protected Widget getView() {
		return northView;
	}

	@Override
	protected Activity getVocabActivity(Place place) {
		if (place instanceof LoginPlace) {
			return null;
		} else {
			return new HeaderActivity(
					VocabGinjector.INSTANCE.getRequestFactory(),
					VocabGinjector.INSTANCE.getPlaceController(),
					VocabGinjector.INSTANCE.getHeaderView());
		}
	}

	@Override
	protected boolean shouldViewBeHidden(Place place) {
		return place instanceof LoginPlace;
	}

}
