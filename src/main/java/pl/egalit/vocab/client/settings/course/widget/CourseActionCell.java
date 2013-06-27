package pl.egalit.vocab.client.settings.course.widget;

import pl.egalit.vocab.client.core.dto.CourseDto;
import pl.egalit.vocab.client.settings.widgets.VocabActionCell;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CourseActionCell extends VocabActionCell<CourseDto> {

	public CourseActionCell(String className, SafeHtml description,
			Delegate<CourseDto> delegate) {
		super(className, description, delegate);
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			CourseDto user, SafeHtmlBuilder sb) {
		super.render(context, user, sb);
	}

}
