package pl.egalit.vocab.client.settings.lector;

import java.util.Comparator;
import java.util.List;

import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.core.resources.MyRes;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.places.ChangeLectorPlace;
import pl.egalit.vocab.client.settings.lector.widgets.LectorActionCell;
import pl.egalit.vocab.client.settings.widgets.VocabActionCell;

import com.google.common.collect.Lists;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

public class SearchLectorViewImpl extends Composite implements SearchLectorView {

	@UiField(provided = true)
	DataGrid<UserDto> cellTable;

	private MyRes myRes;

	private TextColumn<UserDto> nameColumn;

	private TextColumn<UserDto> adminColumn;

	private TextColumn<UserDto> emailColumn;

	private VocabConstants vocabConstants;

	private static SearchLectorViewImplUiBinder uiBinder = GWT
			.create(SearchLectorViewImplUiBinder.class);

	interface SearchLectorViewImplUiBinder extends
			UiBinder<Widget, SearchLectorViewImpl> {
	}

	@Inject
	public SearchLectorViewImpl(MyRes myRes, final VocabConstants vocabConstants) {
		this.myRes = myRes;
		this.vocabConstants = vocabConstants;
		myRes.css().ensureInjected();
		cellTable = new DataGrid<UserDto>();
		// Create name column.
		nameColumn = new TextColumn<UserDto>() {
			@Override
			public String getValue(UserDto user) {
				return user.getName();
			}
		};
		nameColumn.setSortable(true);
		// Create address column.
		emailColumn = new TextColumn<UserDto>() {
			@Override
			public String getValue(UserDto user) {
				return user.getEmail();
			}
		};
		emailColumn.setSortable(true);
		adminColumn = new TextColumn<UserDto>() {
			@Override
			public String getValue(UserDto user) {
				return user.isAdmin() ? vocabConstants.yes() : vocabConstants
						.no();
			}
		};
		adminColumn.setSortable(true);

		// Add the columns.
		cellTable.addColumn(nameColumn, vocabConstants.name());
		cellTable.addColumn(emailColumn, vocabConstants.email());
		cellTable.addColumn(adminColumn, vocabConstants.administrator());

		initWidget(uiBinder.createAndBindUi(this));
	}

	private void setupActionsColumn(MyRes myRes) {
		List<HasCell<UserDto, ?>> list = Lists.newArrayList();
		list.add(new ActionHasCell(myRes.css().modifyButton(), vocabConstants
				.change(), new Delegate<UserDto>() {
			@Override
			public void execute(UserDto user) {
				presenter.goTo(new ChangeLectorPlace(user.getId()));

			}
		}));
		list.add(new ActionHasCell(myRes.css().deleteButton(), vocabConstants
				.delete(), new Delegate<UserDto>() {
			@Override
			public void execute(UserDto user) {
				if (Window.confirm(vocabConstants.deleteLectorConfirmation())) {
					presenter.removeUser(user.getId());
				}

			}
		}));
		CompositeCell<UserDto> actions = new CompositeCell<UserDto>(list);

		cellTable.addColumn(new Column<UserDto, UserDto>(actions) {

			@Override
			public UserDto getValue(UserDto object) {
				return object;
			}
		}, vocabConstants.actions());
	}

	private Presenter presenter;

	private ListDataProvider<UserDto> dataProvider;

	public SearchLectorViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.presenter = listener;
		this.presenter.loadUsers();
	}

	@Override
	public void initView(List<UserDto> users) {

		dataProvider = new ListDataProvider<UserDto>(users);
		// Connect the list to the data provider.
		dataProvider.addDataDisplay(cellTable);
		setupSorting();

	}

	private void setupSorting() {
		ListHandler<UserDto> columnSortHandler = new ListHandler<UserDto>(
				dataProvider.getList());
		columnSortHandler.setComparator(nameColumn, new Comparator<UserDto>() {
			@Override
			public int compare(UserDto o1, UserDto o2) {
				String name1 = o1 != null && o1.getName() != null ? o1
						.getName() : "";
				String name2 = o2 != null && o2.getName() != null ? o2
						.getName() : "";
				return name1.compareTo(name2);

			}
		});
		columnSortHandler.setComparator(emailColumn, new Comparator<UserDto>() {
			@Override
			public int compare(UserDto o1, UserDto o2) {
				String email1 = o1 != null && o1.getEmail() != null ? o1
						.getEmail() : "";
				String email2 = o2 != null && o2.getEmail() != null ? o2
						.getEmail() : "";
				return email1.compareTo(email2);

			}
		});
		columnSortHandler.setComparator(adminColumn, new Comparator<UserDto>() {
			@Override
			public int compare(UserDto o1, UserDto o2) {
				boolean admin1 = o1 != null && o1.isAdmin() ? o1.isAdmin()
						: false;
				boolean admin2 = o2 != null && o2.isAdmin() ? o2.isAdmin()
						: false;
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

	private class ActionHasCell implements HasCell<UserDto, UserDto> {
		private final VocabActionCell<UserDto> cell;

		public ActionHasCell(String className, String description,
				Delegate<UserDto> delegate) {
			cell = new LectorActionCell(className,
					SafeHtmlUtils.fromString(description), delegate);
		}

		@Override
		public Cell<UserDto> getCell() {
			return cell;
		}

		@Override
		public FieldUpdater<UserDto, UserDto> getFieldUpdater() {
			return null;
		}

		@Override
		public UserDto getValue(UserDto object) {
			return object;
		}
	}

}
