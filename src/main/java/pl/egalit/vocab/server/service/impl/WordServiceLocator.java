package pl.egalit.vocab.server.service.impl;

import pl.egalit.vocab.server.service.WordService;

public class WordServiceLocator extends AbstractServiceLocator<WordService> {

	@Override
	public Class<WordService> getServiceClass() {
		return WordService.class;
	}

}
