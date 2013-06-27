package pl.egalit.vocab.client.settings.course;

import java.util.Comparator;
import java.util.List;

import pl.egalit.vocab.client.core.dto.CourseDto;
import pl.egalit.vocab.client.core.resources.MyRes;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.places.ChangeCoursePlace;
import pl.egalit.vocab.client.settings.course.widget.CourseActionCell;
import pl.egalit.vocab.client.settings.widgets.VocabActionCell;

import com.google.common.collect.Lists;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

public class SearchCourseViewImpl extends Composite implements SearchCourseView {

	@UiField(provided = true)
	CellTable<CourseDto> cellTable;

	private MyRes myRes;

	private TextColumn<CourseDto> nameColumn;

	private TextColumn<CourseDto> periodColumn;

	private TextColumn<CourseDto> activeColumn;

	private VocabConstants vocabConstants;

	private static SearchLectorViewImplUiBinder uiBinder = GWT
			.create(SearchLectorViewImplUiBinder.class);

	interface SearchLectorViewImplUiBinder extends
			UiBinder<Widget, SearchCourseViewImpl> {
	}

	@Inject
	public SearchCourseViewImpl(MyRes myRes, final VocabConstants vocabConstants) {
		this.myRes = myRes;
		this.vocabConstants = vocabConstants;
		myRes.css().ensureInjected();
		cellTable = new CellTable<CourseDto>();
		// Create name column.
		nameColumn = new TextColumn<CourseDto>() {
			@Override
			public String getValue(CourseDto course) {
				return course.getName();
			}
		};
		nameColumn.setSortable(true);

		periodColumn = new TextColumn<CourseDto>() {
			@Override
			public String getValue(CourseDto course) {
				DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");
				return fmt.format(course.getStartDate()) + " - "
						+ fmt.format(course.getEndDate());
			}
		};
		periodColumn.setSortable(true);

		activeColumn = new TextColumn<CourseDto>() {
			@Override
			public String getValue(CourseDto course) {
				return course.isActive() ? vocabConstants.yes()
						: vocabConstants.no();
			}
		};
		activeColumn.setSortable(true);

		// Add the columns.
		cellTable.addColumn(nameColumn, vocabConstants.nameColumn());
		cellTable.addColumn(periodColumn, vocabConstants.period());
		cellTable.addColumn(activeColumn, vocabConstants.active());
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void setupActionsColumn(MyRes myRes) {
		List<HasCell<CourseDto, ?>> list = Lists.newArrayList();
		list.add(new ActionHasCell(myRes.css().modifyButton(), vocabConstants
				.change(), new Delegate<CourseDto>() {
			@Override
			public void execute(CourseDto user) {
				presenter.goTo(new ChangeCoursePlace(user.getId()));

			}
		}));

		CompositeCell<CourseDto> actions = new CompositeCell<CourseDto>(list);

		cellTable.addColumn(new Column<CourseDto, CourseDto>(actions) {

			@Override
			public CourseDto getValue(CourseDto object) {
				return object;
			}
		}, "Akcje");
	}

	private Presenter presenter;

	private ListDataProvider<CourseDto> dataProvider;

	public SearchCourseViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.presenter = listener;
		this.presenter.loadUsers();
	}

	@Override
	public void initView(List<CourseDto> courses) {

		dataProvider = new ListDataProvider<CourseDto>(courses);
		// Connect the list to the data provider.
		dataProvider.addDataDisplay(cellTable);

		setupSorting();

	}

	private void setupSorting() {
		ListHandler<CourseDto> columnSortHandler = new ListHandler<CourseDto>(
				dataProvider.getList());
		columnSortHandler.setComparator(nameColumn,
				new Comparator<CourseDto>() {
					@Override
					public int compare(CourseDto o1, CourseDto o2) {
						String name1 = o1 != null && o1.getName() != null ? o1
								.getName() : "";
						String name2 = o2 != null && o2.getName() != null ? o2
								.getName() : "";
						return name1.compareTo(name2);

					}
				});

		columnSortHandler.setComparator(periodColumn,
				new Comparator<CourseDto>() {
					@Override
					public int compare(CourseDto o1, CourseDto o2) {
						return o1.getStartDate().before(o2.getStartDate()) ? 1
								: o1.getStartDate().after(o2.getStartDate()) ? -1
										: 0;

					}
				});

		columnSortHandler.setComparator(activeColumn,
				new Comparator<CourseDto>() {
					@Override
					public int compare(CourseDto o1, CourseDto o2) {
						boolean admin1 = o1 != null && o1.isActive() ? o1
								.isActive() : false;
						boolean admin2 = o2 != null && o2.isActive() ? o2
								.isActive() : false;
						return admin1 == admin2 ? 0 : (admin1 ? 1 : -1);

					}
				});

		cellTable.addColumnSortHandler(columnSortHandler);

		// We know that the data is sorted alphabetically by default.
		cellTable.getColumnSortList().push(nameColumn);
	}

	@Override
	public void setAdminMode(boolean isCurrentUserAdmin) {
		if (isCurrentUserAdmin) {
			setupActionsColumn(myRes);
		}
	}

	private class ActionHasCell implements HasCell<CourseDto, CourseDto> {
		private final VocabActionCell<CourseDto> cell;

		public ActionHasCell(String className, String description,
				Delegate<CourseDto> delegate) {
			cell = new CourseActionCell(className,
					SafeHtmlUtils.fromString(description), delegate);
		}

		@Override
		public Cell<CourseDto> getCell() {
			return cell;
		}

		@Override
		public FieldUpdater<CourseDto, CourseDto> getFieldUpdater() {
			return null;
		}

		@Override
		public CourseDto getValue(CourseDto object) {
			return object;
		}
	}

}