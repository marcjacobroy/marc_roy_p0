package FlashCard.main;

import io.javalin.Javalin; 
import FlashCard.controller.CardController;

public class ServerDriver {
	
	private static CardController cardController = new CardController();
	
	public static void main(String[] args) {
		Javalin app = Javalin.create().start(9090); //sets up and starts our server
		app.get("/hello", ctx -> ctx.html("Hello World"));
		app.post("/createCard", ctx -> cardController.createCard(ctx));
	}
	
}
