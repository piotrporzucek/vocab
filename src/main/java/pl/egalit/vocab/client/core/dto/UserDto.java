package pl.egalit.vocab.client.core.dto;

import java.util.HashSet;
import java.util.Set;

import pl.egalit.vocab.client.entity.CourseProxy;
import pl.egalit.vocab.client.entity.UserProxy;

public class UserDto extends AbstractDto {
	/**
	 * UUID.
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String email;

	private String password;

	private boolean isAdmin;

	private Set<CourseDto> courses;

	private long schoolId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static UserDto fromProxy(UserProxy userProxy) {
		UserDto dto = new UserDto();
		dto.setEmail(userProxy.getEmail());
		dto.setId(userProxy.getId());
		dto.setName(userProxy.getName());
		dto.setPassword(userProxy.getPassword());
		dto.setVersion(userProxy.getVersion());
		dto.setAdmin(userProxy.isAdmin());
		dto.setCourses(new HashSet<CourseDto>());
		dto.setSchoolId(userProxy.getSchoolId());
		if (userProxy.getCourseEntities() != null) {
			for (CourseProxy courseProxy : userProxy.getCourseEntities()) {
				dto.getCourses().add(CourseDto.fromProxy(courseProxy));
			}
		}
		return dto;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<CourseDto> getCourses() {
		return courses;
	}

	public void setCourses(Set<CourseDto> courses) {
		this.courses = courses;
	}

	public long getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}
}
