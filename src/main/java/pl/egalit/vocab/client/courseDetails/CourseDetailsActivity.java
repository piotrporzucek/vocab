package pl.egalit.vocab.client.courseDetails;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.MyRequestFactory.CourseRequest;
import pl.egalit.vocab.client.MyRequestFactory.WordRequest;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.courseDetails.model.WordDto;
import pl.egalit.vocab.client.places.CoursePlace;
import pl.egalit.vocab.client.places.CoursePlace.TabPlace;
import pl.egalit.vocab.client.requestfactory.CourseProxy;
import pl.egalit.vocab.client.requestfactory.WordProxy;
import pl.egalit.vocab.client.requestfactory.WordsUnitProxy;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * Activities are started and stopped by an ActivityManager associated with a
 * container Widget.
 */
public class CourseDetailsActivity extends
		AbstractVocabActivity<CourseDetailsView> implements
		CourseDetailsView.Presenter {

	private final CourseRequest courseRequest;
	private final CoursePlace place;
	private final VocabConstants vocabConstants;

	@Inject
	public CourseDetailsActivity(PlaceController placeController,
			MyRequestFactory requestFactory, CourseDetailsView startView,
			CoursePlace place, VocabConstants vocabConstants) {
		super(requestFactory, placeController, startView);
		this.courseRequest = requestFactory.courseRequest();
		this.place = place;
		this.vocabConstants = vocabConstants;

	}

	@Override
	public String mayStop() {
		return null;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);

		courseRequest.findCourse(place.getCourseId()).fire(
				new VocabReceiver<CourseProxy>(placeController) {
					@Override
					public void onSuccess(CourseProxy response) {
						if (response != null) {
							view.init(CourseDetailsActivity.this,
									!response.isActive());
							view.setTab(place.getTabPlace());
						} else {
							view.showError(ErrorCode.UNKNOWN_COURSE);
						}
					}

				});
	}

	@Override
	public List<WordDto> createEmptyWords(int count) {
		List<WordDto> result = Lists.newArrayList();
		for (int i = 0; i < count; i++) {
			result.add(new WordDto());
		}
		return result;
	}

	@Override
	public void sendWords(List<WordDto> list) {
		WordRequest wordRequest = requestFactory.wordRequest();
		List<WordProxy> proxies = Lists.newArrayList();
		Iterator<WordDto> it = list.iterator();
		while (it.hasNext()) {
			WordDto word = it.next();
			if (Strings.isNullOrEmpty(word.getExpression())
					&& Strings.isNullOrEmpty(word.getAnswer())
					&& Strings.isNullOrEmpty(word.getExample())) {
				it.remove();
			} else {
				WordProxy proxy = wordRequest.create(WordProxy.class);
				proxy.setAnswer(word.getAnswer());
				proxy.setExample(word.getExample());
				proxy.setExpression(word.getExpression());
				proxies.add(proxy);
			}
		}

		wordRequest.saveAndSend(proxies, place.getCourseId()).fire(
				new Receiver<Void>() {
					@Override
					public void onSuccess(Void response) {
						view.getNewWordsView().hideForm();
						view.getNewWordsView().showConfirmation();
					};

					@Override
					public void onConstraintViolation(
							Set<ConstraintViolation<?>> violations) {

						for (ConstraintViolation<?> violation : violations) {
							view.getNewWordsView().markWithError(
									(WordProxy) violation.getRootBean(),
									violation.getMessage());
						}
						Window.alert(vocabConstants.wordSendingError());

					}
				});

	}

	@Override
	public long getCourseId() {
		return place.getCourseId();
	}

	public void setNewTab(TabPlace tabPlace) {
		this.place.setTabPlace(tabPlace);
		view.setTab(tabPlace);

	}

	@Override
	public void loadWordsUnits() {
		this.requestFactory.courseRequest().getWordsUnits(place.getCourseId())
				.fire(new VocabReceiver<List<WordsUnitProxy>>(placeController) {

					@Override
					public void onSuccess(List<WordsUnitProxy> response) {
						view.getArchiveView().clear();
						for (final WordsUnitProxy wordsUnit : response) {
							CourseDetailsActivity.this.requestFactory
									.wordRequest()
									.getWordsForWordsUnit(place.getCourseId(),
											wordsUnit.getId())
									.fire(new VocabReceiver<List<WordProxy>>(
											placeController) {
										@Override
										public void onSuccess(
												List<WordProxy> response) {
											view.getArchiveView().addWordsUnit(
													wordsUnit, response);
											view.getArchiveView().show();
										};
									});
						}
					}

				});
	}

}
