package estimation.game;
import java.util.*;
import estimation.card.*;

import javax.swing.*;

/**
 * This class records the state of current played cards
 */

public class CurrentPlayedCards{
    private Map<String, Card> currentPlayedCards;

    /**
     * construct the current played cards
     * @param currentPlayedCards a map of the current played cards per player
     */
    public CurrentPlayedCards(Map<String, Card> currentPlayedCards) {
        this.currentPlayedCards = currentPlayedCards;
    }

    /**
     * Return the winner id for the pile
     * @param trumpCard trump card of the round
     * @param leadCard first card played for the round
     * @return String representing the winner for the trick
     */
    public String returnWinnerID(Card trumpCard, Card leadCard){

        List<Card> cards = new ArrayList<>(currentPlayedCards.values());
        Collections.sort(cards, new CardComparator(trumpCard,leadCard));
        String s = "";
        Card winner = cards.get(cards.size() - 1);
        for(String id : currentPlayedCards.keySet()){
            if(currentPlayedCards.get(id).equals(winner)){
                s = id;
            }
        }
        return s;
    }

    /**
     * Return the first round's dealer
     * @return the player id of the dealer
     */
    public String returnFirstRoundDealer(){

        List<Card> cards = new ArrayList<>(currentPlayedCards.values());
        Collections.sort(cards, new CardComparator());
        String s = "";
        Card winner = cards.get(cards.size() - 1);
        for(String id : currentPlayedCards.keySet()){
            if(currentPlayedCards.get(id).equals(winner)){
                s = id;
                break;
            }
        }

        return s;
    }
}
