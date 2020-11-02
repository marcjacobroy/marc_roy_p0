package FlashCard.pojos;

public class Card {
	private static int cardCount; 
	
	private int cardId;
	
	private Entry term; 
	
	private Entry def;
	
	private double percentage;
	
	private int count;
	
	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	public void updatePercentage(boolean correct) {
		if (correct) {
			this.percentage = ((this.percentage * count) + 1.) / (count + 1.);
		} else {
			this.percentage = this.percentage * count / (count + 1.);
		}
		this.count += 1;
	}

	public Card(Entry term, Entry def) {
		super();
		this.cardId = cardCount;
		cardCount ++;
		this.percentage = 0.0;
		this.term = term;
		this.def = def;
	}
	
	
	
	public Entry getTerm() {
		return term;
	}

	public void setTerm(Entry term) {
		this.term = term;
	}

	public Entry getDef() {
		return def;
	}

	public void setDef(Entry def) {
		this.def = def;
	}

	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", term=" + term + ", def=" + def + ", percentage=" + percentage + "]";
	}
}
