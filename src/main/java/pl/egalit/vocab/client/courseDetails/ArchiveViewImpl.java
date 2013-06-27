package pl.egalit.vocab.client.courseDetails;

import java.util.List;

import pl.egalit.vocab.client.courseDetails.CourseDetailsView.Presenter;
import pl.egalit.vocab.client.entity.WordProxy;
import pl.egalit.vocab.client.entity.WordsUnitProxy;
import pl.egalit.vocab.client.places.CoursePlace.TabPlace;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ArchiveViewImpl extends Composite implements ArchiveView {

	interface Binder extends UiBinder<Widget, ArchiveViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter presenter;

	@UiField
	VerticalPanel mainPanel;

	public ArchiveViewImpl() {
		initWidget(binder.createAndBindUi(this));

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void tabChanged(TabPlace newTab) {
		if (presenter == null || !TabPlace.ARCHIVE.equals(newTab)) {
			hide();
		} else {
			mainPanel.clear();
			presenter.loadWordsUnits();
		}

	}

	private void setupTable(WordsUnitProxy wordsUnitProxy, List<WordProxy> words) {
		CellTable<WordProxy> table = new CellTable<WordProxy>() {
			@Override
			protected TableSectionElement getTableHeadElement() {
				return super.getTableHeadElement();
			}
		};

		table.setRowCount(words.size(), true);
		table.setRowData(words);

		Column<WordProxy, String> expressionColumn = createExpressionColumn();
		Column<WordProxy, String> answerColumn = createAnswerColumn();
		Column<WordProxy, String> exampleColumn = createExampleColumn();

		table.addColumn(expressionColumn, wordsUnitProxy.getAddedDate()
				.toLocaleString());
		table.addColumn(answerColumn,
				"dodane przez " + wordsUnitProxy.getLector());
		table.addColumn(exampleColumn);

		mainPanel.add(table);

	}

	private TextColumn<WordProxy> createExampleColumn() {

		return new TextColumn<WordProxy>() {
			@Override
			public String getValue(WordProxy obj) {
				return Strings.nullToEmpty(obj.getExample());
			};
		};
	}

	private TextColumn<WordProxy> createAnswerColumn() {

		return new TextColumn<WordProxy>() {
			@Override
			public String getValue(WordProxy obj) {
				return Strings.nullToEmpty(obj.getAnswer());
			};
		};
	}

	private TextColumn<WordProxy> createExpressionColumn() {
		return new TextColumn<WordProxy>() {
			@Override
			public String getValue(WordProxy obj) {
				return Strings.nullToEmpty(obj.getExpression());
			};
		};
	}

	@Override
	public void addWordsUnit(WordsUnitProxy wordsUnit, List<WordProxy> words) {
		setupTable(wordsUnit, words);
	}

	@Override
	public void show() {
		setVisible(true);

	}

	@Override
	public void hide() {
		setVisible(false);

	}

	@Override
	public void clear() {
		mainPanel.clear();
	}

}
