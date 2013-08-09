package pl.egalit.vocab.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.egalit.vocab.server.entity.RegistrationInfo;
import pl.egalit.vocab.shared.RegistrationInfoDto;

@Component
public class RegistrationInfoAssembler {
	public RegistrationInfoDto mapToDto(RegistrationInfo r) {
		RegistrationInfoDto dto = new RegistrationInfoDto();
		dto.setDeviceRegistrationId(r.getDeviceRegistrationId());
		dto.setSchoolId(r.getSchoolId());
		return dto;
	}

	public List<RegistrationInfoDto> mapToDtoList(List<RegistrationInfo> list) {
		List<RegistrationInfoDto> result = new ArrayList<RegistrationInfoDto>();
		for (RegistrationInfo c : list) {
			result.add(mapToDto(c));
		}
		return result;
	}

	public RegistrationInfo mapToEntity(RegistrationInfoDto registrationInfoDto) {
		RegistrationInfo registrationInfo = new RegistrationInfo();
		registrationInfo.setDeviceRegistrationId(registrationInfoDto
				.getDeviceRegistrationId());
		registrationInfo.setSchoolId(registrationInfoDto.getSchoolId());
		return registrationInfo;
	}
}
