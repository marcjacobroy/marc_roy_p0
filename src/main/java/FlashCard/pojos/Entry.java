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
	
	private Language text_language;
	
	public Entry(String text) {
		super();
		this.text = text;
		this.text_language = Language.ENGLISH;
	}

	public Entry(String text, Language text_language) {
		this(text);
		this.text_language = text_language;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Language getText_language() {
		return text_language;
	}

	public void setText_language(Language text_language) {
		this.text_language = text_language;
	}
	
	
	
}
