//************************************************************
//
//  Hand.Java             Authors:  Lewis, Chase, Coleman
//
//  Provides an implementation of a hand of cards using a
//  set to represent the cards
//
//************************************************************

import jss2.*;
import jss2.exceptions.*;

import java.util.*;

public class Hand
{
    protected ArraySet<Card> inHand;
    protected int handvalue,count;

    /**********************************************************
     Constructs a hand of Cards.
     **********************************************************/
    public Hand()
    {
        inHand = new ArraySet<Card>(12);
        handvalue=0;
        count =0;
    }

    /**********************************************************
     To reduce hand when newcard makes player go over 21
     and there is an ace in the hand.
     @param newCard random card from the set
     **********************************************************/
    private void reduceHand(Card newCard)
    {
        if((handvalue) > 21)
        {
            if(aceInHand())
                handvalue -= 10;

        }
    }//end reduceHand

    /**********************************************************
     To check if there is an ace in the hand.
     **********************************************************/
    private boolean aceInHand()
    {
        boolean result = false;
        Card cardchk = null;
        Iterator<Card> scan = inHand.iterator();

        while (scan.hasNext() && !result)
        {
            cardchk = scan.next();
            if(cardchk.getvalue() == 11)
            {
                cardchk.setvalue(1);
                result = true;
            }

        }
        return result;
    }

    /**********************************************************
     Adds a new card to the hand.
     @param currentdeck the Deck the game is playing with
     **********************************************************/
    public Card newCard(Deck currentdeck)
    {
        Card result;
        result = currentdeck.getCard();
        inHand.add(result);
        handvalue+=result.getvalue();
        reduceHand(result);
        count++;

        return result;
    }


    /**********************************************************
     Returns the value of this hand.
     **********************************************************/
    public int getHandValue()
    {
        return handvalue;
    }

    /**********************************************************
     Returns an iterator over this hand.
     **********************************************************/
    public Iterator<Card> iterator()
    {
        return inHand.iterator();
    }

    /**********************************************************
     Removes a card from this hand.
     **********************************************************/
    public Card remove(Card crd) throws ElementNotFoundException
    {
        return(inHand.remove(crd));
    }

    /**********************************************************
     Returns a string representation of this hand.
     **********************************************************/
    public String toString()
    {
        String result="";

        Card cardstr = null;
        int i=0;
        Iterator<Card> scan = inHand.iterator();
        while (scan.hasNext())
        {
            cardstr= scan.next();
            result += "card"+i+": "+cardstr.getvalue()+"\n";
            i++;
        }

        return result;
    }

}//end Hand