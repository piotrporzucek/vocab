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

import pl.egalit.vocab.client.core.css.CssResources;
import pl.egalit.vocab.client.core.resources.VocabConstants;
import pl.egalit.vocab.client.places.CoursePlace;
import pl.egalit.vocab.client.places.CoursePlace.TabPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Sample implementation of {@link CourseDetailsView}.
 */
public class CourseDetailsViewImpl extends Composite implements
		CourseDetailsView {

	interface Binder extends UiBinder<Widget, CourseDetailsViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter presenter;

	@UiField(provided = true)
	TabBar tabBar;

	private final PlaceController placeController;

	@UiField(provided = true)
	NewWordsView newWordsView;

	@UiField(provided = true)
	ArchiveView archiveView;

	private final VocabConstants vocabConstants;

	@Inject
	public CourseDetailsViewImpl(
			@Named("tableRes") CellTable.Resources tableRes,
			final PlaceController placeController, ArchiveView archiveView,
			VocabConstants vocabConstants) {
		this.vocabConstants = vocabConstants;
		this.placeController = placeController;
		this.newWordsView = new NewWordsViewImpl(tableRes, vocabConstants);
		this.archiveView = archiveView;
		this.tabBar = new TabBar();
		CssResources.INSTANCE.css().ensureInjected();
		tabBar.addTab(vocabConstants.newVocabularyTab());
		tabBar.addTab(vocabConstants.archiveTab());
		tabBar.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (tabBar.getTabCount() == 2) {
					placeController.goTo(new CoursePlace(presenter
							.getCourseId(), TabPlace.values()[event
							.getSelectedItem()]));
				} else {
					placeController.goTo(new CoursePlace(presenter
							.getCourseId(), TabPlace.ARCHIVE));
				}

			}
		});
		initWidget(binder.createAndBindUi(this));

	}

	@Override
	public void init(Presenter presenter, boolean archiveMode) {
		this.presenter = presenter;
		archiveView.setPresenter(presenter);
		if (archiveMode) {
			archiveView.show();
			newWordsView.hide();
			tabBar.removeTab(0);

		} else {
			archiveView.hide();
			newWordsView.setPresenter(presenter);
			newWordsView.show();
		}
		tabBar.selectTab(0);

	}

	@Override
	public void setTab(TabPlace newTab) {
		newWordsView.tabChanged(newTab);
		archiveView.tabChanged(newTab);

	}

	@Override
	public void showError(ErrorCode unknownCourse) {
		tabBar.setVisible(false);
		newWordsView.hide();
		archiveView.hide();
		Window.alert(vocabConstants.unknownCourse());

	}

	@Override
	public NewWordsView getNewWordsView() {
		return newWordsView;
	}

	@Override
	public ArchiveView getArchiveView() {
		return archiveView;
	}

}
