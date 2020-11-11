package FlashCard.controller;

import FlashCard.pojos.Card;
import FlashCard.pojos.Entry;
import FlashCard.service.CardService;
import FlashCard.service.CardServiceFullStack;
import io.javalin.http.Context;

public class CardController {
	
	CardService cardService = new CardServiceFullStack();
	
	public void createCard(Context ctx) {
		
		System.out.println("Responding to create card request");
		
		String term = ctx.formParam("term");
		
		String def = ctx.formParam("def");
		
		Entry term_side = new Entry(term);
		
		Entry def_side = new Entry(def);
		
		Card card = new Card(term_side, def_side);
		
		cardService.createCard(card);
		
		ctx.html("Created card");
	}
	
	public void readCardDef(Context ctx) {
		System.out.println("Responding to read card def request");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
	
		ctx.html(cardService.readCardDef(cardId));
		ctx.html("Read card def");
	}
	
	public void readCardTerm(Context ctx) {
		System.out.println("Responding to read card term request");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
	
		ctx.html(cardService.readCardTerm(cardId));
		ctx.html("Read card term");
	}
	
	public void updateCard(Context ctx) {
		System.out.println("Responding to update card request");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		
		String term = ctx.formParam("term");
		
		String def = ctx.formParam("def");
		
		Entry term_side = new Entry(term);
		
		Entry def_side = new Entry(def);
		
		Card card = new Card(term_side, def_side);
		
		cardService.updateCard(cardId, card);
		ctx.html("Updated card");
	}
	
	public void deleteCard(Context ctx) {
		System.out.println("Responding to delete card request");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
	
		cardService.deleteCard(cardId);
		ctx.html("Read card term");
	}

}
