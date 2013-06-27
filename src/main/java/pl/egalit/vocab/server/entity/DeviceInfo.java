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
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Registration info.
 * 
 * An account may be associated with multiple phones, and a phone may be
 * associated with multiple accounts.
 * 
 * registrations lists different phones registered to that account.
 */
@Entity
public class DeviceInfo {
	private static final Logger log = Logger.getLogger(DeviceInfo.class
			.getName());

	/**
	 * User-email # device-id
	 * 
	 * Device-id can be specified by device, default is hash of abs(registration
	 * id).
	 * 
	 * user@example.com#1234
	 */
	@Id
	private String id;

	/**
	 * The ID used for sending messages to.
	 */

	private String deviceRegistrationID;

	/**
	 * Current supported types: (default) - ac2dm, regular froyo+ devices using
	 * C2DM protocol
	 * 
	 * New types may be defined - for example for sending to chrome.
	 */

	private String type;

	/**
	 * For statistics - and to provide hints to the user.
	 */

	private Date registrationTimestamp;

	private Boolean debug;

	private String schoolId;

	public DeviceInfo() {
		// TODO Auto-generated constructor stub
	}

	public DeviceInfo(Key<DeviceInfo> key, String deviceRegistrationID) {
		log.info("new DeviceInfo: deviceRegistrationId=" + deviceRegistrationID);
		this.id = key.getName();
		this.deviceRegistrationID = deviceRegistrationID;
		this.setRegistrationTimestamp(new Date()); // now
	}

	// Accessor methods for properties added later (hence can be null)

	public String getDeviceRegistrationID() {
		log.info("DeviceInfo: return deviceRegistrationID="
				+ deviceRegistrationID);
		return deviceRegistrationID;
	}

	public void setDeviceRegistrationID(String deviceRegistrationID) {
		log.info("DeviceInfo: set deviceRegistrationID=" + deviceRegistrationID);
		this.deviceRegistrationID = deviceRegistrationID;
	}

	public boolean getDebug() {
		return (debug != null ? debug.booleanValue() : false);
	}

	public void setDebug(boolean debug) {
		this.debug = new Boolean(debug);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type != null ? type : "";
	}

	public void setRegistrationTimestamp(Date registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
	}

	public Date getRegistrationTimestamp() {
		return registrationTimestamp;
	}

	/**
	 * Helper function - will query all registrations for a user.
	 */
	@SuppressWarnings("unchecked")
	public static List<DeviceInfo> getDeviceInfoForUser(String user) {
		// PersistenceManager pm = PMF.get().getPersistenceManager();
		// try {
		// // Canonicalize user name
		// user = user.toLowerCase(Locale.ENGLISH);
		// Query query = pm.newQuery(DeviceInfo.class);
		// query.setFilter("key >= '" + user + "' && key < '" + user + "$'");
		// List<DeviceInfo> qresult = (List<DeviceInfo>) query.execute();
		// // Copy to array - we need to close the query
		// List<DeviceInfo> result = new ArrayList<DeviceInfo>();
		// for (DeviceInfo di : qresult) {
		// result.add(di);
		// }
		// query.closeAll();
		// log.info("Return " + result.size() + " devices for user " + user);
		// return result;
		// } finally {
		// pm.close();
		// }
		return null;
	}

	@Override
	public String toString() {
		return "DeviceInfo[id=" + id + ", deviceRegistrationID="
				+ deviceRegistrationID + ", type=" + type
				+ ", registrationTimestamp=" + registrationTimestamp
				+ ", debug=" + debug + "]";
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public static DeviceInfo getOldestDeviceInfo(List<DeviceInfo> registrations) {
		DeviceInfo oldest = registrations.get(0);
		if (oldest.getRegistrationTimestamp() != null) {

			long oldestTime = oldest.getRegistrationTimestamp().getTime();
			for (int i = 1; i < registrations.size(); i++) {
				if (registrations.get(i).getRegistrationTimestamp().getTime() < oldestTime) {
					oldest = registrations.get(i);
					oldestTime = oldest.getRegistrationTimestamp().getTime();
				}

			}
		}
		return oldest;
	}

	public static DeviceInfo getNewestDeviceInfo(String user) {
		List<DeviceInfo> registrations = getDeviceInfoForUser(user);
		DeviceInfo newest = registrations.get(0);
		if (newest.getRegistrationTimestamp() != null) {
			long newestTime = newest.getRegistrationTimestamp().getTime();
			for (int i = 1; i < registrations.size(); i++) {
				if (registrations.get(i).getRegistrationTimestamp().getTime() > newestTime) {
					newest = registrations.get(i);
					newestTime = newest.getRegistrationTimestamp().getTime();
				}

			}
		}
		return newest;
	}

	public String getId() {
		return id;
	}

	public void setId(String key) {
		this.id = key;
	}
}
