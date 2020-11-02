package FlashCard.pojos;

import java.util.Comparator;

public class CardComparer implements Comparator<Card> {
    @Override
    public int compare(Card x, Card y) {
        if (x.getPercentage() == y.getPercentage()){
        	return 0;
        } else if (x.getPercentage() > y.getPercentage()) {
        	return 1;
        } else {
        	return -1;
        }
    }
}
