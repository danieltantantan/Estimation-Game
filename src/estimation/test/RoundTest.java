package estimation.test;

import estimation.bids.*;
import estimation.card.Card;
import estimation.card.Rank;
import estimation.card.Suit;
import estimation.game.GameState;
import estimation.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * This test covers the GameState class
 */

public class RoundTest {

    @Test
    /*
     * Test is round is added as expected
     */
    public void getRoundsTest1() {
        GameState gameState = GameState.getInstance();
        for (int i=0;i< 9;i++){
            gameState.addRound();
        }
        assertEquals(gameState.getRounds(), 10);
    }

    @Test(expected=estimation.game.MaxRoundsException.class)
    /*
     * Test if exception returned when round exceeds maxRounds 13
     */
    public void getRoundsTest2() {
        GameState gameState = GameState.getInstance();
        for (int i=0;i< 15;i++){
            gameState.addRound();
        }
    }

    @Test
    /*
     * Test if round bids is pushed to ArrayList
     */
    public void AddToRoundBidsTest() {
        GameState gameState = GameState.getInstance();
        Card twoOfHeart = new Card(Suit.HEARTS, Rank.TWO);
        String dealerId = "Computer_1";
        gameState.addToRoundBids(3);
        gameState.addToRoundBids(2);
        gameState.addToRoundBids(1);
        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList(3,2,1));
        assertEquals(gameState.getRoundBids(), bids);
    }

    @Test
    /*
     * Test if round bids is reset to empty array
     */
    public void ResetRoundBidsTest() {
        GameState gameState = GameState.getInstance();
        Card twoOfHeart = new Card(Suit.HEARTS, Rank.TWO);
        String dealerId = "Computer_1";
        gameState.addToRoundBids(3);
        gameState.addToRoundBids(2);
        gameState.addToRoundBids(1);
        gameState.resetRoundBids();
        ArrayList<Integer> bids = new ArrayList<>(Arrays.asList());
        assertEquals(gameState.getRoundBids(), bids);
    }

    @Test
    /*
     * CHeck if correct card is played for the trick
     */
    public void PlayTrickTest1() {

        GameState gameState = GameState.getInstance();
        Card aceOfClubs = new Card(Suit.CLUBS, Rank.ACE);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card aceOfDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        gameState.getCurrentState().get("Computer_1").makeStarter();
        gameState.getCurrentState().get("Computer_1").addToHand(aceOfClubs);
        gameState.getCurrentState().get("Computer_1").addToHand(aceOfHearts);
        gameState.getCurrentState().get("Computer_1").addToHand(aceOfDiamonds);

        Card twoOfHeart = new Card(Suit.HEARTS, Rank.TWO);
        gameState.setTrumpCard(twoOfHeart);
        ArrayList<Card> resulted = new ArrayList<>();
        resulted.add(aceOfClubs);

        ArrayList<Card> expected = new ArrayList<>(gameState.playTrick("Computer_1",null).values());
        assertEquals(expected,resulted);
    }

    @Test
    /*
     * CHeck if correct card is played for the trick
     */
    public void PlayTrickTest2() {

        GameState gameState = GameState.getInstance();
        Card aceOfClubs = new Card(Suit.CLUBS, Rank.ACE);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card aceOfDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card kingOfClubs = new Card(Suit.CLUBS, Rank.KING);
        Card jackOfDiamonds = new Card(Suit.DIAMONDS, Rank.JACK);
        Card queenOfHearts = new Card(Suit.HEARTS, Rank.QUEEN);
        gameState.getCurrentState().get("Computer_1").makeStarter();
        gameState.getCurrentState().get("Computer_1").addToHand(aceOfClubs);
        gameState.getCurrentState().get("Computer_1").addToHand(aceOfHearts);
        gameState.getCurrentState().get("Computer_1").addToHand(aceOfDiamonds);
        gameState.getCurrentState().get("Computer_2").addToHand(kingOfClubs);
        gameState.getCurrentState().get("Computer_2").addToHand(queenOfHearts);
        gameState.getCurrentState().get("Computer_2").addToHand(jackOfDiamonds);
        gameState.playTrick("Computer_1",null);
        Card twoOfHeart = new Card(Suit.HEARTS, Rank.TWO);
        gameState.setTrumpCard(twoOfHeart);
        ArrayList<Card> resulted = new ArrayList<>();
        resulted.add(aceOfClubs);
        resulted.add(kingOfClubs);

        ArrayList<Card> expected = new ArrayList<>(gameState.playTrick("Computer_2",null).values());
        assertEquals(expected,resulted);

    }



}