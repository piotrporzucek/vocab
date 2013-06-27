package pl.egalit.vocab.client;

import pl.egalit.vocab.client.core.gin.VocabGinjector;
import pl.egalit.vocab.client.core.mvp.VocabPlaceHistoryMapper;
import pl.egalit.vocab.client.places.LoginPlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class MainVocab implements EntryPoint {

	private static final Place defaultPlace = new LoginPlace();

	@Inject
	@Named("appWidget")
	static DockLayoutPanel appWidget;

	@Inject
	@Named("west")
	static ActivityMapper westActivityMapper;

	@Inject
	@Named("west")
	static SimplePanel westView;

	@Inject
	@Named("content")
	static ActivityMapper contentActivityMapper;

	@Inject
	@Named("content")
	static SimplePanel contentView;

	@Inject
	@Named("north")
	static ActivityMapper northActivityMapper;

	@Inject
	@Named("north")
	static SimplePanel northView;

	@Inject
	@Named("south")
	static ActivityMapper southActivityMapper;

	@Inject
	@Named("south")
	static SimplePanel southView;

	@Inject
	static EventBus eventBus;

	@Inject
	static VocabPlaceHistoryMapper vocabHistoryMapper;

	@Inject
	static PlaceController placeController;

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		init();
		ActivityManager activityManager = new ActivityManager(
				westActivityMapper, eventBus);
		activityManager.setDisplay(westView);

		activityManager = new ActivityManager(contentActivityMapper, eventBus);
		activityManager.setDisplay(contentView);

		activityManager = new ActivityManager(northActivityMapper, eventBus);
		activityManager.setDisplay(northView);

		activityManager = new ActivityManager(southActivityMapper, eventBus);
		activityManager.setDisplay(southView);

		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				vocabHistoryMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);

		RootLayoutPanel.get().add(appWidget);
		RootLayoutPanel.get().addStyleName("rootPanel");
		initUI();
		// Goes to the place represented on URL else default place
		historyHandler.handleCurrentHistory();

	}

	private void init() {
		VocabGinjector.INSTANCE.getClass();
	}

	private static void initUI() {

		appWidget.addNorth(northView, 2);
		appWidget.addWest(westView, 15);
		appWidget.addSouth(southView, 2);
		appWidget.add(contentView);

	}

}
