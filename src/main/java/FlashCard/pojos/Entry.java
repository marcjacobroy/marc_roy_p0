package FlashCard.pojos;

public class Entry {
	
	public enum Language {
		ENGLISH,
		GERMAN,
		FRENCH,
		SPANISH,
		PORTUGUESE,
		ITALIAN,
		RUSSIAN,
		ARABIC
	}
	
	private String text;
	
	private Language textLanguage;
	
	public Entry(String text) {
		super();
		this.text = text;
		this.textLanguage = Language.ENGLISH;
	}

	public Entry(String text, Language textLanguage) {
		this(text);
		this.textLanguage = textLanguage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Language getText_language() {
		return textLanguage;
	}

	public void setText_language(Language textLanguage) {
		this.textLanguage = textLanguage;
	}	
}
