package FlashCard.service;

import java.util.List;
import FlashCard.pojos.Card;

public interface CardService {
	
	public Card createCard(Card card);
	
	public List<Card> getAllCards();
	
	public List<Card> getAllCardsByStudySetId(int studySetId);
	
	public List<Card> getAllCardsByCourseId(int courseId);
}
