package pl.egalit.vocab.client.courseDetails;

import java.util.List;

import pl.egalit.vocab.client.core.mvp.VocabView;
import pl.egalit.vocab.client.courseDetails.CourseDetailsView.Presenter;
import pl.egalit.vocab.client.entity.WordProxy;
import pl.egalit.vocab.client.entity.WordsUnitProxy;

public interface ArchiveView extends VocabView, TabChangedListener {

	void setPresenter(Presenter presenter);

	void addWordsUnit(WordsUnitProxy wordsUnit, List<WordProxy> response);

	void show();

	void hide();

	void clear();

}
