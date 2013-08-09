package pl.egalit.vocab.shared;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class CourseDto implements Serializable {

	private static final long serialVersionUID = -7370731504710781266L;

	public static final String NAME_PROPERTY = "name";
	public static final String CHOSEN_PROPERTY = "chosen";

	public static final String LANGUAGE_PROPERTY = "language";
	private Long id;
	private String name;
	private boolean chosen;
	private String description;
	private boolean active;
	private Date startDate;
	private Date endDate;
	private String password;
	private String language;
	private Long schoolId;

	private boolean initialized;

	public CourseDto() {

	}

	public CourseDto(long courseId, String courseName, String password,
			boolean initialized, String language) {
		this.id = courseId;
		this.name = courseName;
		this.password = password;
		this.initialized = initialized;
		this.language = language;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object[] toArray() {
		return new Object[] { id, name };
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public String toString() {
		return name != null ? name : "null" + " [" + id + "]";
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}