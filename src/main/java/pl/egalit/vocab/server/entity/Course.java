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
package pl.egalit.vocab.server.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Course extends DatastoreObject {

	@Parent
	@Load
	private Ref<School> school;

	@NotNull(message = "{Course.name.empty}")
	@Index
	private String name;
	@Index
	private String description;
	@NotNull(message = "{Course.startDate.empty}")
	private Date startDate;
	@NotNull(message = "{Course.endDate.empty}")
	private Date endDate;
	@Index
	private boolean active;

	@NotNull(message = "{Course.language.empty}")
	private String language;

	@NotNull(message = "{Course.password.empty}")
	private String password;

	@Index
	private long lastUpdateTime;

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Course)) {
			return false;
		}
		Course other = (Course) obj;
		return getId() == other.getId();
	}

	public static Course findCourse(Long id) {
		return new Course();
	}

	@Override
	public int hashCode() {
		return 17;
	}

	public Ref<School> getSchool() {
		return school;
	}

	public void setSchool(Ref<School> school) {
		this.school = school;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;

	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
