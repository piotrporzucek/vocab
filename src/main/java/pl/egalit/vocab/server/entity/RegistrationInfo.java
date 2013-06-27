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


public class RegistrationInfo {

	String deviceId;

	String deviceRegistrationId;

	String schoolId;

	public RegistrationInfo() {
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getDeviceRegistrationId() {
		return deviceRegistrationId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setDeviceRegistrationId(String deviceRegistrationId) {
		this.deviceRegistrationId = deviceRegistrationId;
	}

	@Override
	public String toString() {
		return "RegistrationInfo [deviceId=" + deviceId
				+ ", deviceRegistrationId=" + deviceRegistrationId + "]";
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

}
