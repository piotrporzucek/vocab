package pl.egalit.vocab.server.core;

import static com.googlecode.objectify.ObjectifyService.ofy;
import pl.egalit.vocab.server.entity.DatastoreObject;

import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * 
 * Generic @Locator for objects that extend DatastoreObject
 */

public class ObjectifyLocator extends Locator<DatastoreObject, Long>

{

	@Override
	public DatastoreObject create(Class<? extends DatastoreObject> clazz)

	{

		try

		{

			return clazz.newInstance();

		} catch (InstantiationException e)

		{

			throw new RuntimeException(e);

		} catch (IllegalAccessException e)

		{

			throw new RuntimeException(e);

		}

	}

	@Override
	public DatastoreObject find(Class<? extends DatastoreObject> clazz, Long id)

	{
		try {
			return id != null ? ofy().load().type(clazz).id(id).get() : clazz
					.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class<DatastoreObject> getDomainType()

	{

		// Never called

		return null;

	}

	@Override
	public Long getId(DatastoreObject domainObject)

	{

		return domainObject.getId();

	}

	@Override
	public Class<Long> getIdType()

	{

		return Long.class;

	}

	@Override
	public Object getVersion(DatastoreObject domainObject)

	{

		return domainObject.getVersion();

	}

}
