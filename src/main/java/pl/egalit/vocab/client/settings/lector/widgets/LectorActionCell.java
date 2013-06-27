package pl.egalit.vocab.client.settings.lector.widgets;

import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.settings.widgets.VocabActionCell;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class LectorActionCell extends VocabActionCell<UserDto> {

	public LectorActionCell(String className, SafeHtml description,
			Delegate<UserDto> delegate) {
		super(className, description, delegate);
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			UserDto user, SafeHtmlBuilder sb) {
		if (!user.isAdmin()) {
			super.render(context, user, sb);
		}

	}

}
