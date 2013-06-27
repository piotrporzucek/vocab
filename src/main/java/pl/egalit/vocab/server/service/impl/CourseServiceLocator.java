package pl.egalit.vocab.server.service.impl;

import pl.egalit.vocab.server.service.CourseService;

public class CourseServiceLocator extends AbstractServiceLocator<CourseService> {

	@Override
	public Class<CourseService> getServiceClass() {
		return CourseService.class;
	}

}
