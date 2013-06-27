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
package pl.egalit.vocab.client.entity;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

/**
 * A proxy object containing device registration information: email account
 * name, device id, and device registration id.
 */
@ProxyForName(value = "pl.egalit.vocab.server.entity.UserEntity", locator = "pl.egalit.vocab.server.core.ObjectifyLocator")
public interface UserProxy extends EntityProxy {
	Long getId();

	Integer getVersion();

	Long getSchoolId();

	void setEmail(String email);

	String getEmail();

	void setPassword(String password);

	String getPassword();

	void setName(String name);

	String getName();

	boolean isAdmin();

	List<CourseProxy> getCourseEntities();

	void setCourseEntities(List<CourseProxy> courses);

	void setId(Long id);

}
