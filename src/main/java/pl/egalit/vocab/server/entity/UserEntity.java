package pl.egalit.vocab.server.entity;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Pattern;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class UserEntity extends DatastoreObject {

	@Index
	@Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$", message = "{UserEntity.username.invalid}")
	private String email;
	private String name;
	private Set<Key<Course>> courses;
	private String password;
	private String role;

	@Unindex
	private List<Course> courseEntities = Lists.newArrayList();

	@Parent
	private Key<School> school;

	public UserEntity() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Key<School> getSchool() {
		return school;
	}

	public void setSchool(Key<School> school) {
		this.school = school;
	}

	public Set<Key<Course>> getCourses() {
		return courses;
	}

	public void setCourses(Set<Key<Course>> courses) {
		this.courses = courses;
	}

	public Long getSchoolId() {
		return school == null ? null : school.getId();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdmin() {
		return "ROLE_ADMIN".equals(role);
	}

	public List<Course> getCourseEntities() {
		return courseEntities;
	}

	public void setCourseEntities(List<Course> courseEntities) {
		this.courseEntities = courseEntities;
	}

}
