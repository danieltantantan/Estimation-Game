package estimation.card;
import javax.swing.*;
import estimation.card.*;

/**
 * This class specifies the properties of a card in a standard deck of cards.
 */

public class Card implements Comparable {

    // instance variables for the card
    private Suit suitValue;
    private Rank rankValue;
    private String url;
    private static boolean sortRankMajorOrder = true;


    /**
     * Creates a new playing card.
     * @param suit the suit value of this card.
     * @param rank the rank value of this card.
     * @param url the image url of this card.
     */
    public Card( Suit suit, Rank rank, String url ) {
        this.url = url;
        suitValue = suit;
        rankValue = rank;
    }
    public Card( Suit suit, Rank rank) {
        suitValue = suit;
        rankValue = rank;
    }


    /**
     * Generates the filename associated with the card.  <code>getFilename</code> assumes that all of the standard card images
     * are stored in individual files using filenames in the form of:
     * <b>RS.gif</b> where <b>R</b> is a single character used to represent
     * the rank value of the card and <b>S</b> is a single character used to represent
     * the suit value of the card.
     * <p>The characters used for <b>R</b> are:
     * 'a' (ace), '2', '3', '4', '5', '6', '7', '8', '9',
     * 't' (10), 'j' (jack), 'q' (queen), and 'k' (king).
     * <p>The characters used for <b>S</b> are:
     * 'c' (clubs), 'd' (diamonds), 'h' (hearts), and 's' (spades).
     * <p>Two other cards are also available: "b.gif" (back of card) and "j.gif" (joker).
     *
     * @param suit the suit value of the card.
     * @param rank the rank value of the card.
     * @return a string containing the filename of the card.
     */
    public static String getFilename( Suit suit, Rank rank ) {
        return rank.getSymbol() + suit.getSymbol() + ".gif";
    }


    /**
     * Returns the suit of the card.
     * @return a Suit constant representing the suit value of the card.
     */
    public Suit getSuit() {
        return suitValue;
    }


    /**
     * Returns the rank of the card.
     * @return a Rank constant representing the rank value of the card.
     */
    public Rank getRank() {
        return rankValue;
    }


    /**
     * Returns the graphic image of the card.
     * @return an icon containing the graphic image of the card.
     *
     */
    public String getUrl(){
        return url;
    }


    /**
     * Returns a description of this card.
     * @return the name of the card.
     */
    public String toString() {
        return rankValue.toString() + " of " + suitValue.toString();
    }


    /**
     * Returns a description of the rank of this card.
     * @return the rank value of the card as a string.
     */
    public String rankToString() {
        return rankValue.toString();
    }


    /**
     * Returns a description of the suit of this card.
     * @return the suit value of the card as a string.
     */
    public String suitToString() {
        return suitValue.toString();
    }


    /**
     * Specifies that cards are to be sorted in rank-major order.  Cards are ordered
     * first by their rank value; cards of the same rank are then ordered by their
     * suit value.
     */
    public static void setRankMajorSort() {
        sortRankMajorOrder = true;
    }


    /**
     * Specifies that cards are to be sorted in suit-major order.  Cards are ordered
     * first by their suit value; cards of the same suit are then ordered by their
     * rank value.
     */
    public static void setSuitMajorSort() {
        sortRankMajorOrder = false;
    }


    /**
     * Compares two cards for the purposes of sorting.
     * Cards are ordered first by their suit value, then by their
     * rank value.
     * @param otherCardObject the other card
     * @return a negative integer, zero, or a positive integer is this card is
     * less than, equal to, or greater than the referenced card.
     */
    public int compareTo( Object otherCardObject) {
        Card otherCard = (Card) otherCardObject;
        int suitDiff = suitValue.compareTo( otherCard.suitValue );
        int rankDiff = rankValue.compareTo( otherCard.rankValue );

        if(rankDiff!=0){
            return rankDiff;
        }
        else{
            return suitDiff;
        }
    }

    public int compareTo( Object otherCardObject, Object trumpCardObject ) {
        Card trumpCard = (Card) trumpCardObject;
        Card otherCard = (Card) otherCardObject;
        int suitDiff = suitValue.compareTo( otherCard.suitValue );
        int rankDiff = rankValue.compareTo( otherCard.rankValue );
        if(rankDiff != 0){
            //check if both are trump cards, then return the bigger one.
            if(suitValue.compareTo(trumpCard.suitValue) == 0 && otherCard.suitValue.compareTo(trumpCard.suitValue) == 0){
                return rankDiff;
            }
            //not both trump, so check if first card is trump.
            if(suitValue.compareTo(trumpCard.suitValue) == 0){
                return 1;
            }
            //not both trump, so check if second card is trump.
            if(otherCard.suitValue.compareTo(trumpCard.suitValue) == 0){
                return -1;
            }
            //both are not trump. check if same suit then return rank diff, else, return the suit difference.
            if(suitDiff == 0){
                return rankDiff;
            }
            else{
                return suitDiff;
            }
        }else{//no rank diff. both are same value. tells you that the suits are also different.
            //check if first card is trump.
            if(suitValue.compareTo(trumpCard.suitValue) == 0){
                return 1;
            }
            //check if second card is trump.
            if(otherCard.suitValue.compareTo(trumpCard.suitValue) == 0){
                return -1;
            }
            //both not trump. return the suitDifference.
            else{
                return suitDiff;
            }
        }
    }
    public int compareTo( Object otherCardObject, Object trumpCardObject, Object leadCardObject) {
        Card trumpCard = (Card) trumpCardObject;
        Card otherCard = (Card) otherCardObject;
        Card leadCard = (Card) leadCardObject;
        int suitDiff = suitValue.compareTo( otherCard.suitValue );
        int rankDiff = rankValue.compareTo( otherCard.rankValue );
        if(rankDiff != 0){
            //check if both cards are trump cards, then return the bigger one.
            if(suitValue.compareTo(trumpCard.suitValue) == 0 && otherCard.suitValue.compareTo(trumpCard.suitValue) == 0){
                return rankDiff;
            }
            //check if this card is trump, if so will return it
            if(suitValue.compareTo(trumpCard.suitValue) == 0){
                return 1;
            }
            //check if other card is trump, and return it.
            if(otherCard.suitValue.compareTo(trumpCard.suitValue) == 0){
                return -1;
            }
            //check if both cards are lead cards, if so, return the bigger one
            if(suitValue.compareTo(leadCard.suitValue) == 0 && otherCard.suitValue.compareTo(leadCard.suitValue) == 0){
                return rankDiff;
            }
            //check if this card is lead, if so will return it
            if(suitValue.compareTo(leadCard.suitValue) == 0){
                return 1;
            }
            //check if other card is lead, and return it.
            if(otherCard.suitValue.compareTo(leadCard.suitValue) == 0){
                return -1;
            }
      /* since it reaches this condition, none of the cards are lead or trump cards. 
      we will proceed to sort them based on the predefined suit difference and then rank difference
      */
            // if same suit, then return rank diff. else, return the suit difference.
            if(suitDiff == 0){
                return rankDiff;
            }
            else{
                return suitDiff;
            }
        }
     /* no rank difference. proceed to check if any card is trump card,
     then any card is lead, then normal suit diff
     */
        else{
            if(suitValue.equals(trumpCard.suitValue)){
                return 1;
            }
            if(otherCard.suitValue.equals(trumpCard.suitValue)){
                return -1;
            }
            if(suitValue.equals(leadCard.suitValue)){
                return 1;
            }
            if(otherCard.suitValue.equals(leadCard.suitValue)){
                return -1;
            }
            else{
                return suitDiff;
            }
        }
    }


    /**
     * Compares two cards to determine if they have the same value.
     * This is not the same as the use of <code>equals</code> which compares
     * two objects for equality.
     * @param card the other card
     * @return <code>true</code> if the two cards have the same rank and suit
     * values, <code>false</code> if they do not.
     */
    public boolean isSameAs( Card card ) {
        if ( ( rankValue != card.rankValue ) || ( suitValue != card.suitValue ) )
            return false;
        else
            return true;
    }

}