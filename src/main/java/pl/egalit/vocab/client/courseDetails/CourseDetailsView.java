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
package pl.egalit.vocab.client.courseDetails;

import java.util.List;

import pl.egalit.vocab.client.core.mvp.VocabView;
import pl.egalit.vocab.client.courseDetails.model.WordDto;
import pl.egalit.vocab.client.places.CoursePlace.TabPlace;

import com.google.gwt.place.shared.Place;

/**
 * View base interface. Extends IsWidget so a view impl can easily provide its
 * container widget.
 */
public interface CourseDetailsView extends VocabView {

	void init(Presenter listener, boolean archiveMode);

	public interface Presenter {
		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);

		List<WordDto> createEmptyWords(int i);

		long getCourseId();

		void loadWordsUnits();

		void sendWords(List<WordDto> list);

	}

	void setTab(TabPlace tabPlace);

	void showError(ErrorCode unknownCourse);

	NewWordsView getNewWordsView();

	ArchiveView getArchiveView();

}
