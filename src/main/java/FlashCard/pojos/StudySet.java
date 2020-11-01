package FlashCard.pojos;

import java.util.PriorityQueue;

public class StudySet {
	private static int studySetCount;
	
	private int studySetId;
	
	private PriorityQueue<Card> cards;
	
	public StudySet() {
		this.studySetId = studySetCount;
		studySetCount ++;
	}

	public StudySet(PriorityQueue<Card> cards) {
		this();
		this.cards = cards;
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

	@Override
	public String toString() {
		return "StudySet [studySetId=" + studySetId + ", cards=" + cards + "]";
	}
	
	
}
