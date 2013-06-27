package pl.egalit.vocab.server.dao;

import org.springframework.stereotype.Repository;

import pl.egalit.vocab.server.entity.DeviceInfo;

@Repository
public class DeviceInfoDao extends ObjectifyGenericDao<DeviceInfo> {

	protected DeviceInfoDao() {
		super(DeviceInfo.class);

	}

}
