package pl.egalit.vocab.server.core;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Component;

@Component("customObjectMapper")
public class CustomObjectMapper extends ObjectMapper {
	@PostConstruct
	public void afterPropertiesSet() throws Exception {

		SerializationConfig serialConfig = getSerializationConfig()
				.withDateFormat(null);

		// any other configuration

		this.setSerializationConfig(serialConfig);
	}
}