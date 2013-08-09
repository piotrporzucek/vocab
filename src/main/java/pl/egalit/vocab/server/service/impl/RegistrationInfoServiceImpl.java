package pl.egalit.vocab.server.service.impl;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.egalit.vocab.server.dao.RegistrationInfoDao;
import pl.egalit.vocab.server.entity.RegistrationInfo;
import pl.egalit.vocab.server.service.RegistrationInfoService;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;

@Service
public class RegistrationInfoServiceImpl extends AbstractService implements
		RegistrationInfoService {

	@Autowired
	private RegistrationInfoDao registrationInfoDao;
	private static final Logger log = Logger.getLogger(RegistrationInfo.class
			.getName());

	@Override
	public void unregister(RegistrationInfo registrationInfo) {
		log.info("unregister " + this);
		try {
			doUnregister(registrationInfo.getDeviceRegistrationId());
		} catch (Exception e) {
			log.info("Got exception in unregistration: " + e + " - "
					+ e.getMessage());
			for (StackTraceElement ste : e.getStackTrace()) {
				log.info(ste.toString());
			}
		}
		log.info("Successfully unregistered");
	}

	private void doRegister(RegistrationInfo registrationInfo) throws Exception {

		RegistrationInfo device = null;

		device = registrationInfoDao.getRegistration(registrationInfo
				.getDeviceRegistrationId());

		if (device == null) {
			device = new RegistrationInfo(
					registrationInfo.getDeviceRegistrationId());
		} else {
			// update registration id
			device.setDeviceRegistrationId(registrationInfo
					.getDeviceRegistrationId());
			device.setRegistrationTimestamp(new Date());
		}
		device.setSchoolId(registrationInfo.getSchoolId());
		registrationInfoDao.save(device);

		return;
	}

	private void doUnregister(String deviceId) {
		Key<RegistrationInfo> key = Key
				.create(RegistrationInfo.class, deviceId);
		RegistrationInfo registration;
		try {
			registration = registrationInfoDao.get(key);
			registrationInfoDao.delete(registration);
		} catch (EntityNotFoundException e) {
			log.info("No deviceId " + deviceId);
		}
	}

	@Override
	public void register(RegistrationInfo registrationInfo) {
		log.info("register " + this);
		try {
			doRegister(registrationInfo);
		} catch (Exception e) {
			log.info("Got exception in registration: " + e + " - "
					+ e.getMessage());
			for (StackTraceElement ste : e.getStackTrace()) {
				log.info(ste.toString());
			}
		}
		log.info("Successfully registered");
	}

	@Override
	public void unregister(String regId) {
		RegistrationInfo registration = registrationInfoDao.getByProperty(
				"deviceRegistrationId", regId);
		if (registration != null) {
			registrationInfoDao.delete(registration);
		}
	}

	@Override
	public void updateRegistrationId(String regId, String canonicalRegId) {
		RegistrationInfo RegistrationInfo = registrationInfoDao.getByProperty(
				"deviceRegistrationId", regId);
		RegistrationInfo.setDeviceRegistrationId(canonicalRegId);
		registrationInfoDao.save(RegistrationInfo);
	}
}
