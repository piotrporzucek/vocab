package pl.egalit.vocab.shared;

import java.io.Serializable;

public class SchoolDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String city;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
