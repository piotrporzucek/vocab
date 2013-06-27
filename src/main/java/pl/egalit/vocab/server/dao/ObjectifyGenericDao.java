package pl.egalit.vocab.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

public class ObjectifyGenericDao<T> {

	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC
			| Modifier.TRANSIENT;

	@Autowired
	private ObjectifyFactory objectifyFactory;

	protected Class<T> clazz;

	/**
	 * 
	 * We've got to get the associated domain class somehow
	 * 
	 * 
	 * 
	 * @param clazz
	 */
	protected ObjectifyGenericDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Key<T> save(T entity) {
		return ofy().save().entity(entity).now();
	}

	public Map<Key<T>, T> saveAll(Iterable<T> entities) {
		return ofy().save().entities(entities).now();
	}

	public void delete(T entity) {
		ofy().delete().entity(entity).now();
	}

	public void deleteKey(Key<T> entityKey) {
		ofy().delete().key(entityKey).now();
	}

	public void deleteAll(Iterable<T> entities) {
		ofy().delete().entities(entities);
	}

	public void deleteKeys(Iterable<Key<T>> keys) {
		ofy().delete().keys(keys);
	}

	public T load(Long id) throws EntityNotFoundException {
		return ofy().load().type(this.clazz).id(id).get();
	}

	public T get(Key<T> key) throws EntityNotFoundException {
		return ofy().load().key(key).get();
	}

	/**
	 * 
	 * Convenience method to get all objects matching a single property
	 * 
	 * 
	 * 
	 * @param propName
	 * 
	 * @param propValue
	 * 
	 * @return T matching Object
	 */
	public T getByProperty(String propName, Object propValue) {
		Query<T> q = ofy().load().type(clazz);
		q = q.filter(propName, propValue);
		Ref<T> ref = q.first();
		return ref.get();
	}

	public List<T> listByProperty(String propName, Object propValue) {
		Query<T> q = ofy().load().type(clazz);
		q = q.filter(propName, propValue);
		return q.list();
	}

	public List<Key<T>> listKeysByProperty(String propName, Object propValue) {
		Query<T> q = ofy().load().type(clazz);
		q = q.filter(propName, propValue);
		return asKeyList(q.keys());
	}

	public T getByExample(T exampleObj) {
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		Iterable<T> iterableResults = queryByExample.iterable();
		Iterator<T> i = iterableResults.iterator();
		T obj = i.next();
		if (i.hasNext())
			throw new RuntimeException("Too many results");
		return obj;
	}

	public List<T> listByExample(T exampleObj) {
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		return queryByExample.list();
	}

	private List<T> asList(Iterable<T> iterable) {
		ArrayList<T> list = new ArrayList<T>();
		for (T t : iterable) {
			list.add(t);
		}
		return list;
	}

	private List<Key<T>> asKeyList(Iterable<Key<T>> iterableKeys) {
		ArrayList<Key<T>> keys = new ArrayList<Key<T>>();
		for (Key<T> key : iterableKeys) {
			keys.add(key);
		}
		return keys;
	}

	private Query<T> buildQueryByExample(T exampleObj) {
		Query<T> q = ofy().load().type(clazz);
		// Add all non-null properties to query filter
		for (Field field : clazz.getDeclaredFields()) {
			// Ignore transient, embedded, array, and collection properties
			if ((field.getType().isArray())
					|| (Collection.class.isAssignableFrom(field.getType()))
					|| ((field.getModifiers() & BAD_MODIFIERS) != 0))
				continue;
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(exampleObj);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			if (value != null) {
				q.filter(field.getName(), value);
			}
		}
		return q;
	}

}
