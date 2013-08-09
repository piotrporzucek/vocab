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

import pl.egalit.vocab.client.requestfactory.CourseProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * Sample implementation of {@link ListCoursesView}.
 */
public class ListCoursesViewImpl extends Composite implements ListCoursesView {

	interface Binder extends UiBinder<Widget, ListCoursesViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;

	@UiField(provided = true)
	CoursesPanel activeCoursesPanel;
	@UiField(provided = true)
	CoursesPanel archiveCoursesPanel;

	private boolean activeCoursesLoaded;

	private boolean archiveCoursesLoaded;

	@Inject
	public ListCoursesViewImpl(CoursesPanel archiveCoursesPanel,
			CoursesPanel activeCoursesPanel) {
		this.activeCoursesPanel = activeCoursesPanel;
		this.archiveCoursesPanel = archiveCoursesPanel;
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void setArchiveCourses(List<CourseProxy> response) {
		this.archiveCoursesPanel.addCourses(response);
		this.archiveCoursesLoaded = true;

	}

	@Override
	public void setActiveCourses(List<CourseProxy> response) {
		activeCoursesPanel.addCourses(response);
		this.activeCoursesLoaded = true;
	}

	@Override
	public boolean isInitialized() {
		return activeCoursesLoaded && archiveCoursesLoaded;
	}

	@Override
	public void setChosenCourseId(Long chosenCourseId) {
		archiveCoursesPanel.setChosenCourseId(chosenCourseId);
		activeCoursesPanel.setChosenCourseId(chosenCourseId);

	}

}
