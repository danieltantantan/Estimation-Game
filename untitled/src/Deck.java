//************************************************************
//
//  Deck.Java             Authors:  Lewis, Chase, Coleman
//
//  Provides an implementation of a deck of cards using a
//  set to represent the cards
//
//************************************************************


import jss2.*;
import jss2.exceptions.*;

import java.util.Random;
import javax.swing.*;
import java.awt.*;


public class Deck
{

    ArraySet<Card> deckSet = new ArraySet<Card>();

    /**********************************************************
     Constructs a deck of 52 Cards and
     puts them in the set.
     **********************************************************/
    public Deck()
    {
        ImageIcon card49 = new ImageIcon("2s.gif");
        Card twos = new Card(card49, 2,"spade", "Two");
        ImageIcon card45 = new ImageIcon("3s.gif");
        Card threes = new Card(card45, 3,"spade", "Three");
        ImageIcon card1 = new ImageIcon("4s.gif");
        Card fours = new Card(card1, 4, "spade","Four");
        ImageIcon card13 = new ImageIcon("5s.gif");
        Card fives = new Card(card13, 5, "spade", "Five");
        ImageIcon card27 = new ImageIcon("6s.gif");
        Card sixs = new Card(card27, 6, "spade", "Six");
        ImageIcon card9 = new ImageIcon("7s.gif");
        Card sevens = new Card(card9, 7, "spade" , "Seven");
        ImageIcon card17 = new ImageIcon("8s.gif");
        Card eights = new Card(card17, 8, "spade", "Eight");
        ImageIcon card40 = new ImageIcon("9s.gif");
        Card nines = new Card(card40, 9,"spade", "Nine");
        ImageIcon card50 = new ImageIcon("10s.gif");
        Card tens = new Card(card50, 10,"spade", "Ten");
        ImageIcon card26 = new ImageIcon("jacks.gif");
        Card jacks = new Card(card26, 10, "spade", "Jack");
        ImageIcon card33 = new ImageIcon("queens.gif");
        Card queens = new Card(card33, 10,"spade", "Queen");
        ImageIcon card18 = new ImageIcon("kings.gif");
        Card kings = new Card(card18, 10, "spade", "King");
        ImageIcon card15 = new ImageIcon("aces.gif");
        Card aces = new Card(card15, 11, "spade", "Ace");

        ImageIcon card39 = new ImageIcon("2h.gif");
        Card twoh = new Card(card39, 2,"heart", "Two");
        ImageIcon card2 = new ImageIcon("3h.gif");
        Card threeh = new Card(card2, 3, "heart","Three");
        ImageIcon card8 = new ImageIcon("4h.gif");
        Card fourh = new Card(card8, 4, "heart", "Four");
        ImageIcon card51 = new ImageIcon("5h.gif");
        Card fiveh = new Card(card51, 5,"heart", "Five");
        ImageIcon card24 = new ImageIcon("6h.gif");
        Card sixh = new Card(card24, 6,"heart", "Six");
        ImageIcon card34 = new ImageIcon("7h.gif");
        Card sevenh = new Card(card34, 7,"heart", "Seven");
        ImageIcon card35 = new ImageIcon("8h.gif");
        Card eighth = new Card(card35, 8,"heart", "Eight");
        ImageIcon card4 = new ImageIcon("9h.gif");
        Card nineh = new Card(card4, 9, "heart","Nine");
        ImageIcon card7 = new ImageIcon("10h.gif");
        Card tenh = new Card(card7, 10,"heart", "Ten");
        ImageIcon card3 = new ImageIcon("jackh.gif");
        Card jackh = new Card(card3, 10, "heart","Jack");
        ImageIcon card25 = new ImageIcon("queenh.gif");
        Card queenh = new Card(card25, 10,"heart", "Queen");
        ImageIcon card36 = new ImageIcon("kingh.gif");
        Card kingh = new Card(card36, 10,"heart", "King");
        ImageIcon card10 = new ImageIcon("aceh.gif");
        Card aceh = new Card(card10, 11, "heart", "Ace");

        ImageIcon card31 = new ImageIcon("2d.gif");
        Card twod = new Card(card31, 2,"diamond", "Two");
        ImageIcon card30 = new ImageIcon("3d.gif");
        Card threed = new Card(card30, 3,"diamond", "Three");
        ImageIcon card32 = new ImageIcon("4d.gif");
        Card fourd = new Card(card32, 4,"diamond", "Four");
        ImageIcon card48 = new ImageIcon("5d.gif");
        Card fived = new Card(card48, 5,"diamond", "Five");
        ImageIcon card5 = new ImageIcon("6d.gif");
        Card sixd = new Card(card5, 6, "diamond", "Six");
        ImageIcon card41 = new ImageIcon("7d.gif");
        Card sevend = new Card(card41, 7,"diamond", "Seven ");
        ImageIcon card14 = new ImageIcon("8d.gif");
        Card eightd = new Card(card14, 8, "diamond", "Eight");
        ImageIcon card16 = new ImageIcon("9d.gif");
        Card nined = new Card(card16, 9, "diamond", "Nine");
        ImageIcon card12 = new ImageIcon("10d.gif");
        Card tend = new Card(card12, 10, "diamond", "Ten");
        ImageIcon card11 = new ImageIcon("jackd.gif");
        Card jackd = new Card(card11, 10, "diamond", "Jack");
        ImageIcon card6 = new ImageIcon("queend.gif");
        Card queend = new Card(card6, 10, "diamond", "Queen");
        ImageIcon card47 = new ImageIcon("kingd.gif");
        Card kingd = new Card(card47, 10,"diamond", "King");
        ImageIcon card20 = new ImageIcon("aced.gif");
        Card aced = new Card(card20, 11,"diamond", "Ace");

        ImageIcon card28 = new ImageIcon("2c.gif");
        Card twoc = new Card(card28, 2,"club", "Two");
        ImageIcon card38 = new ImageIcon("3c.gif");
        Card threec = new Card(card38, 3,"club", "Three");
        ImageIcon card19 = new ImageIcon("4c.gif");
        Card fourc = new Card(card19, 4,"club", "Four");
        ImageIcon card21 = new ImageIcon("5c.gif");
        Card fivec = new Card(card21, 5,"club", "Five");
        ImageIcon card43 = new ImageIcon("6c.gif");
        Card sixc = new Card(card43, 6,"club", "Six");
        ImageIcon card42 = new ImageIcon("7c.gif");
        Card sevenc = new Card(card42, 7,"club", "Seven");
        ImageIcon card22 = new ImageIcon("8c.gif");
        Card eightc = new Card(card22, 8,"club", "Eight");
        ImageIcon card23 = new ImageIcon("9c.gif");
        Card ninec = new Card(card23, 9,"club", "Nine");
        ImageIcon card44 = new ImageIcon("10c.gif");
        Card tenc = new Card(card44, 10,"club", "Ten");
        ImageIcon card29 = new ImageIcon("jackc.gif");
        Card jackc = new Card(card29, 10,"club", "Jack");
        ImageIcon card52 = new ImageIcon("queenc.gif");
        Card queenc = new Card(card52, 10,"club", "Queen");
        ImageIcon card46 = new ImageIcon("kingc.gif");
        Card kingc = new Card(card46, 10,"club", "King");
        ImageIcon card37 = new ImageIcon("acec.gif");
        Card acec = new Card(card37, 11,"club", "Ace");


        deckSet.add(acec);
        deckSet.add(threeh);
        deckSet.add(aces);
        deckSet.add(jackh);
        deckSet.add(nineh);
        deckSet.add(sixd);
        deckSet.add(queend);
        deckSet.add(aceh);
        deckSet.add(nined);
        deckSet.add(fours);
        deckSet.add(fourh);
        deckSet.add(aced);
        deckSet.add(sevens);
        deckSet.add(tenh);
        deckSet.add(jackd);
        deckSet.add(tend);
        deckSet.add(fives);
        deckSet.add(eightd);
        deckSet.add(eights);
        deckSet.add(kings);
        deckSet.add(fourc);
        deckSet.add(fivec);
        deckSet.add(eightc);
        deckSet.add(ninec);
        deckSet.add(sixh);
        deckSet.add(queenh);
        deckSet.add(jacks);
        deckSet.add(sixs);
        deckSet.add(twoc);
        deckSet.add(jackc);
        deckSet.add(threed);
        deckSet.add(twod);
        deckSet.add(fourd);
        deckSet.add(queens);
        deckSet.add(sevenh);
        deckSet.add(eighth);
        deckSet.add(kingh);
        deckSet.add(threec);
        deckSet.add(twoh);
        deckSet.add(nines);
        deckSet.add(sevend);
        deckSet.add(sevenc);
        deckSet.add(sixc);
        deckSet.add(tenc);
        deckSet.add(threes);
        deckSet.add(kingc);
        deckSet.add(kingd);
        deckSet.add(fived);
        deckSet.add(twos);
        deckSet.add(tens);
        deckSet.add(fiveh);
        deckSet.add(queenc);


    }
    /**********************************************************
     returns a single random Card from the deck
     **********************************************************/
    public Card getCard()
    {
        Card result = new Card();
        result = deckSet.removeRandom();

        return result;
    }

}//end deck










