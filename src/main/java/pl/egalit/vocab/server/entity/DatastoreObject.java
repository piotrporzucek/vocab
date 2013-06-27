package pl.egalit.vocab.server.entity;

import com.googlecode.objectify.annotation.Id;

public class DatastoreObject

{

	@Id
	protected Long id;

	protected Integer version = 0;

	/**
	 * 13 Auto-increment version # whenever persisted 14
	 */

	void onPersist()

	{

		this.setVersion(this.getVersion() + 1);

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
