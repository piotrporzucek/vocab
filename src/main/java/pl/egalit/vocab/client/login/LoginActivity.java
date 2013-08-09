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
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import pl.egalit.vocab.client.MyRequestFactory;
import pl.egalit.vocab.client.MyRequestFactory.UserAuthentificationRequest;
import pl.egalit.vocab.client.core.exceptions.RegisteredInManySchoolsException;
import pl.egalit.vocab.client.core.mvp.AbstractVocabActivity;
import pl.egalit.vocab.client.core.mvp.VocabReceiver;
import pl.egalit.vocab.client.listCourses.ListCoursesView;
import pl.egalit.vocab.client.places.StartPlace;
import pl.egalit.vocab.client.requestfactory.LoginRequestProxy;
import pl.egalit.vocab.client.requestfactory.SchoolProxy;

import com.google.common.collect.Lists;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Activities are started and stopped by an ActivityManager associated with a
 * container Widget.
 */
public class LoginActivity extends AbstractVocabActivity<LoginView> implements
		LoginView.Presenter {
	private static Logger log = Logger.getLogger("NameOfYourLogger");
	private boolean duringAuthorization;
	protected boolean authorized;

	@Inject
	public LoginActivity(PlaceController placeController, LoginView view,
			MyRequestFactory requestFactory) {
		super(requestFactory, placeController, view);
		view.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		super.start(containerWidget, eventBus);

	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see ListCoursesView.Presenter#goTo(Place)
	 */
	@Override
	public void goTo(Place place) {
		placeController.goTo(place);
	}

	@Override
	public void login(final String username, final String password) {
		UserAuthentificationRequest request = requestFactory
				.authentificationRequest();
		final LoginRequestProxy proxy = request.create(LoginRequestProxy.class);
		proxy.setUsername(username);
		proxy.setPassword(password);
		if (!duringAuthorization && !authorized) {
			request.login(proxy).fire(new Receiver<Void>() {
				@Override
				public void onSuccess(Void response) {
					LoginActivity.this.authorized = true;
					LoginActivity.this.duringAuthorization = false;
					goTo(new StartPlace(-1));
				}

				@Override
				public void onFailure(ServerFailure error) {
					if (RegisteredInManySchoolsException.class.getName()
							.equals(error.getExceptionType())) {
						openSchoolSelectionPopup(username);
					} else {
						view.setErrorMessage("Niepoprawny login lub haslo.");
					}
					LoginActivity.this.duringAuthorization = false;
					LoginActivity.this.authorized = false;

				}

				@Override
				public void onConstraintViolation(
						Set<ConstraintViolation<?>> violations) {
					super.onConstraintViolation(violations);

					List<String> messages = Lists
							.newArrayListWithExpectedSize(3);
					for (ConstraintViolation<?> violation : violations) {
						messages.add(violation.getMessage());
					}
					view.setErrorMessage(messages);
					LoginActivity.this.duringAuthorization = false;
					LoginActivity.this.authorized = false;
				}
			});
		}

	}

	@Override
	public void login(String username, String password, Long schoolId) {
		UserAuthentificationRequest request = requestFactory
				.authentificationRequest();
		final LoginRequestProxy proxy = request.create(LoginRequestProxy.class);
		proxy.setUsername(username);
		proxy.setPassword(password);
		proxy.setSchoolId(schoolId);

		request.login(proxy).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				goTo(new StartPlace(-1));
			}

			@Override
			public void onFailure(ServerFailure error) {
				view.setErrorMessage("Niepoprawny login lub haslo.");
			}

			@Override
			public void onConstraintViolation(
					Set<ConstraintViolation<?>> violations) {
				super.onConstraintViolation(violations);

				List<String> messages = Lists.newArrayListWithExpectedSize(3);
				for (ConstraintViolation<?> violation : violations) {
					messages.add(violation.getMessage());
				}
				view.setErrorMessage(messages);
			}
		});

	}

	protected void openSchoolSelectionPopup(String username) {
		VocabReceiver<Set<SchoolProxy>> vocabReceiver = new VocabReceiver<Set<SchoolProxy>>(
				placeController) {
			@Override
			public void onSuccess(Set<SchoolProxy> response) {
				view.openSchoolSelectionPopup(response);

			}
		};
		this.requestFactory.authentificationRequest()
				.getSchoolsForUser(username).fire(vocabReceiver);

	}

	@Override
	protected void userNotAuthenticated() {
		this.panel.setWidget(view);
	}

	@Override
	protected void userAuthenticated() {
		goTo(new StartPlace(null));
	}

	@Override
	public void recoverForgottenPassword(String text) {
		this.requestFactory.authentificationRequest().sendPassword(text)
				.fire(new Receiver<Void>() {
					@Override
					public void onSuccess(Void response) {
						view.forgottenPasswordRestoreSucceeded();
					}

					@Override
					public void onFailure(ServerFailure error) {
						view.forgottenPasswordRestoreFailure();
					}
				});

	}

	public void resetActivity() {
		this.authorized = false;
		this.duringAuthorization = false;
	}

}
