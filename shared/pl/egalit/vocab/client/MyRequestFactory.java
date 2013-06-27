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

import java.util.List;
import java.util.Set;

import pl.egalit.vocab.client.entity.CourseProxy;
import pl.egalit.vocab.client.entity.MessageProxy;
import pl.egalit.vocab.client.entity.RegistrationInfoProxy;
import pl.egalit.vocab.client.entity.SchoolProxy;
import pl.egalit.vocab.client.entity.UserProxy;
import pl.egalit.vocab.client.entity.WordProxy;
import pl.egalit.vocab.client.entity.WordsUnitProxy;
import pl.egalit.vocab.client.request.LoginRequestProxy;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.ServiceName;

public interface MyRequestFactory extends RequestFactory {

	@ServiceName("pl.egalit.vocab.server.HelloWorldService")
	public interface HelloWorldRequest extends RequestContext {
		/**
		 * Retrieve a "Hello, World" message from the server.
		 */
		Request<String> getMessage();
	}

	@ServiceName(value = "pl.egalit.vocab.server.service.impl.RegistrationInfoServiceImpl", locator = "pl.egalit.vocab.server.service.impl.RegistrationInfoServiceLocator")
	public interface RegistrationInfoRequest extends RequestContext {
		/**
		 * Register a device for C2DM messages.
		 */
		Request<Void> register(RegistrationInfoProxy r);

		/**
		 * Unregister a device for C2DM messages.
		 */
		Request<Void> unregister(RegistrationInfoProxy r);
	}

	@ServiceName(value = "pl.egalit.vocab.server.service.impl.UserAuthentificationServiceImpl", locator = "pl.egalit.vocab.server.service.impl.UserAuthenticationServiceLocator")
	public interface UserAuthentificationRequest extends RequestContext {
		Request<Boolean> isUserAuthenticated();

		Request<Void> login(LoginRequestProxy request);

		Request<String> getUsername();

		Request<Set<SchoolProxy>> getSchoolsForUser(String username);

		Request<Boolean> isCurrentUserAdmin();

		Request<Void> sendPassword(String email);
	}

	@ServiceName(value = "pl.egalit.vocab.server.service.impl.CourseServiceImpl", locator = "pl.egalit.vocab.server.service.impl.CourseServiceLocator")
	public interface CourseRequest extends RequestContext {
		Request<List<CourseProxy>> getActiveCourses();

		Request<List<CourseProxy>> getArchiveCourses();

		Request<CourseProxy> findCourse(long id);

		Request<List<WordsUnitProxy>> getWordsUnits(Long courseId);

		Request<List<CourseProxy>> getAllCourses();

		Request<Void> save(CourseProxy course);

	}

	@ServiceName(value = "pl.egalit.vocab.server.service.impl.WordServiceImpl", locator = "pl.egalit.vocab.server.service.impl.WordServiceLocator")
	public interface WordRequest extends RequestContext {
		Request<Void> saveAndSend(List<WordProxy> words, long courseId);

		Request<List<WordProxy>> getWordsForWordsUnit(long courseId,
				long wordsUnitId);
	}

	@ServiceName(value = "pl.egalit.vocab.server.service.impl.UserServiceImpl", locator = "pl.egalit.vocab.server.service.impl.UserServiceLocator")
	public interface UserRequest extends RequestContext {
		Request<Void> save(UserProxy user, Set<Long> courseIds);

		Request<Void> modify(UserProxy user, Set<Long> courseIds);

		Request<List<UserProxy>> getUsers();

		Request<UserProxy> findUserFromCurrentSchool(Long userId);

		Request<Void> removeUser(long userId);

		Request<UserProxy> getCurrentUserFromCurrentSchool();

	}

	@ServiceName("pl.egalit.vocab.server.Message")
	public interface MessageRequest extends RequestContext {
		/**
		 * Send a message to a device using C2DM.
		 */
		InstanceRequest<MessageProxy, String> send();
	}

	HelloWorldRequest helloWorldRequest();

	RegistrationInfoRequest registrationInfoRequest();

	MessageRequest messageRequest();

	UserAuthentificationRequest authentificationRequest();

	CourseRequest courseRequest();

	WordRequest wordRequest();

	UserRequest userRequest();

}
