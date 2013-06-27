package pl.egalit.vocab.server.entity;

import com.google.common.base.Objects;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class School extends DatastoreObject {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof School)) {
			return false;
		}
		School objSchool = (School) obj;
		return Objects.equal(this.id, objSchool.getId());
	}
}
