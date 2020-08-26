package estimation.card;
import java.util.*;

/**
 * This class specifies a standard deck of cards.
 */

public class Deck {
    private List deck;
    private int index;


    /**
     * Creates an empty deck of cards.
     */
    public static final String[] urls = {
            "./images/2c.gif","./images/3c.gif",
            "./images/4c.gif","./images/5c.gif","./images/6c.gif",
            "./images/7c.gif","./images/8c.gif","./images/9c.gif",
            "./images/tc.gif","./images/jc.gif","./images/qc.gif",
            "./images/kc.gif","./images/ac.gif","./images/2d.gif",
            "./images/3d.gif","./images/4d.gif","./images/5d.gif",
            "./images/6d.gif","./images/7d.gif","./images/8d.gif",
            "./images/9d.gif","./images/td.gif","./images/jd.gif",
            "./images/qd.gif","./images/kd.gif","./images/ad.gif",
            "./images/2h.gif","./images/3h.gif","./images/4h.gif",
            "./images/5h.gif","./images/6h.gif","./images/7h.gif",
            "./images/8h.gif","./images/9h.gif","./images/th.gif",
            "./images/jh.gif","./images/qh.gif","./images/kh.gif","./images/ah.gif",
            "./images/2s.gif","./images/3s.gif",
            "./images/4s.gif","./images/5s.gif","./images/6s.gif",
            "./images/7s.gif","./images/8s.gif","./images/9s.gif",
            "./images/ts.gif","./images/js.gif","./images/qs.gif",
            "./images/ks.gif","./images/as.gif"
    };
    public Deck() {
        deck = new ArrayList();
        index = 0;
        int count = 0;
        List<Rank> ranks = Rank.VALUES;
        List<Suit> suits = Suit.VALUES;
        for(Suit suit : suits){
            for(Rank rank : ranks){
                Card cardToAdd = new Card(suit, rank, urls[count]);
                deck.add(cardToAdd);
                count++;
            }
        }
        Collections.shuffle( deck );
    }


    /**
     * Adds a card to the deck.
     * @param card card to be added to the deck.
     */
    public void addCard( Card card ) {
        deck.add( card );
    }

    /**
     * Deal one card from the deck.
     * @return a card from the deck, or the null reference if there
     * are no cards left in the deck.
     */
    public Card dealCard() {
        if ( index >= deck.size() )
            return null;
        else
            return (Card) deck.get( index++ );
    }


    /**
     * Shuffles the cards present in the deck.
     */
    public void shuffle() {
        Collections.shuffle( deck );
    }

}