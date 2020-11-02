package FlashCard.pojos;

import java.util.PriorityQueue;

// A study set, composed of a priority queue of cards 
public class StudySet {
	private static int studySetCount;
	
	private int studySetId;
	
	private PriorityQueue<Card> cards;
	
	public String studySetName;
	
	public StudySet(String studySetName) {
		this.studySetName = studySetName;
		this.studySetId = studySetCount;
		studySetCount ++;
		CardComparer comparer = new CardComparer();
		this.cards = new PriorityQueue<Card>(comparer);
	}
	
	public void addCard(Card card) {
		this.cards.add(card);
	}

	public static int getStudySetCount() {
		return studySetCount;
	}

	public static void setStudySetCount(int studySetCount) {
		StudySet.studySetCount = studySetCount;
	}

	public int getStudySetId() {
		return studySetId;
	}

	public void setStudySetId(int studySetId) {
		this.studySetId = studySetId;
	}

	public PriorityQueue<Card> getCards() {
		return cards;
	}

	public void setCards(PriorityQueue<Card> cards) {
		this.cards = cards;
	}

	public String getStudySetName() {
		return studySetName;
	}

	public void setStudySetName(String studySetName) {
		this.studySetName = studySetName;
	}

	@Override
	public String toString() {
		return "StudySet [studySetId=" + studySetId + ", cards=" + cards + "]";
	}
	
	
}
