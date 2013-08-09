package pl.egalit.vocab.client.courseDetails;

import pl.egalit.vocab.client.core.mvp.VocabView;
import pl.egalit.vocab.client.courseDetails.CourseDetailsView.Presenter;
import pl.egalit.vocab.client.requestfactory.WordProxy;

public interface NewWordsView extends VocabView, TabChangedListener {
	void hideForm();

	void showConfirmation();

	void showError(ErrorCode errorCode);

	void markWithError(WordProxy rootBean, String message);

	void setPresenter(Presenter presenter);

	void hide();

	void show();

}
