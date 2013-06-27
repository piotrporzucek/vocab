package pl.egalit.vocab.client.core.dto;

import java.util.Date;

import pl.egalit.vocab.client.entity.CourseProxy;

public class CourseDto extends AbstractDto {
	private static final long serialVersionUID = 1L;

	private String name;

	private Date startDate;

	private Date endDate;

	private boolean active;

	private String password;

	private String description;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static CourseDto fromProxy(CourseProxy input) {
		CourseDto dto = new CourseDto();
		dto.setId(input.getId());
		dto.setName(input.getName());
		dto.setStartDate(input.getStartDate());
		dto.setEndDate(input.getEndDate());
		dto.setActive(input.isActive());
		dto.setPassword(input.getPassword());
		dto.setDescription(input.getDescription());
		return dto;
	}

	public void setDescription(String description) {
		this.description = description;

	}

	public String getDescription() {
		return description;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
