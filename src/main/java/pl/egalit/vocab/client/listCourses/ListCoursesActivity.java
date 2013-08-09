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
package pl.egalit.vocab.client.listCourses;

import java.util.List;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.requestfactory.CourseProxy;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

/**
 * Activities are started and stopped by an ActivityManager associated with a
 * container Widget.
 */
public class ListCoursesActivity extends AbstractVocabActivity<ListCoursesView>
		implements ListCoursesView.Presenter {

	private Long chosenCourseId;

	@Inject
	public ListCoursesActivity(PlaceController placeController,
			MyRequestFactory requestFactory, ListCoursesView startView) {
		super(requestFactory, placeController, startView);

	}

	public ListCoursesActivity(PlaceController placeController,
			MyRequestFactory requestFactory, ListCoursesView listCoursesView,
			Long courseId) {
		this(placeController, requestFactory, listCoursesView);
		this.chosenCourseId = courseId;
	}

	@Override
	public String mayStop() {
		return null;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		view.setPresenter(this);
		if (!view.isInitialized()) {
			loadActiveCourses();
			loadArchiveCourses();
		}
		view.setChosenCourseId(getChosenCourseId());
	}

	@Override
	public void loadActiveCourses() {
		requestFactory.courseRequest().getActiveCourses()
				.fire(new VocabReceiver<List<CourseProxy>>(placeController) {
					@Override
					public void onSuccess(List<CourseProxy> response) {
						view.setActiveCourses(response);

					}

				});

	}

	@Override
	public void loadArchiveCourses() {
		requestFactory.courseRequest().getArchiveCourses()
				.fire(new VocabReceiver<List<CourseProxy>>(placeController) {
					@Override
					public void onSuccess(List<CourseProxy> response) {
						view.setArchiveCourses(response);

					}

				});

	}

	public void setChosenCourseId(Long courseId) {
		this.chosenCourseId = courseId;
		view.setChosenCourseId(courseId);
	}

	@Override
	public Long getChosenCourseId() {
		return chosenCourseId;
	}
}
