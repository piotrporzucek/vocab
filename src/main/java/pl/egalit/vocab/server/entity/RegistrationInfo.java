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
import java.util.logging.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Registration info.
 * 
 * An account may be associated with multiple phones, and a phone may be
 * associated with multiple accounts.
 * 
 * registrations lists different phones registered to that account.
 */
@Entity
public class RegistrationInfo extends DatastoreObject {
	private static final Logger log = Logger.getLogger(RegistrationInfo.class
			.getName());

	/**
	 * The ID used for sending messages to.
	 */

	@Index
	private String deviceRegistrationId;

	/**
	 * Current supported types: (default) - ac2dm, regular froyo+ devices using
	 * C2DM protocol
	 * 
	 * New types may be defined - for example for sending to chrome.
	 */

	/**
	 * For statistics - and to provide hints to the user.
	 */

	private Date registrationTimestamp;

	@Index
	private long schoolId;

	public RegistrationInfo() {
		// TODO Auto-generated constructor stub
	}

	public RegistrationInfo(String deviceRegistrationId) {
		log.info("new RegistrationInfo: deviceRegistrationId="
				+ deviceRegistrationId);

		this.deviceRegistrationId = deviceRegistrationId;
		this.setRegistrationTimestamp(new Date()); // now
	}

	// Accessor methods for properties added later (hence can be null)

	public String getDeviceRegistrationId() {
		log.info("RegistrationInfo: return deviceRegistrationId="
				+ deviceRegistrationId);
		return deviceRegistrationId;
	}

	public void setDeviceRegistrationId(String deviceRegistrationId) {
		log.info("RegistrationInfo: set deviceRegistrationId="
				+ deviceRegistrationId);
		this.deviceRegistrationId = deviceRegistrationId;
	}

	public void setRegistrationTimestamp(Date registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
	}

	public Date getRegistrationTimestamp() {
		return registrationTimestamp;
	}

	@Override
	public String toString() {
		return "RegistrationInfo[id=" + id + ", deviceRegistrationId="
				+ deviceRegistrationId + ", registrationTimestamp="
				+ registrationTimestamp + "]";
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

	public long getSchoolId() {
		return schoolId;
	}

}
