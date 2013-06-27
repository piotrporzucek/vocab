package pl.egalit.vocab.client.entity;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

/**
 * A proxy object containing device registration information: email account
 * name, device id, and device registration id.
 */
@ProxyForName(value = "pl.egalit.vocab.server.entity.Word", locator = "pl.egalit.vocab.server.core.ObjectifyLocator")
public interface WordProxy extends EntityProxy {
	Long getId();

	Integer getVersion();

	String getAnswer();

	String getExpression();

	String getExample();

	void setAnswer(String answer);

	void setExpression(String expression);

	void setExample(String example);

}
