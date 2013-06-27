package pl.egalit.vocab.client.core.dto;

import java.io.Serializable;

public class AbstractDto implements Serializable {

	/**
	 * UUID.
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer version;

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
