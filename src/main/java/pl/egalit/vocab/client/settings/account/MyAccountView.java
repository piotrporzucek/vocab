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
package pl.egalit.vocab.client.settings.account;

import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.core.mvp.VocabView;

/**
 * View base interface. Extends IsWidget so a view impl can easily provide its
 * container widget.
 */
public interface MyAccountView extends VocabView {

	public interface Presenter {

		void changeAccount(UserDto modifiedUser, String name, String email);

		void changePassword(UserDto modifiedUser, String text, String text2);

	}

	void setErrorEmail(String string);

	void init(Presenter presenter, UserDto user);

	void setError(String message);

	void setErrorName(String message);

	void setErrorPassword(String password);

}
