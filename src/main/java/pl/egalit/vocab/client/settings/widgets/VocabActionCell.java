package pl.egalit.vocab.client.settings.widgets;

import static com.google.gwt.dom.client.BrowserEvents.CLICK;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public abstract class VocabActionCell<C> extends AbstractCell<C> {

	private final Delegate<C> delegate;
	private final SafeHtml html;

	/**
	 * The HTML templates used to render the cell.
	 */
	interface Templates extends SafeHtmlTemplates {
		/**
		 * The template for this Cell, which includes styles and a value.
		 * 
		 * @param description
		 * 
		 * @param styles
		 *            the styles to include in the style attribute of the div
		 * @param value
		 *            the safe value. Since the value type is {@link SafeHtml},
		 *            it will not be escaped before including it in the
		 *            template. Alternatively, you could make the value type
		 *            String, in which case the value would be escaped.
		 * @return a {@link SafeHtml} instance
		 */

		@SafeHtmlTemplates.Template("<input type=\"submit\" value=\"\" class=\"{0}\" /><label>{1}</label>")
		SafeHtml cell(String className, SafeHtml description);
	}

	/**
	 * Create a singleton instance of the templates used to render the cell.
	 */
	private static Templates templates = GWT.create(Templates.class);

	public VocabActionCell(String className, SafeHtml description,
			com.google.gwt.cell.client.ActionCell.Delegate<C> delegate) {
		super(CLICK);
		this.delegate = delegate;
		this.html = templates.cell(className, description);

	}

	@Override
	public void onBrowserEvent(Context context, Element parent, C value,
			NativeEvent event, ValueUpdater<C> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if (CLICK.equals(event.getType())) {
			EventTarget eventTarget = event.getEventTarget();
			if (!Element.is(eventTarget)) {
				return;
			}
			onEnterKeyDown(context, parent, value, event, valueUpdater);
		}
	}

	@Override
	public void render(Context context, C value, SafeHtmlBuilder sb) {
		sb.append(html);

	}

	@Override
	protected void onEnterKeyDown(Context context, Element parent, C value,
			NativeEvent event, ValueUpdater<C> valueUpdater) {
		delegate.execute(value);
	}

}
