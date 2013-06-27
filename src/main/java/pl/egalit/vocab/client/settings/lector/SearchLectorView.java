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
package pl.egalit.vocab.client.settings.lector;

import java.util.List;

import pl.egalit.vocab.client.core.dto.UserDto;
import pl.egalit.vocab.client.core.mvp.VocabView;

import com.google.gwt.place.shared.Place;

/**
 * View base interface. Extends IsWidget so a view impl can easily provide its
 * container widget.
 */
public interface SearchLectorView extends VocabView {

	void setPresenter(Presenter presenter);

	public interface Presenter {

		void loadUsers();

		void goTo(Place addLectorPlace);

		void removeUser(long id);

	}

	void initView(List<UserDto> result);

	void setAdminMode(boolean isCurrentUserAdmin);

}