package pl.egalit.vocab.server.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public abstract class AbstractServiceLocator<T> implements ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(RequestFactoryServlet
						.getThreadLocalRequest().getSession()
						.getServletContext());
		return ctx.getBean(getServiceClass());
	}

	public abstract Class<T> getServiceClass();

}