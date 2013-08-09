package pl.egalit.vocab.client;

public enum Language {
	ENGLISH("en"), GERMAN("de"), FRENCH("fr"), SPANISH("es"), ITALIAN("it"), POLISH(
			"pl"), PORTUGUESE("po"), RUSSIAN("po"), CHINESE("cn");

	private String code;

	private Language(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static Language getLanguageForCode(String code) {
		for (Language l : Language.values()) {
			if (l.getCode().equals(code)) {
				return l;
			}
		}
		return null;
	}

}
