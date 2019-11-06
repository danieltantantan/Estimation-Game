package estimation.card;
import java.util.*;

public class Deck {
   private List deck;
   private int index;
 
  
  /**
   * Creates an empty deck of cards.
   */
   public Deck() {
      deck = new ArrayList();
      index = 0;
      List<Rank> ranks = Rank.VALUES;
      List<Suit> suits = Suit.VALUES;
      for(Rank rank : ranks){
         for(Suit suit : suits){
            deck.add(new Card(suit,rank));
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