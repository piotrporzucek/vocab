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
package pl.egalit.vocab.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * A place object representing a particular state of the UI. A Place can be
 * converted to and from a URL history token by defining a
 * {@link PlaceTokenizer} for each {@link Place}, and the
 * {@link PlaceHistoryHandler} automatically updates the browser URL
 * corresponding to each {@link Place} in your app.
 */
public class MyAccountPlace extends Place {

	/**
	 * Sample property (stores token).
	 */
	private final String name = "myaccount";

	public String getName() {
		return name;
	}

	/**
	 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
	 */
	public static class Tokenizer implements PlaceTokenizer<MyAccountPlace> {

		@Override
		public String getToken(MyAccountPlace place) {
			return place.getName();
		}

		@Override
		public MyAccountPlace getPlace(String token) {
			return new MyAccountPlace();
		}

	}
}
