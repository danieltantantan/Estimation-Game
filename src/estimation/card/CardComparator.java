package estimation.card;
import java.util.Comparator;

/**
 * This class specifies the card comparators logic
 */

public class CardComparator implements Comparator<Card>{
    private Card trumpCardObject;
    private Card leadCardObject;
    private static int compareBy = 0;
    public CardComparator(){
        compareBy = 0;
    }

    /**
     * Compares Cards by only trump card
     * @param trumpCardObject trump card
     */
    public CardComparator(Card trumpCardObject){
        this.trumpCardObject = trumpCardObject;
        compareBy = 1;
    }

    /**
     * Compares by by both trump card and lead card
     * @param trumpCardObject Trump Card
     * @param leadCardObject Lead Card
     */
    public CardComparator(Card trumpCardObject, Card leadCardObject){
        this.trumpCardObject = trumpCardObject;
        this.leadCardObject = leadCardObject;
        compareBy = 2;
    }

    /**
     * Compares two cards by either trump, or trump and lead card
     * Then, returns whichever is bigger based on compareTo
     * @param card1 First Card
     * @param card2 Second Card
     * @return a negative integer, zero, or a positive integer is this card is
     * less than, equal to, or greater than the referenced card.
     */
    public int compare(Card card1, Card card2){
        if(compareBy == 0){
            return card1.compareTo(card2);
        }
        else if(compareBy == 1){
            return card1.compareTo(card2, this.trumpCardObject);
        }
        else{
            return card1.compareTo(card2, this.trumpCardObject, this.leadCardObject);
        }
    }
}