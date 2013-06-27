package pl.egalit.vocab.server.service.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.egalit.vocab.server.dao.DeviceInfoDao;
import pl.egalit.vocab.server.entity.DeviceInfo;
import pl.egalit.vocab.server.entity.RegistrationInfo;
import pl.egalit.vocab.server.service.RegistrationInfoService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;

@Service
public class RegistrationInfoServiceImpl extends AbstractService implements
		RegistrationInfoService {

	@Autowired
	private DeviceInfoDao deviceInfoDao;
	private static final Logger log = Logger.getLogger(RegistrationInfo.class
			.getName());
	private static final int MAX_DEVICES = 5;

	@Override
	public void unregister(RegistrationInfo registrationInfo) {
		log.info("unregister " + this);
		try {
			doUnregister(registrationInfo.getDeviceRegistrationId(),
					getAccountName());
		} catch (Exception e) {
			log.info("Got exception in unregistration: " + e + " - "
					+ e.getMessage());
			for (StackTraceElement ste : e.getStackTrace()) {
				log.info(ste.toString());
			}
		}
		log.info("Successfully unregistered");
	}

	private String getAccountName() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			throw new RuntimeException("No one logged in");
		}
		return user.getEmail();
	}

	private void doRegister(String deviceRegistrationId, String deviceType,
			String deviceId, String accountName, String schoolId)
			throws Exception {
		log.info("in register: accountName = " + accountName);

		try {
			List<DeviceInfo> registrations = getDeviceInfoForUser(accountName);

			log.info("got registrations");
			if (registrations.size() > MAX_DEVICES) {
				log.info("got registrations > MAX_DEVICES");
				// we could return an error - but user can't handle it yet.
				// we can't let it grow out of bounds.
				// TODO: we should also define a 'ping' message and
				// expire/remove
				// unused registrations
				DeviceInfo oldest = DeviceInfo
						.getOldestDeviceInfo(registrations);
				deviceInfoDao.delete(oldest);
			}

			// Get device if it already exists, else create
			String suffix = (deviceId != null ? "#"
					+ Long.toHexString(Math.abs(deviceId.hashCode())) : "");
			log.info("suffix = " + suffix);

			Key<DeviceInfo> key = Key.create(DeviceInfo.class, accountName
					+ suffix);
			log.info("key = " + key);

			DeviceInfo device = null;
			try {
				device = deviceInfoDao.get(key);
			} catch (NotFoundException e) {
				log.info("Caught NotFoundException");
			}
			if (device == null) {
				device = new DeviceInfo(key, deviceRegistrationId);
				device.setType(deviceType);
			} else {
				// update registration id
				device.setDeviceRegistrationID(deviceRegistrationId);
				device.setRegistrationTimestamp(new Date());
			}
			device.setSchoolId(schoolId);
			deviceInfoDao.save(device);

			return;
		} catch (Exception e) {
			log.info("Caught exception: " + e);
			throw e;
		}
	}

	private List<DeviceInfo> getDeviceInfoForUser(String accountName) {
		String user = accountName.toLowerCase(Locale.ENGLISH);

		List<DeviceInfo> registrations = ofy().load().type(DeviceInfo.class)
				.filter("id >=", user).filter("id <", "$").list();
		return registrations;
	}

	private void doUnregister(String deviceRegistrationID, String accountName) {
		log.info("in unregister: accountName = " + accountName);

		try {
			List<DeviceInfo> registrations = getDeviceInfoForUser(accountName);
			for (int i = 0; i < registrations.size(); i++) {
				DeviceInfo deviceInfo = registrations.get(i);
				if (deviceInfo.getDeviceRegistrationID().equals(
						deviceRegistrationID)) {
					deviceInfoDao.delete(deviceInfo);
					// Keep looping in case of duplicates
				}
			}
		} catch (Exception e) {
			log.warning("Error unregistering device: " + e.getMessage());
		}
	}

	@Override
	public void register(RegistrationInfo registrationInfo) {
		log.info("register " + this);
		try {
			doRegister(registrationInfo.getDeviceRegistrationId(), "ac2dm",
					registrationInfo.getDeviceId(), getAccountName(),
					registrationInfo.getSchoolId());
		} catch (Exception e) {
			log.info("Got exception in registration: " + e + " - "
					+ e.getMessage());
			for (StackTraceElement ste : e.getStackTrace()) {
				log.info(ste.toString());
			}
		}
		log.info("Successfully registered");
	}
}
