package FlashCard.pojos;

public class Card {
	private static int cardCount; 
	
	private int cardId;
	
	private Entry term; 
	
	private Entry def;
	
	private double percentage;
	
	public Card(Entry term, Entry def) {
		super();
		this.cardId = cardCount;
		cardCount ++;
		this.percentage = 0.0;
		this.term = term;
		this.def = def;
	}
	
	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", term=" + term + ", def=" + def + ", percentage=" + percentage + "]";
	}
}
