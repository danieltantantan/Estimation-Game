package estimation.test;

import estimation.player.*;
import estimation.card.*;
import java.util.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * This test covers the player class
 */

public class PlayerTest {
    //Ensure Player ID is reflected correctly
    @Test
    public void PlayerIDTest() {
        Player player1 = new Player(true);
        Player player2 = new Player(false, 1);
        Player player3 = new Player(false, 10);
        assertEquals("Player",player1.getId());
        assertEquals("Computer_1",player2.getId());
        assertEquals("Computer_10",player3.getId());
    }

    @Test
    //Ensure Player Type is reflected correctly
    public void PlayerTypeTest() {
        Player player1 = new Player(true);
        Player player2 = new Player(false, 50);
        assertEquals("Player",player1.getId());
        assertEquals("Computer_50",player2.getId());
    }

    @Test(expected=java.lang.ArithmeticException.class)
    //Results in Division Error when no card in hand
    public void ComputerNoHandBidTest() {
        Player player1 = new Player(true);
        Card twoOfHeart = new Card(Suit.HEARTS, Rank.TWO);
        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList(0,1,3));
        player1.computerPlaceBid(twoOfHeart,bids);
    }

    @Test
    //Computer should select 0 bids when such hand and Trump Card is presented
    public void ComputerBidTest1() {
        Player player1 = new Player(true);
        Card threeOfClubs = new Card(Suit.CLUBS, Rank.THREE);
        player1.addToHand(threeOfClubs);
        Card twoOfHeart = new Card(Suit.HEARTS, Rank.TWO);
        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList(0,1,3));
        assertEquals(player1.computerPlaceBid(twoOfHeart,bids),0);
    }

    @Test
    //Computer should select 2 bids when such hand and Trump Card is presented
    public void ComputerBidTest2() {
        Player player1 = new Player(true);
        Card aceOfClubs = new Card(Suit.CLUBS, Rank.ACE);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        player1.addToHand(aceOfClubs);
        player1.addToHand(aceOfHearts);
        Card twoOfHeart = new Card(Suit.SPADES, Rank.TWO);
        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList(0,1,2));
        assertEquals(player1.computerPlaceBid(twoOfHeart,bids),2);
    }

    @Test
    //Bids 3
    //Computer should choose Ace Of Clubs when such hand, pile and trump card is given
    public void ComputerChooseCardTest1() {
        Player player1 = new Player(true);
        Card aceOfClubs = new Card(Suit.CLUBS, Rank.ACE);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        player1.addToHand(aceOfClubs);
        player1.addToHand(aceOfHearts);
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);

        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList(0,1,3));
        int bidChosen = player1.computerPlaceBid(twoOfSpades,bids);

        player1.setBids(bidChosen);

        ArrayList<Card> pile = new ArrayList<>();
        Card fourOfSpade = new Card(Suit.SPADES, Rank.FOUR);
        pile.add(fourOfSpade);
        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        pile.add(threeOfSpade);
        assertEquals(player1.computerChooseCard(twoOfSpades,pile, fourOfSpade),aceOfClubs);
    }

    @Test
    //Bids 2
    //Computer should choose Ace Of Clubs when such hand, pile and trump card is given
    public void ComputerChooseCardTest2() {
        Player player1 = new Player(true);
        Card aceOfClubs = new Card(Suit.CLUBS, Rank.ACE);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card aceOfDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.TWO);

        player1.addToHand(aceOfClubs);
        player1.addToHand(aceOfHearts);
        player1.addToHand(aceOfDiamonds);
        player1.addToHand(threeOfHearts);

        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);

        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList(0,1,3));

        int bidChosen = player1.computerPlaceBid(twoOfSpades,bids);
        player1.setBids(bidChosen);

        ArrayList<Card> pile = new ArrayList<>();
        Card aceOfSpade = new Card(Suit.SPADES, Rank.ACE);
        pile.add(aceOfSpade);
        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        pile.add(threeOfSpade);

        assertEquals(player1.computerChooseCard(twoOfSpades,pile,aceOfSpade),aceOfClubs);
    }

    @Test
    //Bids 0
    //Computer should choose Ace Of Hearts when such hand, pile and trump card is given
    public void ComputerChooseCardTest3() {
        Player player1 = new Player(true);
        Card aceOfClubs = new Card(Suit.CLUBS, Rank.ACE);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card aceOfDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.TWO);

        player1.addToHand(aceOfClubs);
        player1.addToHand(aceOfHearts);
        player1.addToHand(aceOfDiamonds);
        player1.addToHand(threeOfHearts);

        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);

        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList(0));

        int bidChosen = player1.computerPlaceBid(twoOfSpades,bids);
        player1.setBids(bidChosen);

        ArrayList<Card> pile = new ArrayList<>();
        Card aceOfSpade = new Card(Suit.SPADES, Rank.ACE);
        pile.add(aceOfSpade);
        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        pile.add(threeOfSpade);

        assertEquals(player1.computerChooseCard(twoOfSpades,pile,aceOfSpade),aceOfHearts);
    }
    @Test
    //Bids 1
    //Computer should choose Ace Of Clubs when such hand, pile and trump card is given
    public void ComputerChooseCardTest4() {
        Player player1 = new Player(true);
        Card aceOfClubs = new Card(Suit.CLUBS, Rank.ACE);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card aceOfDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.TWO);

        player1.addToHand(aceOfClubs);
        player1.addToHand(aceOfHearts);
        player1.addToHand(aceOfDiamonds);
        player1.addToHand(threeOfHearts);

        Card fiveOfClubs = new Card(Suit.CLUBS, Rank.FIVE);

        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList(1));

        int bidChosen = player1.computerPlaceBid(fiveOfClubs,bids);
        player1.setBids(bidChosen);

        ArrayList<Card> pile = new ArrayList<>();
        Card aceOfSpade = new Card(Suit.SPADES, Rank.ACE);
        pile.add(aceOfSpade);
        Card threeOfSpade = new Card(Suit.SPADES, Rank.THREE);
        pile.add(threeOfSpade);

        assertEquals(player1.computerChooseCard(fiveOfClubs,pile,aceOfSpade),aceOfClubs);
    }

}