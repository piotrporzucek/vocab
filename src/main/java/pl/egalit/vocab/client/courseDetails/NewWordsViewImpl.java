package pl.egalit.vocab.client.courseDetails;

import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.core.widget.BrowserEventObserver;
import pl.egalit.vocab.client.core.widget.VocabTextInputCell;
import pl.egalit.vocab.client.courseDetails.CourseDetailsView.Presenter;
import pl.egalit.vocab.client.courseDetails.model.WordDto;
import pl.egalit.vocab.client.places.CoursePlace.TabPlace;
import pl.egalit.vocab.client.requestfactory.WordProxy;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class NewWordsViewImpl extends Composite implements NewWordsView,
		BrowserEventObserver {

	interface Binder extends UiBinder<Widget, NewWordsViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private MultiSelectionModel<WordDto> selectionModel;

	private ListDataProvider<WordDto> dataProvider;

	@UiField(provided = true)
	CellTable<WordDto> cellTable;

	@UiField
	Button removeButton;

	@UiField
	Button addButton;

	@UiField
	Button sendButton;

	@UiField
	VerticalPanel formPanel;

	@UiField
	HTMLPanel confirmationPanel;

	@UiField
	HTMLPanel errorPanel;

	@UiField
	Anchor restartSending;

	private Presenter presenter;

	private boolean confirmationShown;

	private final VocabConstants vocabConstants;

	public NewWordsViewImpl(CellTable.Resources tableRes,
			VocabConstants vocabConstants) {
		cellTable = new CellTable<WordDto>(6, tableRes);
		this.vocabConstants = vocabConstants;
		initWidget(binder.createAndBindUi(this));

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		setupTable();
		setupRestartSendingHyperlink();
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	private void enableSendButtonIfPossible(String currentText) {
		if (Strings.isNullOrEmpty(currentText) && isTableEmpty()) {
			sendButton.setEnabled(false);
		} else {
			sendButton.setEnabled(true);
		}
	}

	private void enableRemoveButtonIfPossible() {
		removeButton.setEnabled(!selectionModel.getSelectedSet().isEmpty());
	}

	protected boolean isTableEmpty() {
		for (WordDto word : dataProvider.getList()) {
			if (!Strings.isNullOrEmpty(word.getAnswer())
					|| !Strings.isNullOrEmpty(word.getExample())
					|| !Strings.isNullOrEmpty(word.getExpression())) {
				return false;
			}
		}
		return true;
	}

	protected void restartNewWordsDefaultView() {
		hideConfirmation();
		hideError();
		showForm();
		dataProvider.setList(NewWordsViewImpl.this.presenter
				.createEmptyWords(5));
		cellTable.redraw();
	}

	private void setupRestartSendingHyperlink() {
		restartSending.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				restartNewWordsDefaultView();
			}

		});

	}

	protected void showForm() {
		formPanel.setVisible(true);
		removeButton.setEnabled(false);
		sendButton.setEnabled(false);
		this.confirmationShown = false;

	}

	@UiHandler("removeButton")
	public void removeWords(ClickEvent event) {
		dataProvider.getList().removeAll(selectionModel.getSelectedSet());
	}

	@UiHandler("addButton")
	public void addWords(ClickEvent event) {
		dataProvider.getList().add(new WordDto());
	}

	@UiHandler("sendButton")
	public void onClick(ClickEvent event) {
		presenter.sendWords(dataProvider.getList());
		dataProvider.refresh();
		cellTable.redraw();

	}

	private enum FieldName {
		EXPRESSION, ANSWER, EXAMPLE;
	}

	private void setupTable() {

		FieldUpdater<WordDto, String> fieldExpressionUpdater = createFieldUpdater(FieldName.EXPRESSION);
		FieldUpdater<WordDto, String> fieldAnswerUpdater = createFieldUpdater(FieldName.ANSWER);
		FieldUpdater<WordDto, String> fieldExampleUpdater = createFieldUpdater(FieldName.EXAMPLE);

		Column<WordDto, String> expressionColumn = createExpressionColumn(fieldExpressionUpdater);
		Column<WordDto, String> answerColumn = createAnswerColumn(fieldAnswerUpdater);
		Column<WordDto, String> exampleColumn = createExampleColumn(fieldExampleUpdater);
		selectionModel = new MultiSelectionModel<WordDto>(null);
		cellTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<WordDto> createCheckboxManager());
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						enableRemoveButtonIfPossible();
					}
				});
		Column<WordDto, Boolean> checkColumn = createCheckColumn();

		Header<Boolean> selectAllHeader = createSelectAllHeader();
		cellTable.addColumn(checkColumn, selectAllHeader);
		cellTable.setColumnWidth(checkColumn, 10, Unit.PCT);

		// Add the columns.
		cellTable.addColumn(expressionColumn, vocabConstants.expression());
		cellTable.setColumnWidth(expressionColumn, 20, Unit.PCT);

		cellTable.addColumn(answerColumn, vocabConstants.translation());
		cellTable.setColumnWidth(answerColumn, 20, Unit.PCT);
		cellTable.addColumn(exampleColumn, vocabConstants.example());
		cellTable.setColumnWidth(exampleColumn, 50, Unit.PCT);

		dataProvider = new ListDataProvider<WordDto>(
				this.presenter.createEmptyWords(5));

		// Connect the list to the data provider.
		dataProvider.addDataDisplay(cellTable);
		cellTable.redraw();

	}

	private FieldUpdater<WordDto, String> createFieldUpdater(
			final FieldName fieldName) {
		FieldUpdater<WordDto, String> fieldUpdater = new FieldUpdater<WordDto, String>() {
			@Override
			public void update(int index, WordDto object, String value) {
				switch (fieldName) {
				case EXPRESSION:
					object.setExpression(value);
					break;
				case ANSWER:
					object.setAnswer(value);
					break;
				case EXAMPLE:
					object.setExample(value);
					break;
				}
				// cellTable.redraw();
			}
		};
		return fieldUpdater;
	}

	private Header<Boolean> createSelectAllHeader() {
		Header<Boolean> selectAllHeader = new Header<Boolean>(new CheckboxCell(
				true, false)) {
			@Override
			public Boolean getValue() {
				if (cellTable.getVisibleItemCount() == 0)
					return false;
				for (WordDto word : cellTable.getVisibleItems()) {
					if (!selectionModel.isSelected(word)) {
						return false;
					}
				}
				return true;
			}
		};
		selectAllHeader.setUpdater(new ValueUpdater<Boolean>() {
			@Override
			public void update(Boolean value) {
				for (WordDto item : cellTable.getVisibleItems()) {
					selectionModel.setSelected(item, value);
				}
			}
		});
		selectAllHeader.setHeaderStyleNames("newWordsTableSelectAll");
		return selectAllHeader;
	}

	private Column<WordDto, Boolean> createCheckColumn() {
		Column<WordDto, Boolean> checkColumn = new Column<WordDto, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(WordDto object) {

				return selectionModel.isSelected(object);
			}

		};

		return checkColumn;
	}

	private Column<WordDto, String> createExampleColumn(
			FieldUpdater<WordDto, String> fieldUpdater) {

		Column<WordDto, String> exampleColumn = new Column<WordDto, String>(
				new VocabTextInputCell(this)) {
			@Override
			public String getValue(WordDto obj) {
				return Strings.nullToEmpty(obj.getExample());
			}

		};
		exampleColumn.setFieldUpdater(fieldUpdater);
		return exampleColumn;
	}

	private Column<WordDto, String> createAnswerColumn(
			FieldUpdater<WordDto, String> fieldUpdater) {

		Column<WordDto, String> answerColumn = new Column<WordDto, String>(
				new VocabTextInputCell(this)) {
			@Override
			public String getValue(WordDto obj) {
				return Strings.nullToEmpty(obj.getAnswer());
			}

		};
		answerColumn.setFieldUpdater(fieldUpdater);
		return answerColumn;
	}

	private Column<WordDto, String> createExpressionColumn(
			FieldUpdater<WordDto, String> fieldUpdater) {
		Column<WordDto, String> expressionColumn = new Column<WordDto, String>(
				new VocabTextInputCell(this)) {
			@Override
			public String getValue(WordDto obj) {
				return Strings.nullToEmpty(obj.getExpression());
			}

		};
		expressionColumn.setFieldUpdater(fieldUpdater);
		return expressionColumn;
	}

	@Override
	public void hideForm() {
		formPanel.setVisible(false);
	}

	@Override
	public void showConfirmation() {
		confirmationPanel.setVisible(true);
		this.confirmationShown = true;
	}

	private void hideConfirmation() {
		confirmationPanel.setVisible(false);
	}

	private void hideError() {
		errorPanel.setVisible(false);
	}

	@Override
	public void showError(ErrorCode errorCode) {
		hideForm();
		confirmationPanel.setVisible(false);
		errorPanel.setVisible(true);
	}

	@Override
	public void eventOcurred(String value) {
		enableSendButtonIfPossible(value);

	}

	@Override
	public void markWithError(WordProxy word, String message) {
		for (int i = 0; i < cellTable.getVisibleItemCount(); i++) {
			WordDto wordInTable = cellTable.getVisibleItem(i);
			if (Objects.equal(wordInTable.getAnswer(), word.getAnswer())
					&& Objects.equal(wordInTable.getExample(),
							word.getExample())
					&& Objects.equal(wordInTable.getExpression(),
							word.getExpression())) {
				cellTable.getRowElement(i).getStyle().setBackgroundColor("red");
			}
		}
	}

	@Override
	public void tabChanged(TabPlace newTab) {
		if (TabPlace.NEWWORDS.equals(newTab)) {
			show();
		} else {
			hide();
		}

	}

	@Override
	public void show() {
		setVisible(true);
		if (confirmationShown) {
			restartNewWordsDefaultView();
		} else {
			showForm();
		}

	}

	@Override
	public void hide() {
		hideForm();
		hideConfirmation();
		hideError();
		setVisible(false);
	}

}
