package FlashCard.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import FlashCard.pojos.StudySet;
import FlashCard.pojos.Card;

public class StudyDriver {
		private static Logger log = Logger.getRootLogger();

		private static Scanner scan = new Scanner(System.in);
		
		private StudySet studySet;

		public StudyDriver(StudySet studySet) {
			super();
			this.studySet = studySet;
		}
		
		public Card chooseNextCard() {
			return this.studySet.getCards().poll();
		}
		
		public void Study() {
			log.info("Began studying.");
			boolean continueStudying = true;
			
			do {
				Card card = chooseNextCard();
				System.out.println("What is the definition of " + card.getTerm().getText() +"?");
				String userInput;
				
				do {
					System.out.println("Press [1] to reveal the definition.");
					userInput = scan.nextLine();
				} while(!"1".equals(userInput));
				
				System.out.println(card.getDef().getText());
				
				userInput = "";
				do {
					System.out.println("Did you get the right answer? Type [y] for yes or [n] for no. Or, type [0] to stop studying.");
					userInput = scan.nextLine();
				} while (!userInput.equals("y") && !userInput.equals("n") && !userInput.equals("0"));
				
				if (userInput.equals("y")) {
					card.updatePercentage(true);
				} else if (userInput.equals("n")){
					card.updatePercentage(false);
				} else {
					continueStudying = false;
				}
				studySet.addCard(card);
			} while(continueStudying);
		}
}
