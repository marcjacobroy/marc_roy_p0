package FlashCard.pojos;

// A study card 
public class Card {
	
	public int getCardId() {
		return cardId;
	}

	public static int getCardCount() {
		return cardCount;
	}

	private static int cardCount; 
	
	private int cardId;
	
	private Entry term; 
	
	private Entry def;
	
	private double percentage;
	
	private int countCorrect = 0;
	
	private int countWrong = 0;
	
	public double getPercentage() {
		return ((double) this.countCorrect) / ((double) this.countCorrect + (double) this.countWrong);
	}

	public int getCountCorrect() {
		return countCorrect;
	}

	public void setCountCorrect(int countCorrect) {
		this.countCorrect = countCorrect;
	}

	public int getCountWrong() {
		return countWrong;
	}

	public void setCountWrong(int countWrong) {
		this.countWrong = countWrong;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	public void updatePercentage(boolean correct) {
		if (correct) {
			this.countCorrect++;
		} else {
			this.countWrong++;
		}
	}

	public Card(Entry term, Entry def) {
		super();
		this.cardId = cardCount;
		cardCount ++;
		this.percentage = 0.0;
		this.term = term;
		this.def = def;
	}
	
	public String getTerm() {
		return this.term.getText();
	}

	public void setTerm(Entry term) {
		this.term = term;
	}

	public String getDef() {
		return this.def.getText();
	}

	public void setDef(Entry def) {
		this.def = def;
	}

	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", term=" + term + ", def=" + def + ", percentage=" + percentage + "]";
	}
}
