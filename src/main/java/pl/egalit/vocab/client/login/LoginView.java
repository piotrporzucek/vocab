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
package pl.egalit.vocab.client.login;

import java.util.List;
import java.util.Set;

import pl.egalit.vocab.client.core.mvp.VocabView;
import pl.egalit.vocab.client.requestfactory.SchoolProxy;

import com.google.gwt.place.shared.Place;

/**
 * View base interface. Extends IsWidget so a view impl can easily provide its
 * container widget.
 */
public interface LoginView extends VocabView {

	void setName(String helloName);

	void setPresenter(Presenter listener);

	void setErrorMessage(List<String> messages);

	void setErrorMessage(String messages);

	public interface Presenter {
		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);

		void login(String username, String password);

		void login(String username, String password, Long id);

		void recoverForgottenPassword(String text);
	}

	void openSchoolSelectionPopup(Set<SchoolProxy> response);

	void forgottenPasswordRestoreSucceeded();

	void forgottenPasswordRestoreFailure();
}
