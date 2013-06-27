package pl.egalit.vocab.client.core.exceptions;

public class AppSecurityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppSecurityException() {
		super();
	}

	public AppSecurityException(String code) {
		super(code);
	}
}
