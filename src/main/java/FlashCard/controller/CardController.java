package FlashCard.controller;

import FlashCard.pojos.Card;
import FlashCard.pojos.Entry;
import FlashCard.service.CardService;
import FlashCard.service.CardServiceFullStack;
import io.javalin.http.Context;

public class CardController {
	
	CardService cardService = new CardServiceFullStack();
	
	public void createCard(Context ctx) {
		
		System.out.println("Responding to post card request");
		
		String term = ctx.formParam("term");
		
		String def = ctx.formParam("def");
		
		Entry term_side = new Entry(term);
		
		Entry def_side = new Entry(def);
		
		Card card = new Card(term_side, def_side);
		
		cardService.createCard(card);
		
		ctx.html(Integer.toString(card.getCardId()));
	}
	
	public void getCard(Context ctx) {
		//TODO
	}
	
	public void getAllCards() {
		//TODO
	}

}
