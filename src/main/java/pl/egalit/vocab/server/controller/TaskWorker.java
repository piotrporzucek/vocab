package pl.egalit.vocab.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.egalit.vocab.server.dao.MulticastDao;
import pl.egalit.vocab.server.dao.WordsUnitDao;
import pl.egalit.vocab.server.entity.Multicast;
import pl.egalit.vocab.server.service.RegistrationInfoService;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.googlecode.objectify.Key;

@Controller
@RequestMapping("/task")
public class TaskWorker {
	private static final String HEADER_QUEUE_COUNT = "X-AppEngine-TaskRetryCount";
	private static final String HEADER_QUEUE_NAME = "X-AppEngine-QueueName";
	private static final int MAX_RETRY = 3;
	public static final String PARAMETER_MULTICAST = "multicastKey";

	private static final String API_KEY = "AIzaSyAlkI_A-2HkYa2Wzl0yYT4tAndmfooDPE4";

	protected final Logger logger = Logger.getLogger(getClass().getName());

	private final Sender sender = new Sender(API_KEY);

	@Autowired
	private MulticastDao multicastDao;

	@Autowired
	private WordsUnitDao wordUnitDao;

	@Autowired
	private RegistrationInfoService registrationInfoService;

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void send(
			@RequestHeader(value = HEADER_QUEUE_NAME, required = false) String queueName,
			@RequestHeader(value = HEADER_QUEUE_COUNT, required = false) Integer retryCount,
			@RequestParam(value = PARAMETER_MULTICAST, required = false) Long multicastId)
			throws IOException {

		if (queueName == null) {
			throw new IOException("Missing header " + HEADER_QUEUE_NAME);
		}

		if (retryCount > MAX_RETRY) {
			return;
		}
		if (multicastId != null) {
			sendMulticastMessage(multicastId);
			return;
		}

	}

	private void multicastDone(long multicastId) {
		Key<Multicast> key = Key.create(Multicast.class, multicastId);
		multicastDao.deleteKey(key);

	}

	private Message createMessage(Long courseId) {

		Message message = new Message.Builder().addData("courseId",
				"" + courseId).build();
		return message;
	}

	private void sendMulticastMessage(long multicastKey) {
		Multicast multicast = multicastDao.getById(multicastKey);
		// Recover registration ids from datastore
		List<String> regIds = multicast.getRegIds();
		long courseId = multicast.getCourseKey().getId();
		Message message = createMessage(courseId);
		MulticastResult multicastResult;
		try {
			multicastResult = sender.sendNoRetry(message, regIds);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Exception posting " + message, e);
			multicastDone(multicastKey);
			return;
		}
		boolean allDone = true;
		// check if any registration id must be updated
		if (multicastResult.getCanonicalIds() != 0) {
			List<Result> results = multicastResult.getResults();
			for (int i = 0; i < results.size(); i++) {
				String canonicalRegId = results.get(i)
						.getCanonicalRegistrationId();
				if (canonicalRegId != null) {
					String regId = regIds.get(i);
					registrationInfoService.updateRegistrationId(regId,
							canonicalRegId);
				}
			}
		}
		if (multicastResult.getFailure() != 0) {
			// there were failures, check if any could be retried
			List<Result> results = multicastResult.getResults();
			List<String> retriableRegIds = new ArrayList<String>();
			for (int i = 0; i < results.size(); i++) {
				String error = results.get(i).getErrorCodeName();
				if (error != null) {
					String regId = regIds.get(i);
					logger.warning("Got error (" + error + ") for regId "
							+ regId);
					if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
						// application has been removed from device - unregister
						// it
						registrationInfoService.unregister(regId);
					}
					if (error.equals(Constants.ERROR_UNAVAILABLE)) {
						retriableRegIds.add(regId);
					}
				}
			}
			if (!retriableRegIds.isEmpty()) {
				// update task
				multicastDao.updateMulticast(multicastKey, retriableRegIds);
				allDone = false;
				retryTask();
			}
		}
		if (allDone) {
			multicastDone(multicastKey);
		} else {
			retryTask();
		}
	}

	private void retryTask() {
		// TODO Auto-generated method stub

	}
}
