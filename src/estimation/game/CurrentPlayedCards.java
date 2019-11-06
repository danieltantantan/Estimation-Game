package estimation.game;
import java.util.*;
import estimation.card.*;
public class CurrentPlayedCards{
    private HashMap<String, Card> currentPlayedCards;

    public CurrentPlayedCards(HashMap<String, Card> currentPlayedCards) {
        this.currentPlayedCards = currentPlayedCards;
    }

    public String returnWinnerID(Card trumpCard, ArrayList<Card> pile){
        
        List<Card> cards = new ArrayList<>(currentPlayedCards.values());
        Card firstCard = pile.get(0);
        Collections.sort(pile, new CardComparator(trumpCard,firstCard));

        String s = "";
        Card winner = pile.get(pile.size() - 1);
        for(String id : currentPlayedCards.keySet()){
            if(currentPlayedCards.get(id).equals(winner)){
                s = id;
            }
        }
        return s;
    }

    public String returnFirstRoundDealer(){

        List<Card> cards = new ArrayList<>(currentPlayedCards.values());
        Collections.sort(cards, new CardComparator());
        String s = "";
        Card winner = cards.get(cards.size() - 1);
        for(String id : currentPlayedCards.keySet()){
            if(currentPlayedCards.get(id).equals(winner)){
                s = id;
            }
        }
        return s;
    }
    




}
