package pl.egalit.vocab.client.core.mvp;

import pl.egalit.vocab.client.places.LoginPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class VocabSouthActivityMapper extends VocabActivityMapper {

	private final SimplePanel southView;

	@Inject
	public VocabSouthActivityMapper(
			@Named("appWidget") DockLayoutPanel appWidget,
			@Named("south") SimplePanel southView) {
		super(appWidget);
		this.southView = southView;

	}

	@Override
	protected Widget getView() {
		return southView;
	}

	@Override
	protected Activity getVocabActivity(Place place) {
		return null;
	}

	@Override
	protected boolean shouldViewBeHidden(Place place) {
		return place instanceof LoginPlace;
	}

}
