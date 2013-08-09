package pl.egalit.vocab.client.requestfactory;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

/**
 * A proxy object containing device registration information: email account
 * name, device id, and device registration id.
 */
@ProxyForName(value = "pl.egalit.vocab.server.entity.WordsUnit", locator = "pl.egalit.vocab.server.core.ObjectifyLocator")
public interface WordsUnitProxy extends EntityProxy {
	Long getId();

	Integer getVersion();

	String getLector();

	Date getAddedDate();

}
