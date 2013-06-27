package pl.egalit.vocab.client.core.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.name.Named;

public abstract class VocabActivityMapper implements ActivityMapper {

	private final DockLayoutPanel appWidget;

	public VocabActivityMapper(@Named("appWidget") DockLayoutPanel appWidget) {
		this.appWidget = appWidget;
	}

	@Override
	public Activity getActivity(Place place) {
		if (shouldViewBeHidden(place)) {
			appWidget.setWidgetHidden(getView(), true);
		} else {
			appWidget.setWidgetHidden(getView(), false);
		}
		return getVocabActivity(place);
	}

	protected abstract Widget getView();

	protected abstract Activity getVocabActivity(Place place);

	protected abstract boolean shouldViewBeHidden(Place place);
}
