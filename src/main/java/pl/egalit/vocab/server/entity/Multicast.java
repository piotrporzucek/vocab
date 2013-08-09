package pl.egalit.vocab.server.entity;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Multicast extends DatastoreObject {

	@Index
	private List<String> regIds;

	private Key<Course> courseKey;

	public List<String> getRegIds() {
		return regIds;
	}

	public void setRegIds(List<String> regIds) {
		this.regIds = regIds;
	}

	public Key<Course> getCourseKey() {
		return courseKey;
	}

	public void setCourseKey(Key<Course> courseKey) {
		this.courseKey = courseKey;
	}

}
