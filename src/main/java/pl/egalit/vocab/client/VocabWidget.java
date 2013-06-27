/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package pl.egalit.vocab.client;

import pl.egalit.vocab.client.MyRequestFactory.HelloWorldRequest;
import pl.egalit.vocab.client.MyRequestFactory.MessageRequest;
import pl.egalit.vocab.client.entity.MessageProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class VocabWidget extends Composite {

	private static final int STATUS_DELAY = 4000;
	private static final String STATUS_ERROR = "status error";
	private static final String STATUS_NONE = "status none";
	private static final String STATUS_SUCCESS = "status success";

	interface VocabUiBinder extends UiBinder<Widget, VocabWidget> {
	}

	private static VocabUiBinder uiBinder = GWT.create(VocabUiBinder.class);

	@UiField
	TextAreaElement messageArea;

	@UiField
	InputElement recipientArea;

	@UiField
	DivElement status;

	@UiField
	Button sayHelloButton;

	@UiField
	Button sendMessageButton;

	/**
	 * Timer to clear the UI.
	 */
	Timer timer = new Timer() {
		@Override
		public void run() {
			status.setInnerText("");
			status.setClassName(STATUS_NONE);
			recipientArea.setValue("");
			messageArea.setValue("");
		}
	};

	private void setStatus(String message, boolean error) {
		status.setInnerText(message);
		if (error) {
			status.setClassName(STATUS_ERROR);
		} else {
			if (message.length() == 0) {
				status.setClassName(STATUS_NONE);
			} else {
				status.setClassName(STATUS_SUCCESS);
			}
		}

		timer.schedule(STATUS_DELAY);
	}

	public VocabWidget() {
	}
}
