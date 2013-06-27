package pl.egalit.vocab.server.core;

import com.google.web.bindery.requestfactory.shared.Locator;

public class SimpleLocator<T extends Object> extends Locator<T, Void> {

	@Override
	public T create(Class<? extends T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public T find(Class<? extends T> clazz, Void id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<T> getDomainType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Void getId(T domainObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<Void> getIdType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getVersion(T domainObject) {
		throw new UnsupportedOperationException();
	}

}
