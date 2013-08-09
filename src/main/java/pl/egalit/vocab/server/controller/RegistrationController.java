package pl.egalit.vocab.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.egalit.vocab.server.service.RegistrationInfoService;
import pl.egalit.vocab.shared.RegistrationInfoDto;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private RegistrationInfoService registrationInfoService;

	@Autowired
	private RegistrationInfoAssembler assembler;

	@RequestMapping(value = "/aa", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void register(@RequestBody RegistrationInfoDto registerInfoDto) {
		registrationInfoService
				.register(assembler.mapToEntity(registerInfoDto));
	}
}
