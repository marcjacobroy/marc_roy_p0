package FlashCard.pojos;

public class Card {
	private static int cardCount; 
	
	private int cardId;
	
	private String term; 
	
	private String def;
	
	private double percentage;
	
	public Card(String term, String def) {
		super();
		this.cardId = cardCount;
		cardCount ++;
		this.percentage = 0.0;
		this.term = term;
		this.def = def;
	}
	
	public static int getCardCount() {
		return cardCount;
	}

	public static void setCardCount(int cardCount) {
		Card.cardCount = cardCount;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", term=" + term + ", def=" + def + ", percentage=" + percentage + "]";
	} 
	
	
	
}
