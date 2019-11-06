package estimation.card;
import java.util.Comparator;
public class CardComparator implements Comparator<Card>{
    private Card trumpCardObject;
    private Card leadCardObject;
    private static int compareBy = 0;
    public CardComparator(){
        compareBy = 0;
    }
    public CardComparator(Card trumpCardObject){
        this.trumpCardObject = trumpCardObject;
        compareBy = 1;
    }
    public CardComparator(Card trumpCardObject, Card leadCardObject){
        this.trumpCardObject = trumpCardObject;
        this.leadCardObject = leadCardObject;
        compareBy = 2;
    }
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