package pl.egalit.vocab.client.core.css;

import com.google.gwt.user.cellview.client.CellTable;

public interface TableRes extends CellTable.Resources {
	@Override
	@Source({ CellTable.Style.DEFAULT_CSS, "css/Table.css" })
	TableStyle cellTableStyle();

	interface TableStyle extends CellTable.Style {
	}
}