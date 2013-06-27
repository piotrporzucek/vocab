package pl.egalit.vocab.client.core.widget;

import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;

public class VocabTextInputCell extends TextInputCell {
	private final BrowserEventObserver observer;

	public VocabTextInputCell(BrowserEventObserver observer) {
		super();
		this.observer = observer;
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, String value,
			NativeEvent event, ValueUpdater<String> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		String type = event.getType();
		if ("keyup".equals(type) || "keydown".equals(type)
				|| "blur".equals(type)) {
			InputElement elem = getInputElement(parent);
			observer.eventOcurred(elem.getValue());
		}
	}

}
