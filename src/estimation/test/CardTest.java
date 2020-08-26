package estimation.test;

import estimation.card.*;
import estimation.game.CurrentPlayedCards;
import org.junit.Test;
import estimation.player.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * This test covers the card class
 */

public class CardTest {

    @Test
    public void CardSuitTest() {
        Card cardTest = new Card(Suit.HEARTS, Rank.FIVE);
        assertEquals(Suit.HEARTS, cardTest.getSuit());
//        System.out.println("Card Suite Test is successful");
    }

    @Test
    public void CardRankTest() {
        Card twoOfSpade = new Card(Suit.SPADES, Rank.THREE);
        assertEquals(Rank.THREE, twoOfSpade.getRank());
    }

    @Test
    public void CardEqualsTest() {
        Card twoOfHeart = new Card(Suit.HEARTS, Rank.TWO);
        Card twoOfHeart2 = new Card(Suit.HEARTS, Rank.TWO);
        assertTrue(twoOfHeart.isSameAs(twoOfHeart2));
    }

    @Test
    public void CardEqualsTest2() {
        Card fourOfHearts = new Card(Suit.HEARTS, Rank.SIX);
        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        assertFalse(fourOfHearts.isSameAs(threeOfSpade));
    }

    @Test
    // Test if card of trump suit wins the trick, if different from lead suit.
    public void CardWinnerTest1(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        currentPlayedCards.put("Player", fourOfSpade);
        pile.add(fourOfSpade);

        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        currentPlayedCards.put("Computer_1", threeOfSpade);
        pile.add(threeOfSpade);

        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        currentPlayedCards.put("Computer_2", fiveOfHearts);
        pile.add(fiveOfHearts);

        Card sixOfHearts = new Card(Suit.HEARTS, Rank.SIX);
        currentPlayedCards.put("Computer_3", sixOfHearts);
        pile.add(sixOfHearts);

        CurrentPlayedCards currentPlayedCards1 = new CurrentPlayedCards(currentPlayedCards);
        String winnerID = currentPlayedCards1.returnWinnerID(trumpCard, pile.get(0));
        assertEquals("Computer_3",winnerID);
    }

    @Test
    // test if lead suit same as trump suit, if card wins
    public void CardWinnerTest2(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.SPADES, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        currentPlayedCards.put("Player", fourOfSpade);
        pile.add(fourOfSpade);

        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        currentPlayedCards.put("Computer_1", threeOfSpade);
        pile.add(threeOfSpade);

        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        currentPlayedCards.put("Computer_2", fiveOfHearts);
        pile.add(fiveOfHearts);

        Card sixOfHearts = new Card(Suit.HEARTS, Rank.SIX);
        currentPlayedCards.put("Computer_3", sixOfHearts);
        pile.add(sixOfHearts);

        CurrentPlayedCards currentPlayedCards1 = new CurrentPlayedCards(currentPlayedCards);
        String winnerID = currentPlayedCards1.returnWinnerID(trumpCard, pile.get(0));
        assertEquals("Player",winnerID);
    }

    @Test
    // test without trump, to set dealer at start of game
    public void CardWinnerTest3(){
        var currentPlayedCards = new HashMap<String, Card>();
        ArrayList<Card> pile = new ArrayList<>();

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        currentPlayedCards.put("Player", fourOfSpade);

        Card threeOfSpade = new Card(Suit.SPADES, Rank.ACE);
        currentPlayedCards.put("Computer_1", threeOfSpade);

        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        currentPlayedCards.put("Computer_2", fiveOfHearts);

        Card sixOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        currentPlayedCards.put("Computer_3", sixOfHearts);

        CurrentPlayedCards currentPlayedCards1 = new CurrentPlayedCards(currentPlayedCards);
        String winnerID = currentPlayedCards1.returnFirstRoundDealer();
        assertEquals("Computer_1",winnerID);
    }

    @Test
    // test without trump, to set dealer at start of game
    public void CardWinnerTest4(){
        var currentPlayedCards = new HashMap<String, Card>();
        ArrayList<Card> pile = new ArrayList<>();

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        currentPlayedCards.put("Player", fourOfSpade);

        Card threeOfSpade = new Card(Suit.SPADES, Rank.KING);
        currentPlayedCards.put("Computer_1", threeOfSpade);

        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        currentPlayedCards.put("Computer_2", fiveOfHearts);

        Card sixOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        currentPlayedCards.put("Computer_3", sixOfHearts);

        CurrentPlayedCards currentPlayedCards1 = new CurrentPlayedCards(currentPlayedCards);
        String winnerID = currentPlayedCards1.returnFirstRoundDealer();
        assertEquals("Computer_3",winnerID);
    }

    @Test
    // test with positive bids.
    public void ComputerChooseCardTest1(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(1);

        Card c1 = new Card(Suit.HEARTS, Rank.SIX);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.SPADES, Rank.SIX);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        pile.add(fourOfSpade);

        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        pile.add(threeOfSpade);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile,fourOfSpade);
        assertEquals(c4,chosenCard);

    }

    @Test
    // test with 0 bids, trump suit not in play
    public void ComputerChooseCardTest2(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(0);

        Card c1 = new Card(Suit.HEARTS, Rank.SIX);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.SPADES, Rank.SIX);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        pile.add(fourOfSpade);

        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        pile.add(threeOfSpade);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, fourOfSpade);
        //no card smaller than  FOUR SPADES
        // no trump card played
        // use the biggest trump card
        assertEquals(c1,chosenCard);

    }
    @Test
    // test with 0 bids but trumpsuit in play
    public void ComputerChooseCardTest3(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(0);

        Card c1 = new Card(Suit.HEARTS, Rank.SIX);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.SPADES, Rank.SIX);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        pile.add(fourOfSpade);

        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        pile.add(eightOfHearts);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, fourOfSpade);
        //no card smaller than  FOUR SPADES
        // trump card played
        // but no card with trump smaller than 8 of hearts
        // use the biggest trump card
        assertEquals(c1,chosenCard);
    }

    @Test
    // test if computer chooses between the 2 trump cards, picks the smaller one
    public void ComputerChooseCardTest4(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(0);

        Card c1 = new Card(Suit.HEARTS, Rank.SIX);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.HEARTS, Rank.ACE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        pile.add(fourOfSpade);

        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        pile.add(eightOfHearts);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, pile.get(0));
        //trump card in play
        // no card of lead suit smaller than lead suit played rank card
        assertEquals(c1,chosenCard);
    }

    @Test
    // test with 0 bids but with computer having card of lead suit smaller than currently played largest lead suit
    public void ComputerChooseCardTest5(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(0);

        Card c1 = new Card(Suit.HEARTS, Rank.SIX);
        Card c2 = new Card(Suit.SPADES, Rank.THREE);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.SPADES, Rank.SIX);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        pile.add(fourOfSpade);

        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        pile.add(eightOfHearts);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, pile.get(0));
        //has card smaller than  FOUR SPADES
        // trump card played
        // use the smallest lead suit card
        assertEquals(c2,chosenCard);
    }
    @Test
    // test if computer chooses between the 2 trump cards, picks the smaller one
    public void ComputerChooseCardTest6(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(0);

        Card c1 = new Card(Suit.HEARTS, Rank.KING);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.HEARTS, Rank.ACE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        pile.add(fourOfSpade);

        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        pile.add(eightOfHearts);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, pile.get(0));
        //trump card in play
        //no card bigger than highest rank lead suit
        // no trump card lower than the highest trump card
        //pick the highest trump card
        assertEquals(c4,chosenCard);
    }
    @Test
    // bid is positive
    // test if computer chooses biggest lead suit card
    public void ComputerChooseCardTest7(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(1);

        Card c1 = new Card(Suit.HEARTS, Rank.KING);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.HEARTS, Rank.ACE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        pile.add(fourOfSpade);

        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        pile.add(eightOfHearts);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, pile.get(0));

        assertEquals(c2,chosenCard);
    }

    @Test
    // bid is positive
    // test if computer chooses biggest lead suit card instead of playing trump to win the suit
    public void ComputerChooseCardTest8(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(1);

        Card c1 = new Card(Suit.HEARTS, Rank.KING);
        Card c2 = new Card(Suit.SPADES, Rank.KING);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.SPADES, Rank.THREE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.JACK);
        pile.add(fourOfSpade);

        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        pile.add(eightOfHearts);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, pile.get(0));
        assertEquals(c2,chosenCard);
    }


    @Test
    // bid is positive
    // test if computer has no lead suit card that wins, and no trump card that wins
    // that he will pick the smallest card in ascending order of the suits
    public void ComputerChooseCardTest9(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.CLUBS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(1);

        Card c1 = new Card(Suit.HEARTS, Rank.KING);
        Card c2 = new Card(Suit.SPADES, Rank.KING);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.SPADES, Rank.THREE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.CLUBS, Rank.JACK);
        pile.add(fourOfSpade);

        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        pile.add(eightOfHearts);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, pile.get(0));
        assertEquals(c3,chosenCard);
    }

    @Test
    // bid is positive
    // test if computer has no lead suit card that wins, and no trump card that wins
    // that he will pick the smallest card of lead suit
    public void ComputerChooseCardTest10(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.SPADES, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.setBids(1);

        Card c1 = new Card(Suit.HEARTS, Rank.FIVE);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.SPADES, Rank.THREE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        Card fourOfSpade = new Card(Suit.SPADES, Rank.KING);
        pile.add(fourOfSpade);

        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        pile.add(eightOfHearts);

        Card fiveOfHearts = new Card(Suit.CLUBS, Rank.FIVE);
        pile.add(fiveOfHearts);
        Card chosenCard = computer.computerChooseCard(trumpCard,pile, pile.get(0));
        assertEquals(c4,chosenCard);
    }
    @Test
    //positive bids
    // first player to play
    public void ComputerChooseCardTest11(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.makeStarter();
        computer.setBids(1);

        Card c1 = new Card(Suit.HEARTS, Rank.FIVE);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.SPADES, Rank.THREE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        try {
            Card chosenCard = computer.computerChooseCard(trumpCard, pile, null);
            assertEquals(c2,chosenCard);

        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    @Test
    //positive bids
    // first player to play
    // only have trump suit
    public void ComputerChooseCardTest12(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.makeStarter();
        computer.setBids(1);

        Card c1 = new Card(Suit.HEARTS, Rank.FIVE);
        Card c2 = new Card(Suit.HEARTS, Rank.JACK);
        Card c3 = new Card(Suit.HEARTS, Rank.KING);
        Card c4 = new Card(Suit.HEARTS, Rank.THREE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        try {
            Card chosenCard = computer.computerChooseCard(trumpCard, pile, null);
            assertEquals(c3,chosenCard);

        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    @Test
    //0 bids
    // first player to play
    //picks smallest card in ascending order of suit
    public void ComputerChooseCardTest13(){
        var currentPlayedCards = new HashMap<String, Card>();
        Card trumpCard = new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> pile = new ArrayList<>();

        Player computer = new Player(false);
        computer.makeStarter();
        computer.setBids(0);

        Card c1 = new Card(Suit.HEARTS, Rank.FIVE);
        Card c2 = new Card(Suit.SPADES, Rank.JACK);
        Card c3 = new Card(Suit.DIAMONDS, Rank.KING);
        Card c4 = new Card(Suit.CLUBS, Rank.THREE);
        computer.addToHand(c1);
        computer.addToHand(c2);
        computer.addToHand(c3);
        computer.addToHand(c4);

        try {
            Card chosenCard = computer.computerChooseCard(trumpCard, pile, null);
            assertEquals(c4,chosenCard);

        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    static class BidsTest {

    }
}