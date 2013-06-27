package pl.egalit.vocab.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class CustomRequestFactoryServlet extends RequestFactoryServlet {

	static class LoquaciousExceptionHandler implements ExceptionHandler {
		private static final Logger LOG = LoggerFactory
				.getLogger(LoquaciousExceptionHandler.class);

		@Override
		public ServerFailure createServerFailure(Throwable throwable) {
			LOG.error("Server error", throwable);
			return new ServerFailure(throwable.getMessage(), throwable
					.getClass().getName(), null, true);
		}
	}

	public CustomRequestFactoryServlet() {
		super(new LoquaciousExceptionHandler());
	}
}
